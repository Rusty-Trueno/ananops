/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：RoleBindActionDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * The class Grant auth role.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
public class RoleBindActionDto implements Serializable {

	private static final long serialVersionUID = -8589698204017834593L;
	/**
	 * 按钮权限
	 */
	@ApiModelProperty(value = "按钮权限")
	private Set<Long> actionIdList;
	/**
	 * 角色Id
	 */
	@ApiModelProperty(value = "角色Id")
	private Long roleId;
}
