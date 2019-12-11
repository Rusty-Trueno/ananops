/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcExceptionLogFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.ananops.provider.service.hystrix;

import com.ananops.provider.model.dto.GlobalExceptionLogDto;
import com.ananops.provider.service.MdcExceptionLogFeignApi;
import com.ananops.wrapper.Wrapper;
import org.springframework.stereotype.Component;


/**
 * The class Mdc exception log feign hystrix.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class MdcExceptionLogFeignHystrix implements MdcExceptionLogFeignApi {

	@Override
	public Wrapper saveAndSendExceptionLog(final GlobalExceptionLogDto exceptionLogDto) {
		return null;
	}
}
