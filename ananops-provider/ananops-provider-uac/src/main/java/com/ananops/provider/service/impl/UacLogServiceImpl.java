package com.ananops.provider.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ananops.PublicUtil;
import com.ananops.base.dto.LoginAuthDto;
import com.ananops.core.annotation.OperationLogDto;
import com.ananops.core.support.BaseService;
import com.ananops.provider.mapper.UacLogMapper;
import com.ananops.provider.model.domain.UacAction;
import com.ananops.provider.model.domain.UacLog;
import com.ananops.provider.model.dto.log.UacLogMainDto;
import com.ananops.provider.service.OpcRpcService;
import com.ananops.provider.service.UacActionService;
import com.ananops.provider.service.UacLogService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Uac log service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacLogServiceImpl extends BaseService<UacLog> implements UacLogService {
	@Resource
	private UacLogMapper uacLogMapper;
	@Resource
	private OpcRpcService opcRpcService;
	@Resource
	private UacActionService uacActionService;

	@Override
	public int saveLog(UacLog uacLog, LoginAuthDto loginAuthDto) {
		// 根据uri 查询url对应的权限
		UacAction uacAction = uacActionService.matchesByUrl(uacLog.getRequestUrl());
		if (uacAction != null) {
			uacLog.setActionId(uacAction.getId());
			uacLog.setActionCode(uacAction.getActionCode());
			uacLog.setActionName(uacAction.getActionName());
		}
		uacLog.setUpdateInfo(loginAuthDto);
		uacLog.setId(this.generateId());
		return uacLogMapper.insertSelective(uacLog);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacLog> selectUserLogListByUserId(Long userId) {
		return uacLogMapper.selectUserLogListByUserId(userId);
	}

	@Override
	public Integer saveOperationLog(OperationLogDto operationLogDto) {
		// 根据uri 查询url对应的权限
		UacAction uacAction = uacActionService.matchesByUrl(operationLogDto.getRequestUrl());
		if (uacAction != null) {
			operationLogDto.setActionCode(uacAction.getActionCode());
			operationLogDto.setActionName(uacAction.getActionName());
			operationLogDto.setActionId(uacAction.getId());
		}
		ModelMapper modelMapper = new ModelMapper();
		UacLog uacLog = modelMapper.map(operationLogDto, UacLog.class);
		uacLog.setId(generateId());
		// 获取操作位置
		String locationById = opcRpcService.getLocationById(operationLogDto.getIp());
		uacLog.setLocation(locationById);
		return uacLogMapper.insertSelective(uacLog);
	}

	@Override
	public PageInfo queryLogListWithPage(UacLogMainDto uacLogQueryDtoPage) {
		List<Long> menuIdList = uacLogQueryDtoPage.getMenuIdList();
		Long actionId = null;
		if (PublicUtil.isNotEmpty(menuIdList)) {
			actionId = menuIdList.get(menuIdList.size() - 1);
		}
		uacLogQueryDtoPage.setMenuId(actionId);
		PageHelper.startPage(uacLogQueryDtoPage.getPageNum(), uacLogQueryDtoPage.getPageSize());
		List<UacLog> actionList = uacLogMapper.queryLogListWithPage(uacLogQueryDtoPage);
		return new PageInfo<>(actionList);
	}
}
