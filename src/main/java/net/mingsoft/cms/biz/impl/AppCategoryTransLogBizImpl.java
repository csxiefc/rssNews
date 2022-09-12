package net.mingsoft.cms.biz.impl;

import net.mingsoft.base.biz.impl.BaseBizImpl;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.cms.biz.IAppCategoryTransLogBiz;
import net.mingsoft.cms.dao.IAppCategoryTransLogDao;
import net.mingsoft.cms.entity.AppCategoryTransLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 应用数据传输日志表 服务实现类
 * </p>
 *
 * @author xiefc
 * @since 2022-09-10
 */
@Service
public class AppCategoryTransLogBizImpl extends BaseBizImpl<IAppCategoryTransLogDao, AppCategoryTransLogEntity> implements IAppCategoryTransLogBiz {


    @Autowired
    private IAppCategoryTransLogDao dao;

    @Override
    protected IBaseDao<AppCategoryTransLogEntity> getDao() {
        return dao;
    }
}
