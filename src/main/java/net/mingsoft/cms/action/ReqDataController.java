package net.mingsoft.cms.action;


import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.mingsoft.cms.biz.IAppCategoryMapBiz;
import net.mingsoft.cms.biz.IAppCategoryTransLogBiz;
import net.mingsoft.cms.biz.IContentBiz;
import net.mingsoft.cms.config.DataCenterConfig;
import net.mingsoft.cms.entity.*;
import net.mingsoft.cms.util.DateUtil;
import net.mingsoft.cms.util.HttpUtils;
import net.mingsoft.cms.util.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * REQ DATA请求数据中心
 * </p>
 *
 * @author xiefc
 * @since 2022-09-10
 */
@Slf4j
@Controller
@RequestMapping("/req-data")
public class ReqDataController {

    @Resource
    IAppCategoryTransLogBiz transLogBiz;

    @Resource
    IContentBiz contentBiz;

    @Resource
    IAppCategoryMapBiz appCategoryMapBiz;

    @Autowired
    DataCenterConfig dataCenterConfig;

    /**
     * 站点客户端向数据中心请求获取栏目数据行记录
     *
     * @param request
     * @param id
     * @param limit
     *
     * @return
     */
    @GetMapping("/execute")
    @ResponseBody
    public AjaxResult execute(HttpServletRequest request,String id,Integer limit){
        int factLimit = 10000;
        if(limit != null) {
            factLimit = limit.intValue();
        }
        AjaxResult result = null;
        if(StringUtils.isNotEmpty(id)) { // 根据映射行记录主键进行同步
            result = this.executeById(id,factLimit);
        } else { // 同步所有映射栏目
            result = this.executeAll(factLimit);
        }
        return result;
    }


    /**
     * 根据映射行记录主键进行同步
     *
     * @param id
     * @param limit
     * @return
     */
    private AjaxResult<Integer> executeById(String id,int limit) {
        int count = 0;
        String batchNo = DateUtil.dateToString(new Date(), "yyyyMMddhhmmss SSSSSS");
        AppCategoryMapEntity categoryMap = this.appCategoryMapBiz.getById(id); // 分类映射对象
        count = this.executeByMapObj(batchNo,categoryMap,limit);
        return AjaxResult.success(count);
    }


    /**
     * 同步所有映射栏目
     *
     * @param limit
     * @return
     */
    private AjaxResult<Integer> executeAll(int limit) {
        int count = 0;
        String batchNo = DateUtil.dateToString(new Date(), "yyyyMMddhhmmss SSSSSS"); // 批号
        List<AppCategoryMapEntity> listMap = this.appCategoryMapBiz.list(new QueryWrapper<>());
        for(AppCategoryMapEntity categoryMap : listMap) {
            count += this.executeByMapObj(batchNo,categoryMap,limit);
        }
        return AjaxResult.success(count);
    }


    /**
     * 根据映射行对象进行同步
     *
     * @param batchNo
     * @param categoryMap
     * @param limit
     * @return
     */
    private int executeByMapObj(String batchNo,AppCategoryMapEntity categoryMap,int limit) {
        int count = 0;
        // 记录请求日志
        AppCategoryTransLogEntity newLog = this.buildTransLog(batchNo,categoryMap);
        transLogBiz.save(newLog); // 日志-新增保存
        List<RssItemEntity> list = null;
        try {
            list = this.getRemoteData(categoryMap.getRssCategoryId(),categoryMap.getLastSyncDate() == null ? DateUtil.stringToDate("1998/01/01","yyyy/MM/dd"): categoryMap.getLastSyncDate(),limit); // 获取远程数据中心数据
            if(list != null && list.size() > 0) {
                newLog.setContent(JSONObject.toJSONString(list));
                newLog.setFileRows(list.size());
                newLog.setShouldRows(newLog.getFileRows());
                // 进行数据转换，适应CMS文章对象
                List<ContentEntity> contentList = this.convertList(list,categoryMap);
                // 批量更新CMS CONTENT，每200条保存一次
                List<List<ContentEntity>> splitItemList = ListUtils.splistList(contentList,200);
                splitItemList.forEach(item -> this.contentBiz.saveBatch((Collection<ContentEntity>) item) );
                count = contentList.size(); // 默认为执行成功数量

                // 更新栏目映射对象- 栏目文章的最后更新时间
                categoryMap.setLastSyncDate(list.get(0).getPubdate()); // 更新栏目文章的最后更新时间
                categoryMap.setUpdateBy("sys");
                categoryMap.setUpdateDate(new Date());
                //
                newLog.setStatus("Y"); //  // 更新日志-成功
                newLog.setFactRows(count);
            }
        } catch (Exception e) {
            log.error("executeByMapObj异常",e);
            newLog.setStatus("N"); // 更新日志-运行中
            newLog.setErrorMsg(ExceptionUtil.getRootCauseMessage(e));
        }
        // 更新保存日志
        newLog.setEndTime(new Date());
        newLog.setUpdateBy("sys");
        newLog.setUpdateDate(new Date());
        transLogBiz.updateById(newLog);
        this.appCategoryMapBiz.updateById(categoryMap);
        return count;
    }

