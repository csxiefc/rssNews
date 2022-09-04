package net.mingsoft.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.mingsoft.base.entity.BaseEntity;

/**
 * <p>
 * RSS信息分类
 * </p>
 *
 * @author xiefc
 * @since 2022-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rss_category")
public class RssCategoryEntity extends BaseEntity {

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
     * 分类编码
     */
    @TableField("code")
    private String code;

    /**
     * 分类名称
     */
    @TableField("name")
    private String name;

    /**
     * 父分类ID
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 父分类编码
     */
    @TableField("parent_code")
    private String parentCode;

    /**
     * 父分类名称
     */
    @TableField("parent_name")
    private String parentName;

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
     * 备注
     */
    @TableField("remark")
    private String remark;


}
