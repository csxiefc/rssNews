package net.mingsoft.cms.action.search.model;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class SearchResult {
	private String code = "200";
	private boolean flag = true;
	private String message = "success";
	private long totalNum;
	private int seconds;
	private List<SearchQueryDocument> list = Lists.newArrayList();
}
