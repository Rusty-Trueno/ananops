package com.ananops.provider.service.impl;


import com.ananops.base.dto.LoginAuthDto;
import com.ananops.core.support.BaseService;
import com.ananops.provider.mapper.ImcInspectionItemLogMapper;
import com.ananops.provider.model.domain.ImcInspectionItemLog;
import com.ananops.provider.service.ImcInspectionItemLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by rongshuai on 2019/11/28 15:31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ImcInspectionItemLogServiceImpl extends BaseService<ImcInspectionItemLog> implements ImcInspectionItemLogService {
    @Resource
    ImcInspectionItemLogMapper imcInspectionItemLogMapper;

    public Integer createInspectionItemLog(ImcInspectionItemLog imcInspectionItemLog, LoginAuthDto loginAuthDto){
        Long itemLogId = super.generateId();
        imcInspectionItemLog.setId(itemLogId);
        imcInspectionItemLog.setUpdateInfo(loginAuthDto);
        return imcInspectionItemLogMapper.insert(imcInspectionItemLog);
    }
}
