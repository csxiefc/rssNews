package net.mingsoft.cms.biz.impl;

import net.mingsoft.base.biz.impl.BaseBizImpl;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.cms.biz.IRssSearchLogBiz;
import net.mingsoft.cms.dao.IRssSearchLogDao;
import net.mingsoft.cms.entity.RssSearchLogEntity;
import net.mingsoft.cms.util.AddressUtils;
import net.mingsoft.cms.util.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 * RSS搜索行为记录 服务实现类
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
@Service
public class RssSearchLogBizImpl extends BaseBizImpl<IRssSearchLogDao, RssSearchLogEntity> implements IRssSearchLogBiz {

    @Autowired
    private IRssSearchLogDao dao;


    @Override
    protected IBaseDao getDao() {
        return dao;
    }

    @Override
    @Async
    public void saveLog(HttpServletRequest request,String status) {
        RssSearchLogEntity newObj = new RssSearchLogEntity();
        newObj.setRequestId( request.getRequestedSessionId());
        newObj.setContent(request.getParameterMap().toString());
        newObj.setBrowser(request.getHeader("user-agent"));
        newObj.setIp(IpUtils.getIpAddr(request));
        newObj.setPosname(AddressUtils.getRealAddressByIp(newObj.getIp()));
        newObj.setReferer(request.getHeader("referer"));
        newObj.setStatus(status);
        newObj.setCreateBy("sys");
        newObj.setCreateDate(new Date());
        this.save(newObj);
    }
}
