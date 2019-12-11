/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpcGaodeFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.ananops.provider.service;

import com.ananops.provider.model.dto.gaode.GaodeLocation;
import com.ananops.provider.service.hystrix.OpcGaodeFeignApiHystrix;
import com.ananops.security.feign.OAuth2FeignAutoConfiguration;
import com.ananops.wrapper.Wrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The interface Opc gao feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "ananops-provider-opc", configuration = OAuth2FeignAutoConfiguration.class, fallback = OpcGaodeFeignApiHystrix.class)
public interface OpcGaodeFeignApi {

	/**
	 * IP定位.
	 *
	 * @param ipAddr the ip addr
	 *
	 * @return the location by ip addr
	 */
	@PostMapping(value = "/api/auth/getLocationByIpAddr")
	Wrapper<GaodeLocation> getLocationByIpAddr(@RequestParam("ipAddr") String ipAddr);
}
