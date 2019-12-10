package com.paascloud.provider.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by rongshuai on 2019/11/27 19:39
 */
@Data
@ApiModel
public class ImcAddInspectionTaskDto implements Serializable {
    private static final long serialVersionUID = -6922470629930578506L;
    /**
     * 巡检任务ID
     */
    @ApiModelProperty(value = "巡检任务ID")
    private Long id;
    /**
     * 甲方负责人ID
     */
    @ApiModelProperty(value = "甲方负责人ID")
    private Long principalId;

    /**
     * 服务商ID
     */
    @ApiModelProperty(value = "服务商ID")
    private Long facilitatorId;

    /**
     * 项目ID
     */
    @ApiModelProperty(value = "项目ID")
    private Long projectId;

    /**
     * 甲方位置信息
     */
    @ApiModelProperty(value = "甲方位置信息")
    private String location;

    /**
     * 任务当前状态
     */
    @ApiModelProperty(value = "任务当前状态")
    private Integer status;

    /**
     * 巡检总花费
     */
    @ApiModelProperty(value = "巡检总花费")
    private BigDecimal totalCost;

    /**
     * 巡检产生的维修维护花费
     */
    @ApiModelProperty(value = "巡检产生的维修维护花费")
    private BigDecimal maintenanceCost;

    /**
     * 计划开始时间
     */
    @ApiModelProperty(value = "计划开始时间")
    private Date scheduledStartTime;

    /**
     * 实际开始时间
     */
    @ApiModelProperty(value = "实际开始时间")
    private Date actualStartTime;

    /**
     * 计划完成时间
     */
    @ApiModelProperty(value = "计划完成时间")
    private Date scheduledFinishTime;

    /**
     * 实际结束时间
     */
    @ApiModelProperty(value = "实际结束时间")
    private Date actualFinishTime;

    /**
     * 最迟完成时间
     */
    @ApiModelProperty(value = "最迟完成时间")
    private Date deadline;

    /**
     * 巡检类型（主动发起or走合同）
     */
    @ApiModelProperty(value = "巡检类型")
    private Integer inspectionType;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
