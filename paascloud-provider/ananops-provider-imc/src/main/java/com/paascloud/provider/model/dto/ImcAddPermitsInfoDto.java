package com.paascloud.provider.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by rongshuai on 2019/12/4 21:05
 */
@Data
@ApiModel
public class ImcAddPermitsInfoDto implements Serializable {
    private static final long serialVersionUID = 1980853158109481952L;
    /**
     * 证照信息ID
     */
    @ApiModelProperty(value = "证照信息ID")
    private Long id;

    /**
     * 证照对应的项目ID
     */
    @ApiModelProperty(value = "证照对应的项目ID")
    private Long projectId;

    /**
     * 证照的ID
     */
    @ApiModelProperty(value = "证照的ID")
    private Long permitsId;

    /**
     * 证照名字
     */
    @ApiModelProperty(value = "证照名字")
    private String permitsName;

    /**
     * 证照描述
     */
    @ApiModelProperty(value = "证照描述")
    private String description;
}
