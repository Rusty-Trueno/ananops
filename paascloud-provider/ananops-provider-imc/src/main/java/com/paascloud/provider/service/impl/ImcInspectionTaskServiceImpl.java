package com.paascloud.provider.service.impl;


import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.mapper.ImcInspectionItemMapper;
import com.paascloud.provider.mapper.ImcInspectionTaskMapper;
import com.paascloud.provider.model.domain.ImcInspectionTask;
import com.paascloud.provider.model.dto.ImcAddInspectionTaskDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.paascloud.provider.service.ImcInspectionTaskService;

import javax.annotation.Resource;

/**
 * Created by rongshuai on 2019/11/27 19:31
 */
@Service
public class ImcInspectionTaskServiceImpl extends BaseService<ImcInspectionTask> implements ImcInspectionTaskService {
    @Resource
    ImcInspectionTaskMapper imcInspectionTaskMapper;

    @Resource
    ImcInspectionItemMapper imcInspectionItemMapper;

    /**
     * 插入一条巡检任务记录
     * @param imcAddInspectionTaskDto
     * @return
     */
    public ImcInspectionTask saveTask(ImcAddInspectionTaskDto imcAddInspectionTaskDto, LoginAuthDto loginAuthDto){
        ImcInspectionTask imcInspectionTask = new ImcInspectionTask();
        BeanUtils.copyProperties(imcAddInspectionTaskDto,imcInspectionTask);
        imcInspectionTask.setUpdateInfo(loginAuthDto);
        if(imcInspectionTask.isNew()){
            //如果当前是新建一条任务
            Long taskId = super.generateId();
            imcInspectionTask.setId(taskId);
            imcInspectionTaskMapper.insert(imcInspectionTask);
        }else{
            //如果当前是更新一条记录
            imcInspectionTaskMapper.updateByPrimaryKeySelective(imcInspectionTask);
        }
        return imcInspectionTask;
    }

    /**
     *
     * @param taskId
     * @return
     */
    public ImcInspectionTask getTaskByTaskId(Long taskId){//根据巡检任务的ID，获取巡检任务的详情
        return imcInspectionTaskMapper.selectByPrimaryKey(taskId);
    }


}
