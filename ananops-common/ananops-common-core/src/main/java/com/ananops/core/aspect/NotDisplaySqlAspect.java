/*
 * Copyright (c) 2018. ananops.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：NotDisplaySqlAspect.java
 * 创建人：刘兆明
 * 联系方式：ananops.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.ananops.core.aspect;

import com.ananops.ThreadLocalMap;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * The class Not display sql aspect.
 *
 * @author ananops.net @gmail.com
 */
@Aspect
@Component
public class NotDisplaySqlAspect {
	/**
	 * The constant DISPLAY_SQL.
	 */
	public static final String DISPLAY_SQL = "DISPLAY_SQL";

	@Pointcut("@annotation(com.ananops.core.annotation.NotDisplaySql)")
	private void myPointCut() {
	}

	/**
	 * Before.
	 */
	@Before(value = "myPointCut()")
	public void before() {
		ThreadLocalMap.put(DISPLAY_SQL, Boolean.FALSE);
	}

	/**
	 * After.
	 */
	@After(value = "myPointCut()")
	public void after() {
		ThreadLocalMap.remove(DISPLAY_SQL);
	}
}
