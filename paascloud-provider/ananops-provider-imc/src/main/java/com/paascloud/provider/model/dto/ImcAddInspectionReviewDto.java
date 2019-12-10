package com.paascloud.provider.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by rongshuai on 2019/12/3 12:31
 */
@Data
@ApiModel
public class ImcAddInspectionReviewDto implements Serializable {
    private static final long serialVersionUID = 8922490434318356676L;
    /**
     * 对应的任务ID
     */
    @ApiModelProperty(value = "被巡检设备对应的任务ID")
    private Long inspectionTaskId;

    /**
     * 项目负责人
     */
    @ApiModelProperty(value = "项目负责人ID")
    private Long principalId;

    /**
     * 服务评级
     */
    @ApiModelProperty(value = "服务评级")
    private Integer score;

    /**
     * 服务评论
     */
    @ApiModelProperty(value = "服务评论")
    private String contents;

}
