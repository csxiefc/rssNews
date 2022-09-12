package net.mingsoft.cms.action;


import net.mingsoft.cms.biz.IRssItemBiz;
import net.mingsoft.cms.config.DataCenterConfig;
import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.RssItemEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * RSS DATA服务中心
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
@Controller
@RequestMapping("/rss-data")
public class RssDataController {

    @Autowired
    DataCenterConfig dataCenterConfig;

    @Autowired
    IRssItemBiz rssItemBiz;

    /**
     * 解析RSS链接具体网页内容入口
     *
     * @param siteId
     * @param categoryId
     * @param pubDate
     * @return
     */
    @GetMapping("/execute")
    @ResponseBody
    public AjaxResult<List<RssItemEntity>> execute(HttpServletRequest request,String siteId, String categoryId, Date pubDate, int limit){
        String errMsg = this.valid(request,siteId,categoryId);
        if(StringUtils.isNotEmpty(errMsg)) { // 验证错误
            return AjaxResult.error(errMsg,null);
        } else { // 验证通过
            List<RssItemEntity> list = this.rssItemBiz.getData(categoryId,pubDate,limit);
            return AjaxResult.success(list);
        }
    }

    /**
     * 验证客户端或者参数是否有效
     *
     * @param request
     * @param siteId
     * @param categoryId
     * @return
     */
    private String valid(HttpServletRequest request, String siteId, String categoryId) {
        String result = "";
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isEmpty(siteId) || StringUtils.isEmpty(categoryId) ) {
            result = "param is empty!";
        } else if(token == null || !token.equals(this.dataCenterConfig.getHeaderValue())) {  // 验证令牌
            result = "not auth client!";
        }
        return result;
    }


}
