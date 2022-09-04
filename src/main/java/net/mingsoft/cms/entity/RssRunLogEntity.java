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
import java.util.Date;

/**
 * <p>
 * RSS爬日志
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rss_run_log")
public class RssRunLogEntity extends BaseEntity {

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
     * 数据源 seed、seed_extend
     */
    @TableField("datasouce")
    private String datasouce;

    /**
     * 数据来源ID
     */
    @TableField("datasouce_id")
    private String datasouceId;

    /**
     * 批次编号
     */
    @TableField("batchNo")
    private String batchNo;

    /**
     * 开始时间
     */
    @TableField("startTime")
    private Date startTime;

    /**
     * 结束时间
     */
    @TableField("endTime")
    private Date endTime;

    /**
     * 耗时
     */
    @TableField("takeTime")
    private String takeTime;

    /**
     * 状态 运行中run、成功sunccess 、错误error
     */
    @TableField("status")
    private String status;

    /**
     * 消息
     */
    @TableField("msg")
    private String msg;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;




}
