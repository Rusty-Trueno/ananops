package com.ananops.provider.service;


import com.ananops.base.dto.LoginAuthDto;
import com.ananops.core.support.IService;
import com.ananops.provider.model.domain.ImcInspectionItemLog;

/**
 * Created by rongshuai on 2019/11/28 15:31
 */
public interface ImcInspectionItemLogService extends IService<ImcInspectionItemLog> {
    Integer createInspectionItemLog(ImcInspectionItemLog imcInspectionItemLog, LoginAuthDto loginAuthDto);//创建一条巡检任务子项日志
}