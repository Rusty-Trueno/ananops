package com.paascloud.provider.service.impl;


import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.mapper.ImcInspectionTaskLogMapper;
import com.paascloud.provider.model.domain.ImcInspectionTaskLog;
import com.paascloud.provider.service.ImcInspectionTaskLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by rongshuai on 2019/11/28 15:30
 */
@Service
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
