package net.mingsoft.cms.biz;

import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.cms.entity.RssItemEntity;

import java.util.Date;

/**
 * <p>
 * RSS对象行记录 服务类
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
public interface IRssItemBiz extends IBaseBiz<RssItemEntity> {

    boolean isExist(String feedId, String title, Date pubDate);

    boolean isExist(String url);

}
