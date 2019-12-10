package com.paascloud.provider.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by rongshuai on 2019/12/5 10:27
 */
@Data
@ApiModel
public class ImcTaskChangeStatusDto implements Serializable {
    private static final long serialVersionUID = 7518743322967534899L;

    /**
     * 巡检任务ID
     */
    @ApiModelProperty(value = "巡检任务ID")
    private Long taskId;

    /**
     * 修改后的状态
     */
    @ApiModelProperty(value = "修改后的状态")
    private Integer status;

    /**
     * 巡检任务修改后的状态描述
     */
    @ApiModelProperty(value = "巡检任务修改后的状态描述")
    private String statusMsg;
}
