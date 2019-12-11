/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqTopicController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.ananops.provider.web.frontend;

import com.ananops.base.dto.LoginAuthDto;
import com.ananops.base.dto.UpdateStatusDto;
import com.ananops.core.annotation.LogAnnotation;
import com.ananops.core.support.BaseController;
import com.ananops.provider.model.domain.TpcMqTopic;
import com.ananops.provider.model.vo.TpcMqTopicVo;
import com.ananops.provider.service.TpcMqTopicService;
import com.ananops.wrapper.WrapMapper;
import com.ananops.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * The class Tpc mq topic controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/topic", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TpcMqTopicController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMqTopicController extends BaseController {

	@Resource
	private TpcMqTopicService tpcMqTopicService;


	/**
	 * 查询MQ topic列表.
	 *
	 * @param tpcMqTopic the tpc mq topic
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryTopicListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询MQ topic列表")
	public Wrapper<List<TpcMqTopicVo>> queryTopicListWithPage(@ApiParam(name = "topic", value = "MQ-Topic") @RequestBody TpcMqTopic tpcMqTopic) {

		logger.info("查询角色列表tpcMqTopicQuery={}", tpcMqTopic);
		List<TpcMqTopicVo> list = tpcMqTopicService.listWithPage(tpcMqTopic);
		return WrapMapper.ok(list);
	}

	/**
	 * 修改topic状态.
	 *
	 * @param updateStatusDto the update status dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatusById")
	@ApiOperation(httpMethod = "POST", value = "修改topic状态")
	@LogAnnotation
	public Wrapper modifyTopicStatusById(@ApiParam(value = "修改topic状态") @RequestBody UpdateStatusDto updateStatusDto) {
		logger.info("修改topic状态 updateStatusDto={}", updateStatusDto);
		Long roleId = updateStatusDto.getId();

		LoginAuthDto loginAuthDto = getLoginAuthDto();

		TpcMqTopic topic = new TpcMqTopic();
		topic.setId(roleId);
		topic.setStatus(updateStatusDto.getStatus());
		topic.setUpdateInfo(loginAuthDto);

		int result = tpcMqTopicService.update(topic);
		return super.handleResult(result);
	}
}
