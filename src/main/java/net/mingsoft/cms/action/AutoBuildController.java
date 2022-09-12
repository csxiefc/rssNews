package net.mingsoft.cms.action;


import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.mingsoft.base.entity.ResultData;
import net.mingsoft.cms.biz.IAppCategoryMapBiz;
import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.AppCategoryMapEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * RSS DATA服务中心
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
@Slf4j
@Controller
@RequestMapping("/autoBuild")
public class AutoBuildController {

    @Autowired
    GeneraterAction generaterAction;

    @Autowired
    private IAppCategoryMapBiz categoryMapBiz;

    /**
     * 自动静态化页面
     *
     * @param request
     * @param response
     * @param isRebuild
     * @return
     */
    @RequestMapping(value = "/execute", method = {RequestMethod.GET})
    @ResponseBody
    public AjaxResult autoBuild(HttpServletRequest request,
                                HttpServletResponse response,
                                String mapId,
                                String type,
                                boolean isRebuild) {
        try {
            QueryWrapper<AppCategoryMapEntity> query = new QueryWrapper();
            if(StringUtils.isNotEmpty(mapId)) {
                query.eq("id",mapId);
            }
            List<AppCategoryMapEntity> mapList = this.categoryMapBiz.list();
            for(AppCategoryMapEntity mapObj : mapList) {
                // 文章 静态化
                ResultData artResult = generaterAction.generateArticle(request,response,mapObj.getAppCategoryId(),null,isRebuild);
                // 列表和幻灯 静态化
                generaterAction.genernateColumn(request,response,mapObj.getAppCategoryId(),isRebuild);
            }
            // 首页 静态化
            generaterAction.generateIndex(request,response);
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("autoBuild异常",e);
            return AjaxResult.error(ExceptionUtil.getRootCauseMessage(e));
        }
    }




}
