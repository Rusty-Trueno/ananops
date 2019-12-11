/*
 * Copyright (c) 2018. ananops.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TreeUtils.java
 * 创建人：刘兆明
 * 联系方式：ananops.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.ananops.core.support;


import com.ananops.base.dto.BaseTree;

import java.io.Serializable;

/**
 * The class Tree utils.
 *
 * @author ananops.net @gmail.com
 */
public class TreeUtils<T extends BaseTree<T, ID>, ID extends Serializable> extends AbstractTreeService<T, ID> {

}
