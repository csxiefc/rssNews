package net.mingsoft.cms.biz.impl;

import net.mingsoft.cms.biz.IParseLinkBiz;
import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.RssItemEntity;
import net.mingsoft.cms.entity.RssSeedEntity;
import net.mingsoft.cms.entity.RssSiteEntity;
import net.mingsoft.cms.spider.signal.ArticleParseImplBiz;
import net.mingsoft.cms.spider.signal.IArticleParseBiz;

public class ParseLinkBizImpl implements IParseLinkBiz {

    @Override
    public AjaxResult<RssItemEntity> parse(RssSiteEntity siteObj, RssSeedEntity seedObj, RssItemEntity itemObj) {
        IArticleParseBiz parseBiz = new ArticleParseImplBiz(); // 默认链接内容解析器
        AjaxResult result = parseBiz.parse(siteObj,seedObj,itemObj);
        return result;
    }

}
