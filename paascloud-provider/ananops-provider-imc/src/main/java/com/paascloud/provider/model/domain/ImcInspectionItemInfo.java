package com.paascloud.provider.model.domain;


import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "imc_inspection_item_info")
public class ImcInspectionItemInfo extends BaseEntity {
    private static final long serialVersionUID = -2789308361479691169L;
    /**
     * 对应的项目ID
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 设备ID
     */
    @Column(name = "device_id")
    private Long deviceId;

    /**
     * 设别类型
     */
    @Column(name = "device_type")
    private String deviceType;

    /**
     * 设备位置，纬度
     */
    @Column(name = "device_latitude")
    private BigDecimal deviceLatitude;

    /**
     * 设别位置，经度
     */
    @Column(name = "device_longitude")
    private BigDecimal deviceLongitude;

    /**
     * 合同上规定的巡检频率
     */
    @Column(name = "inspect_frequency")
    private Integer inspectFrequency;

}