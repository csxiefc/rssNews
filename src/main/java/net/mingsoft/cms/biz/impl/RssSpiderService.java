package net.mingsoft.cms.biz.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import lombok.extern.slf4j.Slf4j;
import net.mingsoft.cms.biz.IRssItemBiz;
import net.mingsoft.cms.biz.IRssRunLogBiz;
import net.mingsoft.cms.biz.IRssSeedBiz;
import net.mingsoft.cms.biz.IRssSiteBiz;
import net.mingsoft.cms.entity.*;
import net.mingsoft.cms.util.DateUtil;
import net.mingsoft.cms.util.RssUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * RSS信息分类 服务实现类
 * </p>
 *
 * @author xiefc
 * @since 2022-09-04
 */
@Slf4j
@Service("cmsRssSpiderService")
public class RssSpiderService {

    @Autowired
    private IRssSiteBiz rssSiteBiz;

    @Autowired
    private IRssItemBiz rssItemBiz;

    @Autowired
    private IRssSeedBiz rssSeedBiz;

    @Autowired
    private IRssRunLogBiz runLogBiz;

    public Integer runBySiteObj(String batchNo, RssSiteEntity siteObj) {
        int factCount = 0;
        boolean isUpdateSite = false;
        List<RssSeedEntity> seedList = rssSeedBiz.list(new QueryWrapper<>(new RssSeedEntity().setSiteId(siteObj.getId())).orderByAsc("create_date"));
        for(RssSeedEntity seedObj : seedList) {
            AjaxResult<RssRunLogEntity> seedRes = this.runBySeedObj(batchNo,siteObj,isUpdateSite,seedObj);
            factCount = factCount + seedRes.getData().getFactRows();
            if( seedRes.getData().getStatus().equals("success")) {
                isUpdateSite = true;
            }
        }
        // 排序，最后的文章发布日期在第一位位置
        Collections.sort(seedList, new Comparator<RssSeedEntity>() {
            @Override
            public int compare(RssSeedEntity o1, RssSeedEntity o2) {
                if(o1.getLastPubdate() != null && o2.getLastPubdate() != null) {
                    return o2.getLastPubdate().compareTo(o1.getLastPubdate());
                } else {
                    return 0;
                }
            }
        });
        // 更新站点信息
        siteObj.setLastPubdate(seedList.get(0).getLastPubdate());
        siteObj.setUpdateBy("sys");
        siteObj.setUpdateDate(new Date());
        this.rssSiteBiz.updateById(siteObj);
        // 释放
        seedList.clear(); seedList=null;
        return factCount;
    }

