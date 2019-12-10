package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "imc_inspection_review")
public class ImcInspectionReview extends BaseEntity {
    private static final long serialVersionUID = -7587052877469835674L;
    /**
     * 对应的巡检任务ID
     */
    @Column(name = "inspection_task_id")
    private Long inspectionTaskId;

    /**
     * 项目负责人ID
     */
    @Column(name = "principal_id")
    private Long principalId;

    /**
     * 服务评级
     */
    private Integer score;

    /**
     * 服务评论
     */
    private String contents;

}