    /**
     * 转换为CMS文章集合对象
     *
     * @param itemList
     * @param categoryMap
     * @return
     */
    private List<ContentEntity> convertList(List<RssItemEntity> itemList,AppCategoryMapEntity categoryMap) {
        List<ContentEntity> list = new ArrayList<>();
        for(RssItemEntity item : itemList ) {
            ContentEntity newObj = this.convert(item,categoryMap);
            list.add(newObj);
        }
        return list;
    }

    /**
     * 转换为CMS文章单一对象
     *
     * @param item
     * @param categoryMap
     * @return
     */
    private ContentEntity convert(RssItemEntity item,AppCategoryMapEntity categoryMap) {
        ContentEntity newObj = new ContentEntity();
        newObj.setDatasource("RssItem");
        newObj.setDatasourceId(item.getId());
        newObj.setCategoryId(categoryMap.getAppCategoryId().toString()); // 对接CMS文章管理-测试栏目
        newObj.setContentAuthor(item.getAuthor());
        newObj.setContentTitle(item.getTitle());
        newObj.setContentDetails(item.getLinkContent());
        newObj.setContentImg(this.getImgJson(item.getThumbnailPath())); // 缩图
        newObj.setContentType("h");
        newObj.setContentDisplay("0");
        newObj.setContentSort(0);
        newObj.setContentUrl(item.getLink());
        newObj.setContentSource(item.getLink());
        newObj.setContentDatetime(item.getPubdate());
        newObj.setContentHit(0);
        newObj.setCreateBy("57");
        newObj.setCreateDate(new Date());
        newObj.setUpdateBy("57");
        newObj.setUpdateDate(new Date());
        return newObj;
    }

    /**
     * 构建缩图JSON
     *
     * @param imgPath
     * @return
     */
    private String getImgJson(String imgPath) {
        String result = "[]";
        if(StringUtils.isEmpty(imgPath)) {
            return result;
        }
        // [{"url":"/upload/1/cms/content/1609835640813.jpeg","name":"a.jpeg","path":"/upload/1/cms/content/1609835640813.jpeg","uid":1609835640738,"status":"success"}]
        Map<String,Object> map = new HashMap<>();
        map.put("url","/upload/1/cms/content/1609835640813.jpeg");
        map.put("name","a.jpeg");
        map.put("path",imgPath);
        map.put("uid",new Long("1609835640738"));
        map.put("status","success");
        result = "[";
        result += JSONObject.toJSONString(map);
        result += "]";
        return result;
    }

    /**
     * 获取远程数据中心数据
     *
     * @param rssCategoryId
     * @param pubDate
     * @param limit
     * @return
     */
    private List<RssItemEntity> getRemoteData(String rssCategoryId,Date pubDate,int limit) {
        List<RssItemEntity> result = null;
        String remoteUrl = this.dataCenterConfig.getHost() + this.dataCenterConfig.getTrans();
        remoteUrl += "?siteId=1&categoryId="+rssCategoryId;
        remoteUrl += "&pubDate="+DateUtil.dateToString(pubDate,"yyyy/MM/dd HH:mm:ss");
        remoteUrl += "&limit="+limit;
        AjaxResult res = HttpUtils.getInstance().getForObject(remoteUrl,null,AjaxResult.class,dataCenterConfig.getHeaderValue());
        if(res != null && res.getCode() == 200) { // 获取成功
            String jsonStr = JSONObject.toJSONString(res.getData());
            result = JSON.parseArray(jsonStr, RssItemEntity.class);
        } else { // 获取失败
            log.error("getRemoteData异常",res);
        }
        return result;
    }

    /**
     * 构建请求传输日志对象
     *
     * @param batchNo
     * @param categoryMap
     * @return
     */
    private AppCategoryTransLogEntity buildTransLog(String batchNo,AppCategoryMapEntity categoryMap) {
        if(categoryMap == null) {
            return null;
        }
        AppCategoryTransLogEntity newLog = new AppCategoryTransLogEntity();
        newLog.setBatchNo(batchNo); // 批号
        newLog.setMapId(categoryMap.getId());
        newLog.setAppId(categoryMap.getAppId());
        newLog.setAppCategoryId(categoryMap.getAppCategoryId());
        newLog.setAppCategoryName(categoryMap.getAppCategoryName());
        newLog.setRssCategoryId(categoryMap.getRssCategoryId());
        newLog.setRssCategoryCode(categoryMap.getRssCategoryCode());
        newLog.setRssCategoryName(categoryMap.getRssCategoryName());
        newLog.setLastSyncDate(categoryMap.getLastSyncDate()); // 记录本次同步开始-时间点
        //
        newLog.setStatus("R"); // 运行中
        newLog.setStartTime(new Date());
        newLog.setCreateBy("sys");
        newLog.setCreateDate(new Date());
        return newLog;
    }


}
