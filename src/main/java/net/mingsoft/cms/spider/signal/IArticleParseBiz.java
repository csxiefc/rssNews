package net.mingsoft.cms.spider.signal;

import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.ContentEntity;
import net.mingsoft.cms.entity.RssItemEntity;

public interface IArticleParseBiz {

    AjaxResult<ContentEntity> parse(RssItemEntity item);

    AjaxResult<ContentEntity> parse(String url);

    ContentEntity test(String url);

}
