package net.mingsoft.cms.biz.impl;


import net.mingsoft.base.biz.impl.BaseBizImpl;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.cms.biz.IRssCategoryBiz;

import net.mingsoft.cms.dao.IRssCategoryDao;
import net.mingsoft.cms.entity.RssCategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * RSS信息分类 服务实现类
 * </p>
 *
 * @author xiefc
 * @since 2022-09-04
 */
@Service("cmsRssCategoryBizImpl")
public class RssCategoryBizImpl extends BaseBizImpl<IRssCategoryDao, RssCategoryEntity> implements IRssCategoryBiz {

    @Autowired
    private IRssCategoryDao dao;

    @Override
    protected IBaseDao<RssCategoryEntity> getDao() {
        return dao;
    }
}
