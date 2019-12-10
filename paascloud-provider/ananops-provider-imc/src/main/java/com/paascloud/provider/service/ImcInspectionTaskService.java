package com.paascloud.provider.service;


import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.IService;
import com.paascloud.provider.model.domain.ImcInspectionTask;
import com.paascloud.provider.model.dto.ImcAddInspectionTaskDto;

/**
 * Created by rongshuai on 2019/11/27 19:29
 */
public interface ImcInspectionTaskService extends IService<ImcInspectionTask> {
    ImcInspectionTask saveTask(ImcAddInspectionTaskDto imcAddInspectionTaskDto, LoginAuthDto loginAuthDto);//创建一条巡检任务

    ImcInspectionTask getTaskByTaskId(Long taskId);//根据巡检任务的ID，获得当前巡检任务的详情


}
