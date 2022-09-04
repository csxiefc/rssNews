package net.mingsoft.cms.action;

import net.mingsoft.cms.action.search.model.*;
import net.mingsoft.cms.action.search.service.SearchService;
import net.mingsoft.cms.biz.IRssSearchLogBiz;
import net.mingsoft.cms.entity.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/solr/search", produces = "application/json;charset=UTF-8")
public class SolrSearchController {

	@Autowired
	SearchService searchService;

	@Autowired
	IRssSearchLogBiz rssSearchLogBiz;

	@PostMapping(value = "query")
	@ResponseBody
	public SearchResult SearchDocument(@RequestBody SearchQuery searchQuery) {
		return searchService.SearchDocument(searchQuery);
	}

	/**
	 * 承接前端的查询搜索服务
	 *
	 * @param key
	 * @param start
	 * @param rows
	 * @param request
	 * @return
	 */
	@GetMapping(value = "keyQuery")
	@ResponseBody
	public AjaxResult<SearchResult> keyQuery(String key, int start, int rows, HttpServletRequest request) {
		// 记录查询日志
		this.rssSearchLogBiz.saveLog(request,"Y");
		SearchQuery newQuery = new SearchQuery();
		newQuery.setStart(start);
		newQuery.setRows(rows);
		// 过滤
		List<FilterParam> filterParamList = new ArrayList();
		newQuery.setFilter(filterParamList);
		FilterParam newFilterParam = new FilterParam();
		if(StringUtils.isNotEmpty(key)) {
			// 标题
			newFilterParam.setName("title");
			newFilterParam.setValue(key);
			// 描述
			FilterParam newFilterParam2 = new FilterParam();
			newFilterParam2.setName("description");
			newFilterParam2.setValue(key);
			filterParamList.add(newFilterParam2);
		} else {
			newFilterParam.setName("*");
			newFilterParam.setValue("*");
		}
		filterParamList.add(newFilterParam);
		// 排序
		OrderParam newOrderParam = new OrderParam();
		newOrderParam.setName("pubdate");
		newOrderParam.setOrder("desc");
		List<OrderParam> orderParamList = new ArrayList<>();
		orderParamList.add(newOrderParam);
		newQuery.setSort(orderParamList);
		// 执行查询
		return AjaxResult.success(searchService.SearchDocument(newQuery));
	}

	@PostMapping(value = "")
	@ResponseBody
	public SearchResult createDocument(@RequestBody SearchDocument SearchDocument) {
		if (StringUtils.isBlank(SearchDocument.getId()) 
				|| StringUtils.isBlank(SearchDocument.getStdd_id())) {
			return new SearchResult();
		}
		return searchService.createDocument(SearchDocument);
	}

	@GetMapping(value = "del/{id}")
	@ResponseBody
	public SearchResult deleteDocument(@PathVariable("id") String id) {
		return searchService.deleteDocument(id);
	}
}
