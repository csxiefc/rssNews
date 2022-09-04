package net.mingsoft.cms.biz;

import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.cms.entity.RssSearchLogEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * RSS搜索行为记录 服务类
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
public interface IRssSearchLogBiz extends IBaseBiz<RssSearchLogEntity> {

    public void saveLog(HttpServletRequest request,String status);

}
