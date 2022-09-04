package net.mingsoft.cms.action.search.service;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import net.mingsoft.cms.action.search.model.*;
import net.mingsoft.cms.action.search.utils.BeanUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SearchService {

	@Autowired
	private SolrClient solrClient;

	public SearchResult SearchDocument(SearchQuery searchQuery) {
		SearchResult searchResult = new SearchResult();
		SolrQuery query = new SolrQuery();
    	query.setStart(searchQuery.getStart());
		query.setRows(searchQuery.getRows());
		query.set("wt", "json");
		try {
			// 高亮配置
			String[] lightNames = { "title","description" }; // 设置需要高亮的域
			query.setParam("hl", "true");
			query.setParam("hl.fl", lightNames);
			query.setHighlightSimplePre("<font color='red'>");
			query.setHighlightSimplePost("</font>");
			int num = 0;
			String keyword = "";
			for (FilterParam filterParam : searchQuery.getFilter()) {
				if (num > 0) {
					keyword =keyword+ " OR " + filterParam.toString(); // AND
				} else {
					keyword = filterParam.toString();
					num++;
				}
			}
			if(!keyword.equals("")){
				query.set("q", keyword);
			}else{
				query.set("q", "*");
			}
			
			if (searchQuery.getSort() != null && searchQuery.getSort().size() > 0) {
				for (OrderParam orderParam : searchQuery.getSort()) {
					query.addSort(orderParam.getName(), SolrQuery.ORDER.valueOf(orderParam.getOrder()));
				}
			}
			QueryResponse response = solrClient.query(query);
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			SolrDocumentList documents = response.getResults(); // 获取匹配结果
			searchResult.setSeconds(response.getQTime());
			searchResult.setTotalNum(documents.getNumFound());
			if (searchResult.getTotalNum() > 0) {
				for (SolrDocument document : documents) {
					SearchDocument searchDocument = new SearchDocument();
					// documents中存放了匹配的所有数据（未高亮），而highlighting中存放了高亮匹配的数据（高亮）
					// 通过id主键获取到id值，在highlighting中通过id值获取对应的高亮数据
					Map<String, List<String>> listMap = highlighting.get(document.getFieldValue("id").toString());
					for (int i = 0; i < lightNames.length; i++) {
						if (listMap.get(lightNames[i]) != null) {
							// 根据设置的高亮域，将documents中未高亮的域的值替换为高亮的值
							document.setField(lightNames[i], listMap.get(lightNames[i]).get(0));
						}
					}
					searchResult.getList().add(transferBean(document));
					if (log.isDebugEnabled()) {
						log.debug(document.toString());
					}

				}

			}
		} catch (Exception e) {
			searchResult.setMessage(e.getMessage());
			searchResult.setFlag(false);
			log.error("query===" + JSON.toJSONString(searchQuery), e);
		}
		return searchResult;
	}

	SearchQueryDocument transferBean(SolrDocument document) {
		SearchQueryDocument searchQueryDocument = new SearchQueryDocument();
		try {
			BeanUtils.copyProperties(searchQueryDocument,document);
		} catch(Exception e) {}
		return searchQueryDocument;
	}

	public SearchResult createDocument(SearchDocument searchDocument) {
		SearchResult searchResult = new SearchResult();
		SearchCreateDocument searchCreateDocument = new SearchCreateDocument();
		try {
			UpdateResponse response = solrClient.addBean(searchCreateDocument.init(searchDocument));
			int staus = response.getStatus();
			if (staus != 0) {
				searchResult.setFlag(false);
				searchResult.setMessage(response.getException().getMessage());
				solrClient.rollback();
			}
			solrClient.commit();
		} catch (Exception ex) {
			searchResult.setMessage(ex.getMessage());
			searchResult.setFlag(false);
			log.error("create===" + JSON.toJSONString(searchDocument), ex);
		}
		return searchResult;
	}

	public SearchResult deleteDocument(String id) {
		SearchResult searchResult = new SearchResult();
		try {
			UpdateResponse response = solrClient.deleteById(id);
			int staus = response.getStatus();
			if (staus != 0) {
				searchResult.setFlag(false);
				searchResult.setMessage(response.getException().getMessage());
				solrClient.rollback();

			}
			solrClient.commit();
		} catch (Exception ex) {
			searchResult.setMessage(ex.getMessage());
			searchResult.setFlag(false);
			log.error("create===" + id, ex);
		}
		return searchResult;
	}

}