    /**
     * 根据SEED对象来解析
     *
     * @param batchNo
     * @param siteObj
     * @param isUpdateSite
     * @param seedObj
     * @return
     */
    public AjaxResult<RssRunLogEntity> runBySeedObj(String batchNo,RssSiteEntity siteObj,boolean isUpdateSite,RssSeedEntity seedObj) {
        // 首先记录运行日志
        RssRunLogEntity newLog = new RssRunLogEntity();
        newLog.setBatchNo(batchNo);
        newLog.setDatasouce("RssSeed");
        newLog.setDatasouceId(seedObj.getId());
        newLog.setFactRows(0);
        newLog.setStartTime(new Date());
        newLog.setStatus("run"); // 运行中
        newLog.setCreateBy("sys");
        newLog.setCreateDate(new Date());
        runLogBiz.save(newLog);
        try {
            // 获取RSS行数据
            SyndFeed feedObj = RssUtil.praseXml(seedObj.getUrl());
            if(siteObj != null && !isUpdateSite) { // 更新RSS站点信息
                siteObj.setName(feedObj.getGenerator());
                siteObj.setAuthor(feedObj.getAuthor());
                siteObj.setThumbnailPath(feedObj.getImage().getUrl()); // 网站LOGO图片
                siteObj.setThumbnailDesc(feedObj.getImage().getDescription());
                siteObj.setCopyright(feedObj.getCopyright());
            }
            List<SyndEntry> syndList = feedObj.getEntries();
            List<SyndEntry> shouldList = syndList; // 默认为文件行集合
            if(seedObj.getLastPubdate() != null) {
                shouldList = syndList.stream().filter(s-> s.getPublishedDate().compareTo(seedObj.getLastPubdate()) > 0).collect(Collectors.toList());
            }
            shouldList.sort((a,b)-> b.getPublishedDate().compareTo(a.getPublishedDate()));
            int factCount = this.convert2RssItem(batchNo,seedObj,shouldList); // RSS文件行记录进行结构化
            // 更新SEED对象
            seedObj.setUpdateBy("sys");
            seedObj.setUpdateDate(new Date());
            seedObj.setLastPubdate(shouldList.size() > 0 ? shouldList.get(0).getPublishedDate() : seedObj.getLastPubdate());
            this.rssSeedBiz.updateById(seedObj);
            // 单个RSS网址执行完成-更新日志
            newLog.setLastPubdate(seedObj.getLastPubdate());
            newLog.setFileRows(syndList.size());
            newLog.setShouldRows(shouldList.size());
            newLog.setFactRows(factCount);
            newLog.setStatus("success");
        } catch (Exception e) { // 记录异常消息
            log.error("抓取异常",e);
            seedObj.setLastPubdate(DateUtil.stringToDate("1990-01-01","yyyy-MM-dd")); // 用于上层进行排序获取最新的文章发布日期
            newLog.setStatus("fail");
            newLog.setMsg(ExceptionUtils.getStackTrace(e));
        }
        newLog.setEndTime(new Date());
        String taskTime = ((newLog.getEndTime().getTime() - newLog.getStartTime().getTime()) / 1000) + "s";
        newLog.setTakeTime(taskTime);
        newLog.setUpdateBy("sys");
        newLog.setUpdateDate(new Date());
        runLogBiz.updateById(newLog);
        return AjaxResult.success(newLog); // 返回成功标识，具体的见log对象的status
    }



    /**
     * RSS行记录转换为结构化数据并保存
     *
     * @param batchNo
     * @param seedObj
     * @param syndList
     * @return
     */
    private int convert2RssItem(String batchNo, RssSeedEntity seedObj, List<SyndEntry> syndList) {
        int result = 0;
        if(syndList.size() == 0) {
            return result;
        }
        Collection<RssItemEntity> list = new LinkedList<>();
        for(int s=0;s<syndList.size();s++) {
            try {
                int index = (s+1);
                SyndEntry syndObj = syndList.get(s);
                if(this.rssItemBiz.isExist(syndObj.getLink())) { // 判断URL是否已经存在
                    log.info("exist url:"+syndObj.getLink());
                    continue;
                }
                // 记录RSS行数据
                RssItemEntity newItem = new RssItemEntity();
                newItem.setSiteId(seedObj.getSiteId());
                newItem.setSeedId(seedObj.getId());
                newItem.setCategoryId(seedObj.getCategoryId());
                newItem.setCategoryName(seedObj.getCategoryName());
                newItem.setBatchNo(batchNo);
                newItem.setBizId("");
                newItem.setTitle(syndObj.getTitle().trim().length() > 250 ? syndObj.getTitle().trim().substring(0,250) : syndObj.getTitle().trim());
                newItem.setLink(syndObj.getLink() != null ? syndObj.getLink().trim() : null);
                newItem.setAuthor(syndObj.getAuthor() != null ? syndObj.getAuthor().trim() : null);
                newItem.setDescription(syndObj.getDescription() != null ? syndObj.getDescription().getValue().trim() : null);
                newItem.setPubdate(syndObj.getPublishedDate());
                // newItem.setFromJson(JSON.toJSONString(syndObj));
                newItem.setIsBuild("N");
                newItem.setIsIndex("N");
                newItem.setCreateBy("sys");
                newItem.setCreateDate(new Date());
                list.add(newItem);
            } catch(Exception e) {
                log.error("convertBySyndEntry异常",e);
            }
        }
        if(list.size() > 0) { // 批量保存
            this.rssItemBiz.saveBatch(list);
            result = list.size();
            // 释放内存
            list.clear();list=null;
        }
        return result;
    }
}
