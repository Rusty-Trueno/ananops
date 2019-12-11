/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.ananops.provider.service;

import com.ananops.provider.model.dto.OrderDto;
import com.ananops.provider.service.hystrix.OmcOrderFeignHystrix;
import com.ananops.security.feign.OAuth2FeignAutoConfiguration;
import com.ananops.wrapper.Wrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The interface Omc order feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "ananops-provider-omc", configuration = OAuth2FeignAutoConfiguration.class, fallback = OmcOrderFeignHystrix.class)
public interface OmcOrderFeignApi {
	/**
	 * Update order by id wrapper.
	 *
	 * @param order the order
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/order/updateOrderById")
	Wrapper updateOrderById(@RequestBody OrderDto order);
}
