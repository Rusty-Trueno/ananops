package com.ananops.provider.service.impl;


import com.ananops.base.dto.LoginAuthDto;
import com.ananops.core.support.BaseService;
import com.ananops.provider.mapper.ImcPermitsInfoMapper;
import com.ananops.provider.model.domain.ImcPermitsInfo;
import com.ananops.provider.model.dto.ImcAddPermitsInfoDto;
import com.ananops.provider.service.ImcPermitsInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by rongshuai on 2019/12/4 13:02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ImcPermitsInfoServiceImpl extends BaseService<ImcPermitsInfo> implements ImcPermitsInfoService {
    @Resource
    ImcPermitsInfoMapper imcPermitsInfoMapper;

    public ImcPermitsInfo saveImcPermitsInfo(ImcAddPermitsInfoDto imcAddPermitsInfoDto, LoginAuthDto loginAuthDto){
        ImcPermitsInfo imcPermitsInfo = new ImcPermitsInfo();
        BeanUtils.copyProperties(imcAddPermitsInfoDto,imcPermitsInfo);
        imcPermitsInfo.setUpdateInfo(loginAuthDto);
        if(imcPermitsInfo.isNew()){
            //如果当前的需求是新增一条证照信息记录
            Long permitsInfoId = super.generateId();
            imcPermitsInfo.setId(permitsInfoId);
            imcPermitsInfoMapper.insert(imcPermitsInfo);
        }else{
            //如果当前是更新原有的证照记录中的某些字段
            imcPermitsInfoMapper.updateByPrimaryKeySelective(imcPermitsInfo);
        }
        return imcPermitsInfo;
    }
}
