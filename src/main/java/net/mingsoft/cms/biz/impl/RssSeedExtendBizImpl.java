package net.mingsoft.cms.biz.impl;

import net.mingsoft.base.biz.impl.BaseBizImpl;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.cms.biz.IRssSeedExtendBiz;
import net.mingsoft.cms.dao.IRssSeedExtendDao;
import net.mingsoft.cms.entity.RssSeedExtendEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * RSS网站链接扩展 服务实现类
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
@Service("cmsRssSeedExtendBizImpl")
public class RssSeedExtendBizImpl extends BaseBizImpl<IRssSeedExtendDao, RssSeedExtendEntity> implements IRssSeedExtendBiz {

    @Autowired
    private IRssSeedExtendDao dao;


    @Override
    protected IBaseDao getDao() {
        return dao;
    }
}
