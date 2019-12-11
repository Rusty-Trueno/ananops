package com.ananops.provider.service.impl;

import com.ananops.base.dto.LoginAuthDto;
import com.ananops.core.support.BaseService;
import com.ananops.provider.mapper.ImcInspectionTaskLogMapper;
import com.ananops.provider.model.domain.ImcInspectionTaskLog;
import com.ananops.provider.service.ImcInspectionTaskLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by rongshuai on 2019/11/28 15:30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ImcInspectionTaskLogServiceImpl extends BaseService<ImcInspectionTaskLog> implements ImcInspectionTaskLogService {
    @Resource
    ImcInspectionTaskLogMapper imcInspectionTaskLogMapper;

    public Integer createInspectionTaskLog(ImcInspectionTaskLog imcInspectionTaskLog, LoginAuthDto loginAuthDto){//创建一条巡检任务的日志
        Long taskLogId = super.generateId();
        imcInspectionTaskLog.setId(taskLogId);
        imcInspectionTaskLog.setUpdateInfo(loginAuthDto);
        return imcInspectionTaskLogMapper.insert(imcInspectionTaskLog);
    }
}
