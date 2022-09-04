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
import java.time.LocalDateTime;

/**
 * <p>
 * RSS网站链接扩展
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rss_seed_extend")
public class RssSeedExtendEntity extends BaseEntity {

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
     * 所属种子
     */
    @TableField("seed_id")
    private String seedId;

    /**
     * 父级ID
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 层级
     */
    @TableField("rlevle")
    private Integer rlevle;

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
     * 网址类型RSS、网站
     */
    @TableField("urlType")
    private String urlType;

    /**
     * 网址
     */
    @TableField("url")
    private String url;

    /**
     * 内容解析类
     */
    @TableField("crackClazz")
    private String crackClazz;

    /**
     * 权重
     */
    @TableField("weight")
    private BigDecimal weight;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;




}
