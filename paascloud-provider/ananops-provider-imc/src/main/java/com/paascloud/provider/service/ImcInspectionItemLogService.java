package com.paascloud.provider.service;


import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.IService;
import com.paascloud.provider.model.domain.ImcInspectionItemLog;

/**
 * Created by rongshuai on 2019/11/28 15:31
 */
public interface ImcInspectionItemLogService extends IService<ImcInspectionItemLog> {
    Integer createInspectionItemLog(ImcInspectionItemLog imcInspectionItemLog, LoginAuthDto loginAuthDto);//创建一条巡检任务子项日志
}