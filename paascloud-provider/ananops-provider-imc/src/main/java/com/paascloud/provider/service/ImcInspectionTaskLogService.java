package com.paascloud.provider.service;


import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.IService;
import com.paascloud.provider.model.domain.ImcInspectionTaskLog;

/**
 * Created by rongshuai on 2019/11/28 15:29
 */
public interface ImcInspectionTaskLogService extends IService<ImcInspectionTaskLog> {
    Integer createInspectionTaskLog(ImcInspectionTaskLog imcInspectionTaskLog, LoginAuthDto loginAuthDto);//创建一条巡检任务的日志
}
