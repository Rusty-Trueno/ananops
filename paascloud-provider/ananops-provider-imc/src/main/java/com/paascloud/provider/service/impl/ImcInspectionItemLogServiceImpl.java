package com.paascloud.provider.service.impl;


import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.mapper.ImcInspectionItemLogMapper;
import com.paascloud.provider.model.domain.ImcInspectionItemLog;
import org.springframework.stereotype.Service;
import com.paascloud.provider.service.ImcInspectionItemLogService;

import javax.annotation.Resource;

/**
 * Created by rongshuai on 2019/11/28 15:31
 */
@Service
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
