package com.ananops.provider.mapper;


import com.ananops.core.mybatis.MyMapper;
import com.ananops.provider.model.domain.ImcInspectionTaskLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ImcInspectionTaskLogMapper extends MyMapper<ImcInspectionTaskLog> {
}