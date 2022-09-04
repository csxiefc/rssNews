package net.mingsoft.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.mingsoft.base.entity.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * RSS对象行记录-价值元素
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rss_item_element")
public class RssItemElementEntity extends BaseEntity {

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
     * 对象主键
     */
    @TableField("obj_id")
    private String objId;

    /**
     * 行主键
     */
    @TableField("item_id")
    private String itemId;

    /**
     * 类型 关键字KEY、图片PIC
     */
    @TableField("type")
    private String type;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;




}
