package com.ananops.provider.service.impl;


import com.alibaba.fastjson.JSON;
import com.ananops.RedisKeyUtil;
import com.ananops.base.constant.AliyunMqTopicConstants;
import com.ananops.base.dto.LoginAuthDto;
import com.ananops.base.enums.ErrorCodeEnum;
import com.ananops.base.exception.BusinessException;
import com.ananops.core.support.BaseService;
import com.ananops.provider.manager.ImcTaskManager;
import com.ananops.provider.mapper.ImcInspectionItemMapper;
import com.ananops.provider.mapper.ImcInspectionTaskMapper;
import com.ananops.provider.model.domain.ImcInspectionTask;
import com.ananops.provider.model.domain.MqMessageData;
import com.ananops.provider.model.dto.ImcAddInspectionTaskDto;
import com.ananops.provider.model.dto.ImcTaskChangeStatusDto;
import com.ananops.provider.model.enums.TaskStatusEnum;
import com.ananops.provider.service.ImcInspectionTaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * Created by rongshuai on 2019/11/27 19:31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ImcInspectionTaskServiceImpl extends BaseService<ImcInspectionTask> implements ImcInspectionTaskService {
    @Resource
    ImcInspectionTaskMapper imcInspectionTaskMapper;

    @Resource
    ImcInspectionItemMapper imcInspectionItemMapper;

    @Resource
    ImcTaskManager imcTaskManager;

    /**
     * 插入一条巡检任务记录
     * @param imcAddInspectionTaskDto
     * @return
     */
    public ImcInspectionTask saveTask(ImcAddInspectionTaskDto imcAddInspectionTaskDto, LoginAuthDto loginAuthDto){
        ImcInspectionTask imcInspectionTask = new ImcInspectionTask();
        BeanUtils.copyProperties(imcAddInspectionTaskDto,imcInspectionTask);
        imcInspectionTask.setUpdateInfo(loginAuthDto);
        MqMessageData mqMessageData;
        String body = JSON.toJSONString(imcAddInspectionTaskDto);
        String topic = AliyunMqTopicConstants.MqTagEnum.UPDATE_INSPECTION_TASK.getTopic();
        String tag = AliyunMqTopicConstants.MqTagEnum.UPDATE_INSPECTION_TASK.getTag();
        if(imcInspectionTask.isNew()){
            //如果当前是新建一条任务
            Long taskId = super.generateId();
            imcInspectionTask.setId(taskId);
            String key = RedisKeyUtil.createMqKey(topic,tag,String.valueOf(taskId),body);
            mqMessageData = new MqMessageData(body, topic, tag, key);
            imcTaskManager.saveInspectionTask(mqMessageData,imcInspectionTask,true);
            //imcInspectionTaskMapper.insert(imcInspectionTask);
        }else{
            //如果当前是更新一条记录
            String key = RedisKeyUtil.createMqKey(topic,tag,String.valueOf(imcInspectionTask.getId()),body);
            mqMessageData = new MqMessageData(body, topic, tag, key);
            imcTaskManager.saveInspectionTask(mqMessageData,imcInspectionTask,false);
            //imcInspectionTaskMapper.updateByPrimaryKeySelective(imcInspectionTask);
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


    public ImcInspectionTask modifyTaskStatus(ImcTaskChangeStatusDto imcTaskChangeStatusDto, LoginAuthDto loginAuthDto){
        MqMessageData mqMessageData;
        Long taskId = imcTaskChangeStatusDto.getTaskId();
        Integer status = imcTaskChangeStatusDto.getStatus();
        ImcInspectionTask imcInspectionTask = new ImcInspectionTask();
        Example example = new Example(ImcInspectionTask.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",taskId);
        if(imcInspectionTaskMapper.selectCountByExample(example)==0){//如果当前任务不存在
            throw new BusinessException(ErrorCodeEnum.GL9999098,taskId);
        }
        //如果当前任务存在
        imcTaskChangeStatusDto.setStatusMsg(TaskStatusEnum.getStatusMsg(status));
        imcInspectionTask.setId(taskId);
        imcInspectionTask.setStatus(status);
        imcInspectionTask.setUpdateInfo(loginAuthDto);
        String body = JSON.toJSONString(imcTaskChangeStatusDto);
        String topic = AliyunMqTopicConstants.MqTagEnum.MODIFY_INSPECTION_TASK_STATUS.getTopic();
        String tag = AliyunMqTopicConstants.MqTagEnum.MODIFY_INSPECTION_TASK_STATUS.getTag();
        String key = RedisKeyUtil.createMqKey(topic,tag,String.valueOf(imcInspectionTask.getId()),body);
        mqMessageData = new MqMessageData(body, topic, tag, key);
        imcTaskManager.modifyTaskStatus(mqMessageData,imcInspectionTask);
        return imcInspectionTask;
    }
}
