package net.mingsoft.cms.biz.impl;

import net.mingsoft.base.biz.impl.BaseBizImpl;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.cms.biz.IRssSiteBiz;
import net.mingsoft.cms.dao.IRssSiteDao;
import net.mingsoft.cms.entity.RssSiteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * RSS网站 服务实现类
 * </p>
 *
 * @author xiefc
 * @since 2022-09-04
 */
@Service("cmsRssSiteBizImpl")
public class RssSiteBizImpl extends BaseBizImpl<IRssSiteDao, RssSiteEntity> implements IRssSiteBiz {

    @Autowired
    private IRssSiteDao dao;

    @Override
    protected IBaseDao<RssSiteEntity> getDao() {
        return null;
    }
}
