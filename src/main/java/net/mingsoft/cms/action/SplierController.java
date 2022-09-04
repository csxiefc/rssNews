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
		AjaxResult<ContentEntity> contentRes = parseLinkBiz.parse(item);
		if(contentRes.getCode() == 200) { // 新增保存-CMS文章
			this.contentBiz.save(contentRes.getData());
		}
		return contentRes;
	}

	@GetMapping(value = "execute")
	@ResponseBody
	public AjaxResult execute() {
		defaultTask.crawl();
		return AjaxResult.success(DateUtil.getStringNow());
	}


}
