package com.ananops.provider.model.domain;

import com.ananops.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "an_imc_inspection_item")
public class ImcInspectionItem extends BaseEntity {
    private static final long serialVersionUID = -1997464510426371277L;
    /**
     * 被巡检的设备ID
     */
    @Column(name = "device_id")
    private Long deviceId;

    /**
     * 从属的巡检任务的ID
     */
    @Column(name = "inspection_task_id")
    private Long inspectionTaskId;

    /**
     * 被巡检的设备的状态（0.故障，1.正常）
     */
    @Column(name = "device_status")
    private Integer deviceStatus;

    /**
     * 故障描述
     */
    @Column(name = "exception_description")
    private String exceptionDescription;

    /**
     * 故障等级
     */
    @Column(name = "exception_level")
    private Integer exceptionLevel;

    /**
     * 计划开始时间
     */
    @Column(name = "scheduled_start_time")
    private Date scheduledStartTime;

    /**
     * 实际开始时间
     */
    @Column(name = "actual_start_time")
    private Date actualStartTime;

    /**
     * 计划完成时间
     */
    @Column(name = "scheduled_finish_time")
    private Date scheduledFinishTime;

    /**
     * 实际完成时间
     */
    @Column(name = "actual_finish_time")
    private Date actualFinishTime;

    /**
     * 最迟完成时间
     */
    private Date deadline;

    /**
     * 巡检产生的维修维护工单对应的id
     */
    @Column(name = "maintenance_task_id")
    private Long maintenanceTaskId;

    /**
     * 巡检结果描述
     */
    private String description;

    /**
     * 巡检状态
     */
    private Integer status;

    /**
     * 被巡检设备的位置，纬度
     */
    @Column(name = "device_latitude")
    private BigDecimal deviceLatitude;

    /**
     * 被巡检设备的位置，经度
     */
    @Column(name = "device_longitude")
    private BigDecimal deviceLongitude;

    /**
     * 被巡检设备的类型
     */
    @Column(name = "device_type")
    private String deviceType;

}