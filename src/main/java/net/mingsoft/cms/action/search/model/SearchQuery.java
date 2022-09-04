package net.mingsoft.cms.action.search.model;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class SearchQuery {

	private List<FilterParam> filter = Lists.newArrayList();
	private List<OrderParam> sort = Lists.newArrayList();
	private int start = 0;
	private int rows = 10;
}
