package net.mingsoft.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.mingsoft.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>
 * RSS对象行记录
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rss_item")
public class RssItemEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId( value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 业务主键
     */
    @TableField("bizId")
    private String bizId;

    /**
     * 站点ID
     */
    @TableField("site_id")
    private String siteId;

    /**
     * 对象主键
     */
    @TableField("seed_id")
    private String seedId;

    /**
     * 分类ID
     */
    @TableField("categoryId")
    private String categoryId;

    /**
     * 分类名称
     */
    @TableField("categoryName")
    private String categoryName;

    /**
     * 执行批次
     */
    @TableField("batchNo")
    private String batchNo;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 副标题
     */
    @TableField("subTitle")
    private String subTitle;

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
     * 编辑
     */
    @TableField("editor")
    private String editor;

    /**
     * 关键字
     */
    @TableField("keyword")
    private String keyword;

    /**
     * 发布日期
     */
    @TableField("pubdate")
    private Date pubdate;

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
     * 缩略图路径
     */
    @TableField("thumbnail_path")
    private String thumbnailPath;

    /**
     * 缩略图文字说明
     */
    @TableField("thumbnail_desc")
    private String thumbnailDesc;

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


}
