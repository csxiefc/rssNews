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
 * 应用数据传输日志表
 * </p>
 *
 * @author xiefc
 * @since 2022-09-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("app_category_trans_log")
public class AppCategoryTransLogEntity extends BaseEntity {

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
     * 批次编号
     */
    @TableField("batchNo")
    private String batchNo;

    /**
     * 映射ID
     */
    @TableField("map_id")
    private String mapId;

    /**
     * 应用外键ID
     */
    @TableField("app_id")
    private Integer appId;

    /**
     * 站点栏目ID
     */
    @TableField("app_category_id")
    private String appCategoryId;

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
     * 最后同步时间 考虑是否pubdate，用于增量更新
     */
    @TableField("last_sync_date")
    private Date lastSyncDate;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 文件行数
     */
    @TableField("file_rows")
    private Integer fileRows;

    /**
     * 应该解析行数
     */
    @TableField("should_rows")
    private Integer shouldRows;

    /**
     * 实际解析行数
     */
    @TableField("fact_rows")
    private Integer factRows;

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
     * 错误消息
     */
    @TableField("error_msg")
    private String errorMsg;

    /**
     * 状态  成功Y、异常N
     */
    @TableField("status")
    private String status;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;




}
