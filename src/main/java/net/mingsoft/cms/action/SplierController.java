package net.mingsoft.cms.action;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.mingsoft.cms.biz.*;
import net.mingsoft.cms.biz.impl.ParseLinkBizImpl;
import net.mingsoft.cms.entity.*;
import net.mingsoft.cms.util.DateUtil;
import net.mingsoft.cms.util.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping(value = "/splier", produces = "application/json;charset=UTF-8")
public class SplierController {

	@Autowired
	IRssItemBiz rssItemBiz;

	@Autowired
	IRssSeedBiz rssSeedBiz;

	@Autowired
	IRssSiteBiz rssSiteBiz;

	@Autowired
	IContentBiz contentBiz;

	@Autowired
	private ThreadPoolTaskExecutor executor;


	/**
	 * 解析RSS链接具体网页内容入口
	 *
	 * @param siteId
	 * @param seedId
	 * @param itemId
	 * @return
	 */
	@GetMapping("/execute")
	@ResponseBody
	public AjaxResult execute(String siteId, String seedId,String itemId,boolean rebuild){
		AjaxResult result = null;
		// 开始时间
		long stime = System.currentTimeMillis();
		if(StringUtils.isNotEmpty(itemId)) { // rssItem
			result = this.crawlByRssItemId(itemId,rebuild);
		} else if(StringUtils.isNotEmpty(seedId)) { // rssSeed
			result = this.crawlByRssSeedId(seedId,rebuild);
		} else if(StringUtils.isNotEmpty(siteId)) { // rssSite
			result = this.crawlByRssSiteId(siteId,rebuild);
		} else if(StringUtils.isEmpty(siteId) && StringUtils.isEmpty(seedId) && StringUtils.isEmpty(itemId)) { // param empty
			result = this.crawlByRssSiteId(null,rebuild);
		}
		// 结束时间
		long etime = System.currentTimeMillis();
		System.out.printf("执行时长：%d 秒.", (etime - stime)/1000);//执行时长：1000 毫秒.
		return result;
	}


