package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import com.paascloud.provider.model.domain.ImcMaintenanceTask;

@Mapper
@Component
public interface ImcMaintenanceTaskMapper extends MyMapper<ImcMaintenanceTask> {
}