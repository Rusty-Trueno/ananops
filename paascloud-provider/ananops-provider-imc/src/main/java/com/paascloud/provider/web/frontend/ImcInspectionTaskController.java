package com.paascloud.provider.web.frontend;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.base.exception.BusinessException;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.domain.ImcInspectionTask;
import com.paascloud.provider.model.dto.ImcAddInspectionTaskDto;
import com.paascloud.provider.model.dto.ImcTaskChangeStatusDto;
import com.paascloud.provider.model.enums.TaskStatusEnum;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.paascloud.provider.core.annotation.AnanLogAnnotation;
import com.paascloud.provider.service.ImcInspectionTaskService;
import tk.mybatis.mapper.entity.Example;

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
        Long taskId = imcTaskChangeStatusDto.getTaskId();
        Integer status = imcTaskChangeStatusDto.getStatus();
        ImcInspectionTask imcInspectionTask = new ImcInspectionTask();
        Example example = new Example(ImcInspectionTask.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",taskId);
        if(imcInspectionTaskService.selectCountByExample(example)==0){//如果当前任务不存在
            throw new BusinessException(ErrorCodeEnum.GL9999098,taskId);
        }
        //如果当前任务存在
        imcTaskChangeStatusDto.setStatusMsg(TaskStatusEnum.getStatusMsg(status));
        imcInspectionTask.setId(taskId);
        imcInspectionTask.setStatus(status);
        LoginAuthDto loginAuthDto = getLoginAuthDto();
        imcInspectionTask.setUpdateInfo(loginAuthDto);
        imcInspectionTaskService.update(imcInspectionTask);
        return WrapMapper.ok(imcTaskChangeStatusDto);
    }

}
