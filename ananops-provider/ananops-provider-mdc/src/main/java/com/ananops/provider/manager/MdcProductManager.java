/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductManager.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.ananops.provider.manager;

import com.ananops.base.enums.ErrorCodeEnum;
import com.ananops.provider.annotation.MqProducerStore;
import com.ananops.provider.exceptions.MdcBizException;
import com.ananops.provider.mapper.MdcProductMapper;
import com.ananops.provider.model.domain.MdcProduct;
import com.ananops.provider.model.domain.MqMessageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * The class User manager.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Component
@Transactional(rollbackFor = Exception.class)
public class MdcProductManager {
	@Resource
	private MdcProductMapper mdcProductMapper;

	/**
	 * Save product.
	 *
	 * @param mqMessageData the mq message data
	 * @param product       the product
	 * @param addFlag       the add flag
	 */
	@MqProducerStore
	public void saveProduct(final MqMessageData mqMessageData, final MdcProduct product, boolean addFlag) {
		log.info("保存商品信息. mqMessageData={}, product={}", mqMessageData, product);
		if (addFlag) {
			mdcProductMapper.insertSelective(product);
		} else {
			int result = mdcProductMapper.updateByPrimaryKeySelective(product);
			if (result < 1) {
				throw new MdcBizException(ErrorCodeEnum.MDC10021022, product.getId());
			}
		}
	}

	/**
	 * Delete product.
	 *
	 * @param mqMessageData the mq message data
	 * @param productId     the product id
	 */
	@MqProducerStore
	public void deleteProduct(final MqMessageData mqMessageData, final Long productId) {
		log.info("删除商品信息. mqMessageData={}, productId={}", mqMessageData, productId);
		int result = mdcProductMapper.deleteByPrimaryKey(productId);
		if (result < 1) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021023, productId);
		}
	}
}
