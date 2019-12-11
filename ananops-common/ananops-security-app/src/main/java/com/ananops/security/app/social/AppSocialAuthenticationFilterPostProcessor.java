/*
 * Copyright (c) 2018. ananops.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AppSocialAuthenticationFilterPostProcessor.java
 * 创建人：刘兆明
 * 联系方式：ananops.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.ananops.security.app.social;


import com.ananops.security.core.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * The type App social authentication filter post processor.
 * @author ananops
 */
@Component
public class AppSocialAuthenticationFilterPostProcessor implements SocialAuthenticationFilterPostProcessor{

	private final AuthenticationSuccessHandler pcAuthenticationSuccessHandler;

	@Autowired
	public AppSocialAuthenticationFilterPostProcessor(AuthenticationSuccessHandler pcAuthenticationSuccessHandler) {
		this.pcAuthenticationSuccessHandler = pcAuthenticationSuccessHandler;
	}

	@Override
	public void process(final SocialAuthenticationFilter socialAuthenticationFilter) {
		socialAuthenticationFilter.setAuthenticationSuccessHandler(pcAuthenticationSuccessHandler);
	}
}
