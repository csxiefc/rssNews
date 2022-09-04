package net.mingsoft.cms.action.search.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
@SolrDocument
public class SearchCreateDocument {
//	@Field("idx_id")
//	private String idx_id;// 业务主键
//	@Field("idx_title")
//	private String idx_title;// 标题
//	@Field("idx_user_id")
//	private String idx_user_id;// 创建者ID
//	@Field("idx_user_name")
//	private String idx_user_name;// 创建者名称
//	@Field("idx_parent_id")
//	private String idx_parent_id;// 上级业务主键
//	@Field("idx_desc")
//	private String idx_desc;// 业务描述
//	@Field("idx_auth_value")
//	private List<String> idx_auth_value = Lists.newArrayList();// 授权值
//	@Field("idx_app_id")
//	private String idx_app_id;// 所属模块或应用ID
//	@Field("idx_attachment_id")
//	private String idx_attachment_id;// 附件ID,多个附件id以”,”分割
//	@Field("idx_date")
//	private Date idx_date;// 索引创建时间
//	@Field("id")
//	private String id;// 索引主键
//	@Field("idx_suffix")
//	private String idx_suffix;// 文件名后缀
	
	   @Field("id")
	   private String id;//索引id
	   @Field("stdd_id")
	   private String stdd_id;//标准di
	   @Field("stdd_name")
	   private String stdd_name;//标准名称
	   @Field("stdd_number")
	   private String stdd_number;//标准编号
	   @Field("stdd_category")
	   private String stdd_category;//标准类型
	   @Field("gk_dept_name")
	   private String gk_dept_name;//归口部门name
	   @Field("publish_time")
	   private Date publish_time;//发布时间
	   @Field("impl_time")
	   private Date impl_time;//实施时间
	   @Field("stdd_remark")
	   private String stdd_remark;//制修订说明
	   @Field("impl_desc")
	   private String impl_desc;//实施要点
	   @Field("monitor_desc")
	   private String monitor_desc;//监督检点
	   @Field("file_name")
	   private String file_name;//文件名
	   @Field("file_content")
	   private String file_content;//文件文本内容（用于全文索引）
	   @Field("file_id")
	   private String file_id;//附件id
	   @Field("file_url")
	   private String file_url;//附件url
	   @Field("snyc_date")
	   private String snyc_date;//同步时间
	   @Field("stdd_comp_id")
	   private String stdd_comp_id;//compId
	   @Field("stdd_level")
	   private String stdd_level;//文件级别
	   @Field("suit_scope")
	   private String suit_scope;//市县共用 1市 2 市县共用
	   @Field("gk_dept_id")
	   private String gk_dept_id;//归口id
	   @Field("publish_result")
	   private String publish_result;//是否发布
	public Object init(SearchDocument searchDocument) {
		// TODO Auto-generated method stub
		return null;
	}
}
