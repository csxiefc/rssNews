package net.mingsoft.cms.biz.impl;

import net.mingsoft.base.biz.impl.BaseBizImpl;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.cms.biz.IRssSeedBiz;
import net.mingsoft.cms.dao.IRssSeedDao;
import net.mingsoft.cms.entity.RssSeedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * RSS网站链接 服务实现类
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
@Service("cmsRssSeedBizImpl")
public class RssSeedBizImpl extends BaseBizImpl<IRssSeedDao, RssSeedEntity> implements IRssSeedBiz {

    @Autowired
    private IRssSeedDao dao;


    @Override
    protected IBaseDao getDao() {
        return dao;
    }
}
