package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import com.paascloud.provider.model.domain.ImcInspectionItem;

@Mapper
@Component
public interface ImcInspectionItemMapper extends MyMapper<ImcInspectionItem> {
}