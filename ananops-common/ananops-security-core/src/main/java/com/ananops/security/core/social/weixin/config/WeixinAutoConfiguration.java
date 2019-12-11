/*
 * Copyright (c) 2018. ananops.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：WeixinAutoConfiguration.java
 * 创建人：刘兆明
 * 联系方式：ananops.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.ananops.security.core.social.weixin.config;

import com.ananops.security.core.properties.SecurityProperties;
import com.ananops.security.core.properties.WeixinProperties;
import com.ananops.security.core.social.view.PcConnectView;
import com.ananops.security.core.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * 微信登录配置
 *
 * @author ananops.net @gmail.com
 */
@Configuration
@ConditionalOnProperty(prefix = "paascloud.security.social.weixin", name = "app-id")
public class WeixinAutoConfiguration extends SocialAutoConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	/**
	 * Create connection factory connection factory.
	 *
	 * @return the connection factory
	 */
	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
		return new WeixinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
				weixinConfig.getAppSecret());
	}

	/**
	 * Weixin connected view view.
	 *
	 * @return the view
	 */
	@Bean({"connect/weixinConnect", "connect/weixinConnected"})
	@ConditionalOnMissingBean(name = "weixinConnectedView")
	public View weixinConnectedView() {
		return new PcConnectView();
	}

}
