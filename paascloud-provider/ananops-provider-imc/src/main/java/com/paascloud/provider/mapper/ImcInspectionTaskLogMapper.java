package com.paascloud.provider.mapper;


import com.paascloud.core.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import com.paascloud.provider.model.domain.ImcInspectionTaskLog;

@Mapper
@Component
public interface ImcInspectionTaskLogMapper extends MyMapper<ImcInspectionTaskLog> {
}