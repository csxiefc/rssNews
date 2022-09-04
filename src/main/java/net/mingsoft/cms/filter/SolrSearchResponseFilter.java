package net.mingsoft.cms.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.mingsoft.cms.biz.IRssSearchLogBiz;
import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.config.ApplicationContextHolder;
import net.mingsoft.config.ClientAuthConfig;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * SOLR搜索服务认证服务
 */
@Slf4j
@WebFilter(filterName = "solrSearchResponseFilter", urlPatterns = "/solr/search/keyQuery")
public class SolrSearchResponseFilter implements Filter {

    @Resource
    ClientAuthConfig clientAuthConfig;

    @Resource
    IRssSearchLogBiz rssSearchLogBiz;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        boolean isValid = false;
        // 判断引用
        HttpServletRequest req = (HttpServletRequest) request;
        String currReferer = req.getHeader("referer");
        String[] includeReferers = clientAuthConfig.getIncludeReferer().split(",");
        if(ArrayUtils.contains(includeReferers,currReferer)) {
            // 判断header
            String factHeadValue =  req.getHeader(clientAuthConfig.getHeaderName());
            if(StringUtils.isNotEmpty(factHeadValue) && factHeadValue.equals(clientAuthConfig.getHeaderValue())) {
                isValid = true;
            }
        }
        if(!isValid) { // 认证不通过
            try {
                rssSearchLogBiz.saveLog(req,"N"); // 记录访问日志
                //把返回值输出到客户端
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print(JSON.toJSONString(AjaxResult.error("not auth client")));
                // out.print(AjaxResult.error("not auth client")); // 返回认证失败消息
                out.flush();
                out.close();
            } catch(Exception e) {
                log.error("SolrSearchResponseFilter异常",e);
            }
        } else { // 认证通过
            // 这里需要重写ResponseWrapper，因为原方法是没有获取返回值的功能
            ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse) response);
            // 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理
            filterChain.doFilter(request, wrapperResponse);
            try {
                //把返回值输出到客户端
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print(new String(wrapperResponse.getContent(), "UTF-8"));
                out.flush();
                out.close();
            } catch(Exception e) {
                log.error("SolrSearchResponseFilter异常",e);
            }
        }
    }







}

