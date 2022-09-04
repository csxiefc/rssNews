package net.mingsoft.cms.spider.signal;

import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.RssItemEntity;
import net.mingsoft.cms.entity.RssSeedEntity;
import net.mingsoft.cms.entity.RssSiteEntity;

public interface IArticleParseBiz {

    AjaxResult parse(RssSiteEntity siteObj, RssSeedEntity seedObj, RssItemEntity item);

}