	/**
	 * 根据RSS站点ID解析该站点下所有RSS链接网页
	 *
	 * @param id
	 * @return
	 */
	private AjaxResult crawlByRssSiteId(String id,boolean rebuild) {
		QueryWrapper query = new QueryWrapper<>(new RssSiteEntity()).orderByAsc("create_date");
		if(StringUtils.isNotEmpty(id)) {
			query.eq("id",id);
		}
		List<RssSiteEntity> list = this.rssSiteBiz.list(query);
		if(list.size() == 0) {
			return AjaxResult.error("have no suit rssSite!");
		}
		int factCount = 0;
		List<CompletableFuture<Integer>> futures = new ArrayList<>();
		for(RssSiteEntity siteObj : list) {
			//并行线程调用 提供执行效率，为每一个RSS SITE启一个线程
			if(StringUtils.isNotEmpty(siteObj.getParseConf())) {
				futures.add(CompletableFuture.supplyAsync(() -> crawlByRssSiteObj(siteObj,rebuild), executor));
			}
		}
		//等待全部完成
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
		//获取内容
		for (CompletableFuture<Integer> future : futures) {
			try {
				Integer res = future.get();
				factCount = factCount + res.intValue();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		// 释放内存
		futures.clear();futures=null;
		list.clear();list=null;
		return AjaxResult.success(DateUtil.getStringNow()+",factCount="+factCount); // 返回成功记录数
	}

	/**
	 * 根据RSS站点SEED ID解析该SEED下所有RSS链接网页
	 *
	 * @param id
	 * @return
	 */
	private AjaxResult crawlByRssSeedId(String id,boolean rebuild) {
		RssSeedEntity seedObj = this.rssSeedBiz.getById(id);
		RssSiteEntity siteObj = this.rssSiteBiz.getById(seedObj.getSiteId());
		return this.crawlByRssSeedObj(siteObj,seedObj,rebuild);
	}

	/**
	 * 根据RSS ITEM_ID解析单行数据
	 *
	 * @param id
	 * @return
	 */
	private AjaxResult crawlByRssItemId(String id,boolean rebuild) {
		QueryWrapper query = new QueryWrapper(new RssItemEntity());
		query.eq("id",id);
		if(!rebuild) { // 仅查询未被解析过的链接
			query.eq("isBuild","N");
		}
		RssItemEntity itemObj = this.rssItemBiz.getOne(query);
		if(itemObj == null ) {
			return AjaxResult.error("RssItemId="+id+",isBuild=N,not found!");
		}
		RssSeedEntity seedObj = this.rssSeedBiz.getById(itemObj.getSeedId());
		RssSiteEntity siteObj = this.rssSiteBiz.getById(itemObj.getSiteId());
		return this.crawlByRssItemObj(siteObj,seedObj,itemObj);
	}

	/**
	 * 根据RSS SITE对象解析
	 *
	 * @param siteObj
	 * @return
	 */
	private Integer crawlByRssSiteObj(RssSiteEntity siteObj, boolean rebuild) {
		int count = 0;
		List<RssSeedEntity> seedList = this.rssSeedBiz.list(new QueryWrapper<>(new RssSeedEntity().setSiteId(siteObj.getId())));
		for(RssSeedEntity seedObj : seedList) {
			AjaxResult<Integer> seedResult = this.crawlByRssSeedObj(siteObj,seedObj,rebuild);
			count = count + seedResult.getData().intValue();
		}
		return count;
	}

	/**
	 * 根据RSS SEED对象解析
	 *
	 * @param siteObj
	 * @param seedObj
	 * @return
	 */
	private AjaxResult<Integer> crawlByRssSeedObj(RssSiteEntity siteObj, RssSeedEntity seedObj, boolean rebuild) {
		AjaxResult<Integer> result = AjaxResult.success(0);
		int count = 0;
		String errorMsg = "";
		QueryWrapper query = new QueryWrapper(new RssItemEntity());
		query.eq("seed_id",seedObj.getId());
		if(!rebuild) { // 仅查询未被解析过的链接
			query.eq("isBuild","N");
		}
		List<RssItemEntity> itemList = this.rssItemBiz.list(query);
		if(itemList.size() == 0) {
			AjaxResult.success(new Integer(count));
		}
		// 分隔为每200条分批进行解析、和保存更新
		List<List<RssItemEntity>> dealItemList = ListUtils.splistList(itemList,200);
		IParseLinkBiz parseLinkBiz = new ParseLinkBizImpl();
		for(List<RssItemEntity> dealList : dealItemList) {
			List<RssItemEntity> itemUpdateList = new ArrayList<>();
			for(RssItemEntity itemObj : dealList) {
				AjaxResult itemRes = parseLinkBiz.parse(siteObj,seedObj,itemObj);
				if(itemRes.getCode() == 200) { // 新增保存-CMS文章
					itemUpdateList.add(itemObj);
				}
			}
			if(itemUpdateList.size() > 0) { // 批量更新RSS ITEM，每200条保存一次
				try {
					this.rssItemBiz.updateBatchById((Collection<RssItemEntity>) itemUpdateList);
					count += itemUpdateList.size();
				} catch (Exception e) {
					errorMsg = ExceptionUtil.getRootCauseMessage(e);
				}
			}
			// 释放
			itemUpdateList.clear();itemUpdateList=null;
		}
		result.setMsg("count="+itemList.size()+",fact="+count+",msg="+errorMsg);
		result.setData(count);
		// 释放
		dealItemList.clear(); dealItemList = null;
		itemList.clear();itemList=null;
		return result;
	}


	/**
	 * 根据RSS ITEM对象解析单行数据
	 *
	 * @param siteObj
	 * @param seedObj
	 * @param itemObj
	 * @return
	 */
	private AjaxResult<Integer> crawlByRssItemObj(RssSiteEntity siteObj, RssSeedEntity seedObj, RssItemEntity itemObj) {
		IParseLinkBiz parseLinkBiz = new ParseLinkBizImpl();
		AjaxResult itemRes = parseLinkBiz.parse(siteObj,seedObj,itemObj);
		if(itemRes.getCode() == 200) {
			/*
			在解析网页内容的时候，不做CMS的文章建立
			ContentEntity newObj = this.convert2CmsContent((RssItemEntity)itemRes.getData());
			// 新增保存-CMS文章
			this.contentBiz.save(newObj);
			 */
			// 更新RSS行记录
			this.rssItemBiz.updateById(itemObj);
			return AjaxResult.success(new Integer(1));
		} else { // 错误消息
			return AjaxResult.error(itemRes.getMsg(),new Integer(0));
		}
	}

	/**
	 * 转换为CMS文章对象
	 *
	 * @param item
	 * @return
	 */
	private ContentEntity convert2CmsContent(RssItemEntity item) {
		ContentEntity newObj = new ContentEntity();
		newObj.setDatasource("RssItem");
		newObj.setDatasourceId(item.getId());
		newObj.setCategoryId("1565900410928119810"); // 对接CMS文章管理-测试栏目
		newObj.setContentAuthor(item.getAuthor());
		newObj.setContentTitle(item.getTitle());
		newObj.setContentDetails(item.getLinkContent());
		newObj.setContentImg("[]");
		newObj.setContentType("h");
		newObj.setContentDisplay("0");
		newObj.setContentSort(0);
		newObj.setContentUrl(item.getLink());
		newObj.setContentSource(item.getLink());
		newObj.setContentDatetime(item.getPubdate());
		newObj.setContentHit(0);
		newObj.setCreateBy("57");
		newObj.setCreateDate(new Date());
		newObj.setUpdateBy("57");
		newObj.setUpdateDate(new Date());
		return newObj;
	}

}
