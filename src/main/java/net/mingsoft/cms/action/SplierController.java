package net.mingsoft.cms.action;

import lombok.extern.slf4j.Slf4j;
import net.mingsoft.cms.biz.IContentBiz;
import net.mingsoft.cms.biz.IParseLinkBiz;
import net.mingsoft.cms.biz.IRssItemBiz;
import net.mingsoft.cms.biz.impl.ParseLinkBizImpl;
import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.ContentEntity;
import net.mingsoft.cms.entity.RssItemEntity;
import net.mingsoft.cms.spider.DefaultTask;
import net.mingsoft.cms.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping(value = "/splier", produces = "application/json;charset=UTF-8")
public class SplierController {

	@Autowired
	DefaultTask defaultTask;

	@Autowired
	IRssItemBiz rssItemBiz;

	@Autowired
	IContentBiz contentBiz;


	/**
	 * 根据RSS行级项进行爬取网页内容
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "crawlByRssItemId")
	@ResponseBody
	public AjaxResult crawlByRssItemId(String id) {
		IParseLinkBiz parseLinkBiz = new ParseLinkBizImpl();
		RssItemEntity item = this.rssItemBiz.getById(id);
		AjaxResult<RssItemEntity> itemRes = parseLinkBiz.parse(item);
		if(itemRes.getCode() == 200) { // 新增保存-CMS文章
			ContentEntity newObj = this.convert2CmsContent(itemRes.getData());
			this.contentBiz.save(newObj);
		}
		return itemRes;
	}

	@GetMapping(value = "execute")
	@ResponseBody
	public AjaxResult execute() {
		defaultTask.crawl();
		return AjaxResult.success(DateUtil.getStringNow());
	}

	private ContentEntity convert2CmsContent(RssItemEntity item) {
		ContentEntity newObj = new ContentEntity();
		newObj.setDatasource("RssItem");
		newObj.setDatasourceId(item.getId());
		newObj.setCategoryId("1565900410928119810"); // 测试-头条资讯
		newObj.setContentAuthor(item.getAuthor());
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
