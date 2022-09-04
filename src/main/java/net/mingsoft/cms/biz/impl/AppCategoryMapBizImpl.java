package net.mingsoft.cms.biz.impl;

import net.mingsoft.base.biz.impl.BaseBizImpl;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.cms.biz.IAppCategoryMapBiz;
import net.mingsoft.cms.dao.IAppCategoryMapDao;
import net.mingsoft.cms.entity.AppCategoryMapEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网站栏目数据映射 服务实现类
 * </p>
 *
 * @author xiefc
 * @since 2022-09-04
 */
@Service
public class AppCategoryMapBizImpl extends BaseBizImpl<IAppCategoryMapDao, AppCategoryMapEntity> implements IAppCategoryMapBiz {

    @Autowired
    private IAppCategoryMapDao dao;

    @Override
    protected IBaseDao<AppCategoryMapEntity> getDao() {
        return dao;
    }
}
