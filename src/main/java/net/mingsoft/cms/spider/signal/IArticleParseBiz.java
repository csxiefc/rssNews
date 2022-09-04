package net.mingsoft.cms.spider.signal;

import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.RssItemEntity;

public interface IArticleParseBiz {

    AjaxResult<RssItemEntity>  parse(RssItemEntity item);

}
