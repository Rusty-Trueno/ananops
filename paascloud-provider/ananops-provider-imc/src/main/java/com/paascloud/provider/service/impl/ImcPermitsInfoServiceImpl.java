package com.paascloud.provider.service.impl;


import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.mapper.ImcPermitsInfoMapper;
import com.paascloud.provider.model.domain.ImcPermitsInfo;
import com.paascloud.provider.model.dto.ImcAddPermitsInfoDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.paascloud.provider.service.ImcPermitsInfoService;

import javax.annotation.Resource;

/**
 * Created by rongshuai on 2019/12/4 13:02
 */
@Service
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
