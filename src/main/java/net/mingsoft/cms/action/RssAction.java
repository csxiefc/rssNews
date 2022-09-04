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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.mingsoft.cms.biz.IRssSeedBiz;
import net.mingsoft.cms.biz.IRssSiteBiz;
import net.mingsoft.cms.biz.impl.RssSpiderService;
import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.RssSeedEntity;
import net.mingsoft.cms.entity.RssSiteEntity;
import net.mingsoft.cms.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Api(tags={"rss数据抽取"})
@Controller("rssAction")
@RequestMapping("/back/rss") ///${ms.manager.path}
@RestController
@Slf4j
public class RssAction extends BaseAction {


	@Autowired
	private IRssSiteBiz rssSiteBiz;

	@Autowired
	private IRssSeedBiz rssSeedBiz;


	@Autowired
	private ThreadPoolTaskExecutor executor;

	@Autowired
	private RssSpiderService rssSpiderService;



	/**
	 * 返回编辑界面content_form
	 */
	@GetMapping("/spider")
	@ResponseBody
	public AjaxResult spider(String siteId, String seedId){
		AjaxResult result = null;
		// 开始时间
		long stime = System.currentTimeMillis();
		if(StringUtils.isNotEmpty(seedId)) {
			result = this.runBySeedId(seedId);
		} else if(StringUtils.isNotEmpty(siteId)) {
			result = this.runBySiteId(siteId);
		} else if(StringUtils.isEmpty(siteId) && StringUtils.isEmpty(seedId)) {
			result = this.runBySiteId(null);
		}
		// 结束时间
		long etime = System.currentTimeMillis();
		System.out.printf("执行时长：%d 秒.", (etime - stime)/1000);//执行时长：1000 毫秒.
		return result;
	}

	/**
	 * 根据RSS站点ID来解析
	 *
	 * @param id
	 * @return
	 */
	private AjaxResult runBySiteId(String id) {
		QueryWrapper query = new QueryWrapper<>(new RssSiteEntity()).orderByAsc("create_date");
		if(StringUtils.isNotEmpty(id)) {
			query.eq("id",id);
		}
		List<RssSiteEntity> list = this.rssSiteBiz.list(query);
		if(list.size() == 0) {
			return AjaxResult.error("have no suit rssSite!");
		}
		String batchNo = DateUtil.dateToString(new Date(), "yyyyMMddhhmmss SSSSSS");
		int factCount = 0;
		List<CompletableFuture<Integer>> futures = new ArrayList<>();
		for(RssSiteEntity siteObj : list) {
			//并行线程调用 提供执行效率，为每一个RSS SITE启一个线程
			futures.add(CompletableFuture.supplyAsync(() -> this.rssSpiderService.runBySiteObj(batchNo,siteObj), executor));
		}
		//等待全部完成
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
		//获取内容
		for (CompletableFuture<Integer> future : futures) {
			try {
				Integer res = future.get();
				factCount = factCount + res.intValue();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		// 释放内存
		futures.clear();futures=null;
		list.clear();list=null;
		return AjaxResult.success(DateUtil.getStringNow()+",factCount="+factCount); // 返回成功记录数
	}



	/**
	 * 根据SEED_ID来解析
	 *
	 * @param id
	 * @return
	 */
	private AjaxResult runBySeedId(String id) {
		String batchNo = DateUtil.dateToString(new Date(), "yyyyMMddhhmmss SSSSSS");
		RssSeedEntity seedObj = this.rssSeedBiz.getById(id);
		return rssSpiderService.runBySeedObj(batchNo,null,false,seedObj);
	}



}
