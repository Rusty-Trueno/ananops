package com.ananops.provider.model.domain;

import com.ananops.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "an_imc_maintenance_task")
public class ImcMaintenanceTask extends BaseEntity {
    private static final long serialVersionUID = 3715592479801307397L;
    /**
     * 对应的巡检子项目ID
     */
    @Column(name = "inspection_item_id")
    private Long inspectionItemId;

    /**
     * 巡检查出的故障设备的ID
     */
    @Column(name = "device_id")
    private Long deviceId;

    /**
     * 故障等级
     */
    private Integer level;

    /**
     * 故障设备的位置，纬度
     */
    @Column(name = "device_latitude")
    private BigDecimal deviceLatitude;

    /**
     * 故障设备的位置，经度
     */
    @Column(name = "device_longitude")
    private BigDecimal deviceLongitude;

    /**
     * 故障描述
     */
    private String description;
}