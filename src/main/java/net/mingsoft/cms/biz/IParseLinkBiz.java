package net.mingsoft.cms.biz;

import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.RssItemEntity;
import net.mingsoft.cms.entity.RssSeedEntity;
import net.mingsoft.cms.entity.RssSiteEntity;

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

    AjaxResult parse(RssSiteEntity siteObj, RssSeedEntity seedObj, RssItemEntity itemObj);

}
