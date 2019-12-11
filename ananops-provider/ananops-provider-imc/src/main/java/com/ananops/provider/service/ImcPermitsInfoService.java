package com.ananops.provider.service;


import com.ananops.base.dto.LoginAuthDto;
import com.ananops.core.support.IService;
import com.ananops.provider.model.domain.ImcPermitsInfo;
import com.ananops.provider.model.dto.ImcAddPermitsInfoDto;

/**
 * Created by rongshuai on 2019/12/4 13:01
 */
public interface ImcPermitsInfoService extends IService<ImcPermitsInfo> {
    ImcPermitsInfo saveImcPermitsInfo(ImcAddPermitsInfoDto imcAddPermitsInfoDto, LoginAuthDto loginAuthDto);//添加一条巡检项目对应的证照记录

}
