package com.ananops.provider.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by rongshuai on 2019/12/3 8:22
 */
@Data
@ApiModel
public class ImcAddDeviceOrderDto implements Serializable {
    private static final long serialVersionUID = -7255977012891559528L;
    /**
     * 备品备件订单对应的ID
     */
    @ApiModelProperty(value = "备品备件订单对应的ID")
    private Long id;
    /**
     * 对应的巡检任务ID
     */
    @ApiModelProperty(value = "被巡检设备对应的任务ID")
    private Long inspectionTaskId;

    /**
     * 对应的巡检任务子项ID
     */
    @ApiModelProperty(value = "被巡检设备对应的任务子项ID")
    private Long inspectionItemId;

    /**
     * 设备类型
     */
    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    /**
     * 设备生产商
     */
    @ApiModelProperty(value = "设备生产商")
    private String manufacture;

    /**
     * 设备型号
     */
    @ApiModelProperty(value = "设备型号")
    private String deviceModel;

    /**
     * 当前订单的花费
     */
    @ApiModelProperty(value = "当前订单的花费")
    private BigDecimal cost;

    /**
     * 设备的ID
     */
    @ApiModelProperty(value = "被巡检的设备的ID")
    private Long deviceId;

}
