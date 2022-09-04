package net.mingsoft.cms.biz.impl;

import net.mingsoft.base.biz.impl.BaseBizImpl;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.cms.biz.IRssItemBiz;
import net.mingsoft.cms.biz.IRssItemElementBiz;
import net.mingsoft.cms.dao.IRssItemDao;
import net.mingsoft.cms.dao.IRssItemElementDao;
import net.mingsoft.cms.entity.RssItemElementEntity;
import net.mingsoft.cms.entity.RssItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * RSS对象行元素服务实现类
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
@Service("cmsRssItemElementBizImpl")
public class RssItemElementBizImpl extends BaseBizImpl<IRssItemElementDao, RssItemElementEntity> implements IRssItemElementBiz {

    @Autowired
    private IRssItemElementDao dao;


    @Override
    protected IBaseDao getDao() {
        return dao;
    }
}
