package com.ananops.provider.service;


import com.ananops.base.dto.LoginAuthDto;
import com.ananops.core.support.IService;
import com.ananops.provider.model.domain.ImcInspectionTask;
import com.ananops.provider.model.dto.ImcAddInspectionTaskDto;
import com.ananops.provider.model.dto.ImcTaskChangeStatusDto;

/**
 * Created by rongshuai on 2019/11/27 19:29
 */
public interface ImcInspectionTaskService extends IService<ImcInspectionTask> {
    ImcInspectionTask saveTask(ImcAddInspectionTaskDto imcAddInspectionTaskDto, LoginAuthDto loginAuthDto);//创建一条巡检任务

    ImcInspectionTask getTaskByTaskId(Long taskId);//根据巡检任务的ID，获得当前巡检任务的详情

    ImcInspectionTask modifyTaskStatus(ImcTaskChangeStatusDto imcTaskChangeStatusDto, LoginAuthDto loginAuthDto);//修改巡检任务的状态
}
