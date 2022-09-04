package net.mingsoft.cms.biz;

import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.RssItemEntity;

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

    AjaxResult<RssItemEntity> parse(RssItemEntity itemObj);

}
