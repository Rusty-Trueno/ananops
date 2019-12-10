package com.paascloud.provider.web.frontend;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.domain.ImcInspectionReview;
import com.paascloud.provider.model.dto.ImcAddInspectionReviewDto;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.paascloud.provider.core.annotation.AnanLogAnnotation;
import com.paascloud.provider.service.ImcInspectionReviewService;

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
