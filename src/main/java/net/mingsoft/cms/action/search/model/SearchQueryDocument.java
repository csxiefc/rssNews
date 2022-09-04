package net.mingsoft.cms.action.search.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import lombok.Data;

@Data
@SolrDocument
public class SearchQueryDocument {

	/**
	 * 主键
	 */
	@TableId( value = "id")
	private String id;

	/**
	 * 对象主键
	 */
	@TableField("seed_id")
	private String seedId;

	/**
	 * 执行批次
	 */
	@TableField("batchNo")
	private String batchNo;

	/**
	 * 业务主键
	 */
	@TableField("bizId")
	private String bizId;

	/**
	 * 标题
	 */
	@TableField("title")
	private String title;

	/**
	 * 网址
	 */
	@TableField("link")
	private String link;

	/**
	 * 作者
	 */
	@TableField("author")
	private String author;

	/**
	 * 关键字
	 */
	@TableField("keyword")
	private String keyword;

	/**
	 * 发布日期
	 */
	@TableField("pubdate")
	private String pubdate;

	/**
	 * 摘要
	 */
	@TableField("description")
	private String description;

	/**
	 * 来源JSON
	 */
	@TableField("fromJson")
	private String fromJson;

	/**
	 * 网址内容
	 */
	@TableField("link_content")
	private String linkContent;

	/**
	 * 是否建立索引 Y是、N否
	 */
	@TableField("isIndex")
	private String isIndex;

	/**
	 * 是否静态 Y是、N否
	 */
	@TableField("isBuild")
	private String isBuild;

	/**
	 * 备注
	 */
	@TableField("remark")
	private String remark;


	/**
	 * 更新日期
	 */
	@TableField("update_date")
	private String update_date;

	/**
	 * 创建日期
	 */
	@TableField("create_date")
	private String create_date;
}
