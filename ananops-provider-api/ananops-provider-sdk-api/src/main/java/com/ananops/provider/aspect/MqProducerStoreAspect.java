/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MqProducerStoreAspect.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.ananops.provider.aspect;

import com.ananops.base.enums.ErrorCodeEnum;
import com.ananops.provider.annotation.MqProducerStore;
import com.ananops.provider.exceptions.TpcBizException;
import com.ananops.provider.model.domain.MqMessageData;
import com.ananops.provider.model.enums.DelayLevelEnum;
import com.ananops.provider.model.enums.MqSendTypeEnum;
import com.ananops.provider.service.MqMessageService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;

import javax.annotation.Resource;
import java.lang.reflect.Method;


/**
 * The class Mq producer store aspect.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Aspect
public class MqProducerStoreAspect {
	@Resource
	private MqMessageService mqMessageService;

	@Value("${ananops.aliyun.rocketMq.producerGroup}")
	private String producerGroup;

	@Resource
	private TaskExecutor taskExecutor;

	/**
	 * Add exe time annotation pointcut.
	 */
	@Pointcut("@annotation(com.ananops.provider.annotation.MqProducerStore)")
	public void mqProducerStoreAnnotationPointcut() {

	}

	/**
	 * Add exe time method object.
	 *
	 * @param joinPoint the join point
	 *
	 * @return the object
	 */
	@Around(value = "mqProducerStoreAnnotationPointcut()")
	public Object processMqProducerStoreJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("processMqProducerStoreJoinPoint - 线程id={}", Thread.currentThread().getId());
		Object result;
		Object[] args = joinPoint.getArgs();
		MqProducerStore annotation = getAnnotation(joinPoint);
		MqSendTypeEnum type = annotation.sendType();
		int orderType = annotation.orderType().orderType();
		DelayLevelEnum delayLevelEnum = annotation.delayLevel();
		if (args.length == 0) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050005);
		}
		MqMessageData domain = null;
		for (Object object : args) {
			if (object instanceof MqMessageData) {
				domain = (MqMessageData) object;
				break;
			}
		}

		if (domain == null) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050005);
		}

		domain.setOrderType(orderType);
		domain.setProducerGroup(producerGroup);
		//1 等待确认
		if (type == MqSendTypeEnum.WAIT_CONFIRM) {
			if (delayLevelEnum != DelayLevelEnum.ZERO) {
				domain.setDelayLevel(delayLevelEnum.delayLevel());
			}
			//发送待确认消息到可靠消息系统
			//本地服务消息落地，可靠消息服务中心也持久化预发送消息 但是不发送
			mqMessageService.saveWaitConfirmMessage(domain); //只持久化未发送
		}
		result = joinPoint.proceed();//返回注解方法 执行业务即保存用户 下面发送到tpc
		if (type == MqSendTypeEnum.SAVE_AND_SEND) {
			mqMessageService.saveAndSendMessage(domain);
		} else if (type == MqSendTypeEnum.DIRECT_SEND) {
			mqMessageService.directSendMessage(domain);
		} else {
			final MqMessageData finalDomain = domain;
			taskExecutor.execute(() -> mqMessageService.confirmAndSendMessage(finalDomain.getMessageKey()));
		}
		return result;
	}

	private static MqProducerStore getAnnotation(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		return method.getAnnotation(MqProducerStore.class);
	}
}
