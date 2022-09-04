/**
 * The MIT License (MIT)
 * Copyright (c) 2012-2022 铭软科技(mingsoft.net)
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package net.mingsoft.cms.action;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rometools.rome.feed.synd.SyndEntry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.mingsoft.base.entity.ResultData;
import net.mingsoft.basic.annotation.LogAnn;
import net.mingsoft.basic.bean.EUListBean;
import net.mingsoft.basic.constant.e.BusinessTypeEnum;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.basic.util.SqlInjectionUtil;
import net.mingsoft.basic.util.StringUtil;
import net.mingsoft.cms.bean.ContentBean;
import net.mingsoft.cms.biz.*;
import net.mingsoft.cms.entity.*;
import net.mingsoft.cms.util.DateUtil;
import net.mingsoft.cms.util.RssUtil;
import net.mingsoft.mdiy.biz.IModelBiz;
import net.mingsoft.mdiy.entity.ModelEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.*;


@Api(tags={"rss数据抽取"})
@Controller("rssAction")
@RequestMapping("/back/rss") ///${ms.manager.path}
@RestController
@Slf4j
public class RssAction extends BaseAction {


	@Autowired
	private IRssItemBiz rssItemBiz;

	@Autowired
	private IRssSeedBiz rssSeedBiz;

	@Autowired
	private IRssRunLogBiz runLogBiz;


	/**
	 * 返回编辑界面content_form
	 */
	@GetMapping("/spider")
	public void test(String siteId, String id, String url){
		// 开始时间
		long stime = System.currentTimeMillis();
		QueryWrapper query = new QueryWrapper<>(new RssSeedEntity().setUrlType("RSS")).orderByAsc("create_date");
		if(StringUtils.isNotEmpty(siteId)) {
			query.eq("site_id",siteId);
		}
		if(StringUtils.isNotEmpty(id)) {
			query.eq("id",id);
		}
		if(StringUtils.isNotEmpty(url)) {
			query.eq("url",url);
		}
		List<RssSeedEntity> RssItemList = rssSeedBiz.list(query);
		if(RssItemList == null)  {
			return ;
		}
		log.info("RssItemList.size()="+RssItemList.size());
		String batchNo = DateUtil.dateToString(new Date(), "yyyyMMddhhmmss SSSSSS");
		for(int i=0;i<RssItemList.size();i++) {
			log.info("Execute index ="+(i+1));
			RssSeedEntity seedObj = RssItemList.get(i);
			// 记录运行日志
			RssRunLogEntity newLog = new RssRunLogEntity();
			newLog.setBatchNo(batchNo);
			newLog.setDatasouce("seed");
			newLog.setDatasouceId(seedObj.getId());
			newLog.setStartTime(new Date());
			newLog.setStatus("run"); // 运行中
			newLog.setCreateBy("sys");
			newLog.setCreateDate(new Date());
			runLogBiz.save(newLog);
			try {
				// 获取RSS行数据
				List<SyndEntry> syndList =  RssUtil.praseXml(seedObj.getUrl());
				log.info("syndList.size()="+syndList.size());
				for(int s=0;s<syndList.size();s++) {
					int index = (s+1);
					log.info("SyndEntry Execute index ="+index);
					SyndEntry syndObj = syndList.get(s);
					// 判断URL是否已经存在
					if(this.rssItemBiz.isExist(syndObj.getLink())) {
						log.info("exist url:"+syndObj.getLink());
						continue;
					}
					// 记录RSS行数据
					RssItemEntity newItem = new RssItemEntity();
					newItem.setSeedId(seedObj.getId());
					newItem.setBatchNo(batchNo);
					newItem.setBizId("");
					newItem.setTitle(syndObj.getTitle().trim().length() > 250 ? syndObj.getTitle().trim().substring(0,250) : syndObj.getTitle().trim());
					newItem.setLink(syndObj.getLink() != null ? syndObj.getLink().trim() : null);
					newItem.setAuthor(syndObj.getAuthor() != null ? syndObj.getAuthor().trim() : null);
					newItem.setDescription(syndObj.getDescription().getValue() != null ? syndObj.getDescription().getValue().trim() : null);
					newItem.setPubdate(syndObj.getPublishedDate());
					// newItem.setFromJson(JSON.toJSONString(syndObj));
					newItem.setIsBuild("N");
					newItem.setIsIndex("N");
					newItem.setCreateBy("sys");
					newItem.setCreateDate(new Date());
					rssItemBiz.save(newItem);
				}

				// 单个RSS网址执行完成-更新日志
				newLog.setEndTime(new Date());
				newLog.setStatus("success");
				String taskTime = ((newLog.getEndTime().getTime() - newLog.getStartTime().getTime()) / 1000) + "s";
				newLog.setTakeTime(taskTime);
				newLog.setUpdateBy("sys");
				newLog.setUpdateDate(new Date());
				runLogBiz.updateById(newLog);
			} catch (Exception e) {
				log.error("抓取异常",e);
				// 记录异常消息
				newLog.setEndTime(new Date());
				newLog.setStatus("fail");
				newLog.setMsg(ExceptionUtils.getStackTrace(e));
				String taskTime = ((newLog.getEndTime().getTime() - newLog.getStartTime().getTime()) / 1000) + "s";
				newLog.setTakeTime(taskTime);
				newLog.setUpdateBy("sys");
				newLog.setUpdateDate(new Date());
				runLogBiz.updateById(newLog);
			} finally {
				continue;
			}
		}

		// 结束时间
		long etime = System.currentTimeMillis();
		System.out.printf("执行时长：%d 秒.", (etime - stime)/1000);//执行时长：1000 毫秒.

	}


}
