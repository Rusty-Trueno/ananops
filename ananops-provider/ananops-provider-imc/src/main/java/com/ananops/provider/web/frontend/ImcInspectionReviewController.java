package com.ananops.provider.web.frontend;


import com.ananops.base.dto.LoginAuthDto;
import com.ananops.core.support.BaseController;
import com.ananops.provider.core.annotation.AnanLogAnnotation;
import com.ananops.provider.model.domain.ImcInspectionReview;
import com.ananops.provider.model.dto.ImcAddInspectionReviewDto;
import com.ananops.provider.service.ImcInspectionReviewService;
import com.ananops.wrapper.WrapMapper;
import com.ananops.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by rongshuai on 2019/12/3 12:43
 */
@RestController
@RequestMapping(value = "/inspectionReview",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - ImcInspectionReviewService",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ImcInspectionReviewController extends BaseController {
    @Resource
    ImcInspectionReviewService imcInspectionReviewService;

    @PostMapping(value = "/save")
    @ApiOperation(httpMethod = "POST",value = "编辑当前巡检任务对应用户评价")
    @AnanLogAnnotation
    public Wrapper<ImcInspectionReview> saveInspectionReview(@ApiParam(name = "saveInspectionReview",value = "新增一条当前巡检任务对应用户评价")@RequestBody ImcAddInspectionReviewDto imcAddInspectionReviewDto){
        ImcInspectionReview imcInspectionReview = new ImcInspectionReview();
        BeanUtils.copyProperties(imcAddInspectionReviewDto,imcInspectionReview);
        LoginAuthDto loginAuthDto = getLoginAuthDto();
        return WrapMapper.ok(imcInspectionReviewService.addInspectionReview(imcInspectionReview,loginAuthDto));
    }

    @GetMapping(value = "/getReviewByTaskId/{taskId}")
    @ApiOperation(httpMethod = "GET",value = "根据巡检任务ID，获取巡检任务对应的评论")
    public Wrapper<ImcInspectionReview> getReviewByTaskId(@PathVariable Long taskId){
        ImcInspectionReview imcInspectionReview = imcInspectionReviewService.getInspectionReviewByTaskId(taskId);
        return WrapMapper.ok(imcInspectionReview);
    }

}
