package com.ananops.provider.mapper;


import com.ananops.core.mybatis.MyMapper;
import com.ananops.provider.model.domain.ImcPermitsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ImcPermitsInfoMapper extends MyMapper<ImcPermitsInfo> {
}