package net.mingsoft.cms.biz;

import com.rometools.rome.feed.synd.SyndEntry;
import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.cms.entity.RssItemEntity;
import net.mingsoft.cms.entity.RssSeedEntity;

import java.util.Date;
import java.util.List;

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

    int convertBySyndEntry(String batchNo, RssSeedEntity seedObj, List<SyndEntry> syndList);

    List<RssItemEntity> getData(String categoryId, Date pubDate,int limit) ;

}
