package com.ananops.provider.web.frontend;


import com.ananops.base.dto.LoginAuthDto;
import com.ananops.core.support.BaseController;
import com.ananops.provider.core.annotation.AnanLogAnnotation;
import com.ananops.provider.model.domain.ImcInspectionTask;
import com.ananops.provider.model.dto.ImcAddInspectionTaskDto;
import com.ananops.provider.model.dto.ImcTaskChangeStatusDto;
import com.ananops.provider.service.ImcInspectionTaskService;
import com.ananops.wrapper.WrapMapper;
import com.ananops.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by rongshuai on 2019/11/27 19:28
 */
@RestController
@RequestMapping(value = "/inspectionTask",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - ImcInspectionTask",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ImcInspectionTaskController extends BaseController {
    @Resource
    ImcInspectionTaskService imcInspectionTaskService;

    @PostMapping(value = "/save")
    @ApiOperation(httpMethod = "POST",value = "编辑巡检任务记录")
    @AnanLogAnnotation
    public Wrapper<ImcInspectionTask> saveInspectionTask(@ApiParam(name = "saveInspectionTask",value = "编辑巡检任务记录")@RequestBody ImcAddInspectionTaskDto imcAddInspectionTaskDto){
        LoginAuthDto loginAuthDto = getLoginAuthDto();
        return WrapMapper.ok(imcInspectionTaskService.saveTask(imcAddInspectionTaskDto,loginAuthDto));
    }

    @GetMapping(value = "/getTaskByTaskId/{taskId}")
    @ApiOperation(httpMethod = "GET",value = "根据任务的ID，获取当前的任务详情")
    public Wrapper<ImcInspectionTask> getTaskByTaskId(@PathVariable Long taskId){
        ImcInspectionTask imcInspectionTask = imcInspectionTaskService.getTaskByTaskId(taskId);
        return WrapMapper.ok(imcInspectionTask);
    }

    @PostMapping(value = "/modifyTaskStatusByTaskId/{taskId}")
    @ApiOperation(httpMethod = "POST",value = "更改巡检任务的状态")
    @AnanLogAnnotation
    public Wrapper<ImcTaskChangeStatusDto> modifyTaskStatusByTaskId(@ApiParam(name = "modifyTaskStatus",value = "根据巡检任务的ID修改巡检任务的状态")@RequestBody ImcTaskChangeStatusDto imcTaskChangeStatusDto){
        LoginAuthDto loginAuthDto = getLoginAuthDto();
        imcInspectionTaskService.modifyTaskStatus(imcTaskChangeStatusDto,loginAuthDto);
        return WrapMapper.ok(imcTaskChangeStatusDto);
    }

}
