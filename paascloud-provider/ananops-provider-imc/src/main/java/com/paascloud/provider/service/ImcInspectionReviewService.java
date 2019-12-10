package com.paascloud.provider.service;


import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.IService;
import com.paascloud.provider.model.domain.ImcInspectionReview;

/**
 * Created by rongshuai on 2019/12/3 9:11
 */
public interface ImcInspectionReviewService extends IService<ImcInspectionReview> {
    ImcInspectionReview addInspectionReview(ImcInspectionReview imcInspectionReview, LoginAuthDto loginAuthDto);

    ImcInspectionReview getInspectionReviewByTaskId(Long taskId);//根据任务ID，获取当前任务对应的评论
}
