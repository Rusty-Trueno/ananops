package com.ananops.provider.service.impl;


import com.ananops.base.dto.LoginAuthDto;
import com.ananops.base.enums.ErrorCodeEnum;
import com.ananops.base.exception.BusinessException;
import com.ananops.core.support.BaseService;
import com.ananops.provider.mapper.ImcInspectionReviewMapper;
import com.ananops.provider.mapper.ImcInspectionTaskMapper;
import com.ananops.provider.model.domain.ImcInspectionReview;
import com.ananops.provider.model.domain.ImcInspectionTask;
import com.ananops.provider.service.ImcInspectionReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * Created by rongshuai on 2019/12/3 9:11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ImcInspectionReviewServiceImpl extends BaseService<ImcInspectionReview> implements ImcInspectionReviewService {
    @Resource
    ImcInspectionReviewMapper imcInspectionReviewMapper;

    @Resource
    ImcInspectionTaskMapper imcInspectionTaskMapper;

    public ImcInspectionReview addInspectionReview(ImcInspectionReview imcInspectionReview, LoginAuthDto loginAuthDto) {
        imcInspectionReview.setUpdateInfo(loginAuthDto);
        Long taskId = imcInspectionReview.getInspectionTaskId();
        Example example1 = new Example(ImcInspectionReview.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("inspectionTaskId",taskId);
        Example example2 = new Example(ImcInspectionTask.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("id",taskId);
        if(imcInspectionTaskMapper.selectCountByExample(example2)==0){
            //当前被评论的巡检任务不存在
            throw new BusinessException(ErrorCodeEnum.GL9999098,taskId);
        }
        if(imcInspectionReviewMapper.selectCountByExample(example2)>0){
            //当前巡检任务已经存在评论，不能再评论了
            throw new BusinessException(ErrorCodeEnum.GL9999099,imcInspectionReview.getInspectionTaskId());
        }
        Long reviewId = super.generateId();
        imcInspectionReview.setId(reviewId);
        imcInspectionReviewMapper.insert(imcInspectionReview);
        return imcInspectionReview;
    }

    public ImcInspectionReview getInspectionReviewByTaskId(Long taskId){//根据任务ID，获取当前任务对应的评论
        Example example = new Example(ImcInspectionReview.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("inspectionTaskId",taskId);
        Example example2 = new Example(ImcInspectionTask.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("id",taskId);
        if(imcInspectionTaskMapper.selectCountByExample(example2)==0){
            //当前被评论的巡检任务不存在
            throw new BusinessException(ErrorCodeEnum.GL9999098,taskId);
        }
        return imcInspectionReviewMapper.selectByExample(example).get(0);
    }
}
