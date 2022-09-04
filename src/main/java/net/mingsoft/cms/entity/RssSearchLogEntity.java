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
 * RSS搜索行为记录
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rss_search_log")
public class RssSearchLogEntity extends BaseEntity {

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
     * 请求ID
     */
    @TableField("requestId")
    private String requestId;

    /**
     * 搜索内容
     */
    @TableField("content")
    private String content;

    /**
     * IP地址
     */
    @TableField("ip")
    private String ip;

    /**
     * 所属位置
     */
    @TableField("posname")
    private String posname;

    /**
     * 浏览器
     */
    @TableField("browser")
    private String browser;

    /**
     * 状态 Y成功 N异常
     */
    @TableField("status")
    private String status;

    /**
     * 引用
     */
    @TableField("referer")
    private String referer;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
