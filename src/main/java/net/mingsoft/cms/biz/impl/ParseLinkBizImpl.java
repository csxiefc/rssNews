package net.mingsoft.cms.biz.impl;

import net.mingsoft.cms.biz.IParseLinkBiz;
import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.RssItemEntity;
import net.mingsoft.cms.spider.signal.ArticleParseImplBiz;
import net.mingsoft.cms.spider.signal.IArticleParseBiz;

public class ParseLinkBizImpl implements IParseLinkBiz {

    @Override
    public AjaxResult<RssItemEntity> parse(RssItemEntity itemObj) {
        IArticleParseBiz parseBiz = new ArticleParseImplBiz(); // 默认链接内容解析器
        AjaxResult<RssItemEntity> result = parseBiz.parse(itemObj);
        return result;
    }

}
