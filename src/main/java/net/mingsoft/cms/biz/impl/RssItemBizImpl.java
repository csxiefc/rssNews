package net.mingsoft.cms.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.mingsoft.base.biz.impl.BaseBizImpl;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.cms.biz.IRssItemBiz;
import net.mingsoft.cms.dao.IRssItemDao;
import net.mingsoft.cms.entity.RssItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * RSS对象行记录 服务实现类
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
@Service("cmsRssItemBizImpl")
public class RssItemBizImpl  extends BaseBizImpl<IRssItemDao, RssItemEntity> implements IRssItemBiz {

    @Autowired
    private IRssItemDao dao;


    @Override
    protected IBaseDao getDao() {
        return dao;
    }

    @Override
    public boolean isExist(String seedId, String title, Date pubDate) {
        QueryWrapper query = new QueryWrapper(new RssItemEntity().setSeedId(seedId).setTitle(title).setPubdate(pubDate));
        int count = this.dao.selectCount(query);
        return count > 0 ? true :false;
    }

    @Override
    public boolean isExist(String url) {
        QueryWrapper query = new QueryWrapper(new RssItemEntity().setLink(url));
        int count = this.dao.selectCount(query);
        return count > 0 ? true :false;
    }
}
