/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MessageBeanConfiguration.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.ananops.provider.autoconfigure;

import com.ananops.provider.aspect.MqConsumerStoreAspect;
import com.ananops.provider.aspect.MqProducerStoreAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The class Elastic job auto configuration.
 *
 * @author paascloud.net @gmail.com
 */
@Configuration
public class MessageBeanConfiguration {
	@Bean
	@ConditionalOnExpression("${ananops.aliyun.rocketMq.reliableMessageConsumer:true}")
	public MqConsumerStoreAspect mqConsumerStoreAspect() {
		return new MqConsumerStoreAspect();
	}

	@Bean
	@ConditionalOnExpression("${ananops.aliyun.rocketMq.reliableMessageProducer:true}")
	public MqProducerStoreAspect mqProducerStoreAspect() {
		return new MqProducerStoreAspect();
	}
}
