package net.mingsoft.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.mingsoft.base.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * RSS网站
 * </p>
 *
 * @author xiefc
 * @since 2022-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rss_site")
public class RssSiteEntity extends BaseEntity {

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
     * 网站名称
     */
    @TableField("name")
    private String name;

    /**
     * 网址
     */
    @TableField("url")
    private String url;

    /**
     * 作者
     */
    @TableField("author")
    private String author;

    /**
     * 版权
     */
    @TableField("copyright")
    private String copyright;

    /**
     * 关键字
     */
    @TableField("keywords")
    private String keywords;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

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
     * 权重
     */
    @TableField("weight")
    private BigDecimal weight;

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
     * 内容解析XPATH配置
     */
    @TableField("parse_conf")
    private String parseConf;

    /**
     * 最后发布时间
     */
    @TableField("last_pubdate")
    private Date lastPubdate;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
