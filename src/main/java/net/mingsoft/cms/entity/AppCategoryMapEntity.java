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
 * 网站栏目数据映射
 * </p>
 *
 * @author xiefc
 * @since 2022-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("app_category_map")
public class AppCategoryMapEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
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
     * 站点名称
     */
    @TableField("app_name")
    private String appName;

    /**
     * 站点栏目ID
     */
    @TableField("app_category_id")
    private Long appCategoryId;

    /**
     * 站点栏目名称
     */
    @TableField("app_category_name")
    private String appCategoryName;

    /**
     * 数据分类ID
     */
    @TableField("rss_category_id")
    private String rssCategoryId;

    /**
     * 数据分类编码
     */
    @TableField("rss_category_code")
    private String rssCategoryCode;

    /**
     * 数据分类名称
     */
    @TableField("rss_category_name")
    private String rssCategoryName;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
