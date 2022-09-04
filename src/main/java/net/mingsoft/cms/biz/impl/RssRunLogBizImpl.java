package net.mingsoft.cms.biz.impl;

import net.mingsoft.base.biz.impl.BaseBizImpl;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.cms.biz.IRssRunLogBiz;
import net.mingsoft.cms.dao.IRssRunLogDao;
import net.mingsoft.cms.entity.RssRunLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * RSS爬日志 服务实现类
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
@Service
public class RssRunLogBizImpl extends BaseBizImpl<IRssRunLogDao, RssRunLogEntity> implements IRssRunLogBiz {

    @Autowired
    private IRssRunLogDao dao;


    @Override
    protected IBaseDao getDao() {
        return dao;
    }
}
