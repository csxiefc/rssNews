package net.mingsoft.cms.biz;

import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.ContentEntity;
import net.mingsoft.cms.entity.RssItemEntity;
import net.mingsoft.cms.entity.RssSeedEntity;

/**
 * <p>
 * RSS网站链接 服务类
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
public interface IParseLinkBiz  {

    // public AjaxResult parse(String siteId);

    AjaxResult<ContentEntity> parse(RssItemEntity itemObj);

}
