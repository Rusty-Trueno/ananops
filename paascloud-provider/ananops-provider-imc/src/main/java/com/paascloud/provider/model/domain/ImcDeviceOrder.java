package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "imc_device_order")
public class ImcDeviceOrder extends BaseEntity {
    private static final long serialVersionUID = -266164296926669966L;
    /**
     * 对应的巡检子项目的ID
     */
    @Column(name = "inspection_item_id")
    private Long inspectionItemId;

    /**
     * 对应的巡检任务的ID
     */
    @Column(name = "inspection_task_id")
    private Long inspectionTaskId;

    /**
     * 设备的ID
     */
    @Column(name = "device_id")
    private Long deviceId;

    /**
     * 当前订单花费
     */
    private BigDecimal cost;

    /**
     * 设备类型
     */
    @Column(name = "device_type")
    private String deviceType;

    /**
     * 设别生产商
     */
    private String manufacture;

    /**
     * 设备型号
     */
    @Column(name = "device_model")
    private String deviceModel;


}