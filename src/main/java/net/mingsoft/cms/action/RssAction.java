/**
 * The MIT License (MIT)
 * Copyright (c) 2012-2022 铭软科技(mingsoft.net)
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package net.mingsoft.cms.action;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.mingsoft.cms.biz.IRssItemBiz;
import net.mingsoft.cms.biz.IRssRunLogBiz;
import net.mingsoft.cms.biz.IRssSeedBiz;
import net.mingsoft.cms.biz.IRssSiteBiz;
import net.mingsoft.cms.entity.*;
import net.mingsoft.cms.util.DateUtil;
import net.mingsoft.cms.util.RssUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Api(tags={"rss数据抽取"})
@Controller("rssAction")
@RequestMapping("/back/rss") ///${ms.manager.path}
@RestController
@Slf4j
public class RssAction extends BaseAction {


	@Autowired
	private IRssSiteBiz rssSiteBiz;

	@Autowired
	private IRssItemBiz rssItemBiz;

	@Autowired
	private IRssSeedBiz rssSeedBiz;

	@Autowired
	private IRssRunLogBiz runLogBiz;


	/**
	 * 返回编辑界面content_form
	 */
	@GetMapping("/spider")
	public void spider(String siteId, String seedId){
		// 开始时间
		long stime = System.currentTimeMillis();
		if(StringUtils.isNotEmpty(seedId)) {
			this.runBySeedId(seedId);
		} else if(StringUtils.isNotEmpty(siteId)) {
			this.runBySiteId(siteId);
		} else if(StringUtils.isEmpty(siteId) && StringUtils.isEmpty(seedId)) {
			this.runBySiteId(null);
		}
		// 结束时间
		long etime = System.currentTimeMillis();
		System.out.printf("执行时长：%d 秒.", (etime - stime)/1000);//执行时长：1000 毫秒.

	}

	/**
	 * 根据RSS站点ID来解析
	 *
	 * @param id
	 * @return
	 */
	private AjaxResult runBySiteId(String id) {
		QueryWrapper query = new QueryWrapper<>(new RssSiteEntity()).orderByAsc("create_date");
		if(StringUtils.isNotEmpty(id)) {
			query.eq("id",id);
		}
		List<RssSiteEntity> list = this.rssSiteBiz.list(query);
		String batchNo = DateUtil.dateToString(new Date(), "yyyyMMddhhmmss SSSSSS");
		int factCount = 0;
		for(RssSiteEntity siteObj : list) {
			boolean isUpdateSite = false;
			List<RssSeedEntity> seedList = rssSeedBiz.list(new QueryWrapper<>(new RssSeedEntity().setSiteId(siteObj.getId())).orderByAsc("create_date"));
			for(RssSeedEntity seedObj : seedList) {
				AjaxResult<RssRunLogEntity> seedRes = this.runBySeedObj(batchNo,siteObj,isUpdateSite,seedObj);
				factCount = factCount + seedRes.getData().getFactRows();
				isUpdateSite = true;
			}
			// 更新站点信息
			seedList.sort((a,b)-> a.getLastPubdate().compareTo(b.getLastPubdate()));
			siteObj.setLastPubdate(seedList.get(0).getLastPubdate());
			siteObj.setUpdateBy("sys");
			siteObj.setUpdateDate(new Date());
			this.rssSiteBiz.updateById(siteObj);
		}
		return AjaxResult.success(factCount+""); // 返回成功记录数
	}

	/**
	 * 根据SEED_ID来解析
	 *
	 * @param id
	 * @return
	 */
	private AjaxResult runBySeedId(String id) {
		String batchNo = DateUtil.dateToString(new Date(), "yyyyMMddhhmmss SSSSSS");
		RssSeedEntity seedObj = this.rssSeedBiz.getById(id);
		return this.runBySeedObj(batchNo,null,false,seedObj);
	}

	/**
	 * 根据SEED对象来解析
	 *
	 * @param batchNo
	 * @param siteObj
	 * @param isUpdateSite
	 * @param seedObj
	 * @return
	 */
	private AjaxResult<RssRunLogEntity> runBySeedObj(String batchNo,RssSiteEntity siteObj,boolean isUpdateSite,RssSeedEntity seedObj) {
		// 记录运行日志
		RssRunLogEntity newLog = new RssRunLogEntity();
		newLog.setBatchNo(batchNo);
		newLog.setDatasouce("seed");
		newLog.setDatasouceId(seedObj.getId());
		newLog.setStartTime(new Date());
		newLog.setStatus("run"); // 运行中
		newLog.setCreateBy("sys");
		newLog.setCreateDate(new Date());
		runLogBiz.save(newLog);
		try {
			// 获取RSS行数据
			SyndFeed feedObj = RssUtil.praseXml(seedObj.getUrl());
			if(siteObj != null && !isUpdateSite) { // 更新RSS站点信息
				siteObj.setName(feedObj.getGenerator());
				siteObj.setAuthor(feedObj.getAuthor());
				siteObj.setThumbnailPath(feedObj.getImage().getUrl());
				siteObj.setThumbnailDesc(feedObj.getImage().getDescription());
				siteObj.setCopyright(feedObj.getCopyright());
			}
			List<SyndEntry> syndList = feedObj.getEntries();
			List<SyndEntry> shouldList = syndList.stream().filter(s-> s.getPublishedDate().compareTo(feedObj.getPublishedDate()) > 0).collect(Collectors.toList());
			shouldList.sort((a,b)-> a.getPublishedDate().compareTo(b.getPublishedDate()));
			log.info("fileRows="+syndList.size());
			log.info("shouldRow ="+shouldList.size());
			int factCount = this.convert2RssItem(batchNo,seedObj,shouldList);
			log.info("factCount ="+factCount);

			// 更新SEED对象
			seedObj.setUpdateBy("sys");
			seedObj.setUpdateDate(new Date());
			seedObj.setLastPubdate(shouldList.get(0).getPublishedDate());
			this.rssSeedBiz.updateById(seedObj);
			// 单个RSS网址执行完成-更新日志
			newLog.setLastPubdate(seedObj.getLastPubdate());
			newLog.setFileRows(syndList.size());
			newLog.setShouldRows(0);
			newLog.setFactRows(factCount);
			newLog.setEndTime(new Date());
			newLog.setStatus("success");
			String taskTime = ((newLog.getEndTime().getTime() - newLog.getStartTime().getTime()) / 1000) + "s";
			newLog.setTakeTime(taskTime);
			newLog.setUpdateBy("sys");
			newLog.setUpdateDate(new Date());
			runLogBiz.updateById(newLog);
		} catch (Exception e) {
			log.error("抓取异常",e);
			// 记录异常消息
			newLog.setEndTime(new Date());
			newLog.setStatus("fail");
			newLog.setMsg(ExceptionUtils.getStackTrace(e));
			String taskTime = ((newLog.getEndTime().getTime() - newLog.getStartTime().getTime()) / 1000) + "s";
			newLog.setTakeTime(taskTime);
			newLog.setUpdateBy("sys");
			newLog.setUpdateDate(new Date());
			runLogBiz.updateById(newLog);
		}
		return AjaxResult.success(newLog); // 返回成功标识，具体的见log对象的status
	}

	/**
	 * RSS行记录转换为结构化数据并保存
	 *
	 * @param batchNo
	 * @param seedObj
	 * @param syndList
	 * @return
	 */
	private int convert2RssItem(String batchNo, RssSeedEntity seedObj, List<SyndEntry> syndList) {
		int result = 0;
		log.info("syndList.size()="+syndList.size());
		for(int s=0;s<syndList.size();s++) {
			try {
				int index = (s+1);
				log.info("SyndEntry Execute index ="+index);
				SyndEntry syndObj = syndList.get(s);
				// 判断URL是否已经存在
				if(this.rssItemBiz.isExist(syndObj.getLink())) {
					log.info("exist url:"+syndObj.getLink());
					continue;
				}
				// 记录RSS行数据
				RssItemEntity newItem = new RssItemEntity();
				newItem.setSiteId(seedObj.getSiteId());
				newItem.setSeedId(seedObj.getId());
				newItem.setCategoryId(seedObj.getCategoryId());
				newItem.setCategoryName(seedObj.getCategoryName());
				newItem.setBatchNo(batchNo);
				newItem.setBizId("");
				newItem.setTitle(syndObj.getTitle().trim().length() > 250 ? syndObj.getTitle().trim().substring(0,250) : syndObj.getTitle().trim());
				newItem.setLink(syndObj.getLink() != null ? syndObj.getLink().trim() : null);
				newItem.setAuthor(syndObj.getAuthor() != null ? syndObj.getAuthor().trim() : null);
				newItem.setDescription(syndObj.getDescription().getValue() != null ? syndObj.getDescription().getValue().trim() : null);
				newItem.setPubdate(syndObj.getPublishedDate());
				// newItem.setFromJson(JSON.toJSONString(syndObj));
				newItem.setIsBuild("N");
				newItem.setIsIndex("N");
				newItem.setCreateBy("sys");
				newItem.setCreateDate(new Date());
				this.rssItemBiz.save(newItem);
				result++;
			} catch(Exception e) {
				log.error("convertBySyndEntry异常",e);
			}
		}
		return result;
	}

}
