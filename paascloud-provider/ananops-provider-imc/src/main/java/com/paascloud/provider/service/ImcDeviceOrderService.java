package com.paascloud.provider.service;


import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.IService;
import com.paascloud.provider.model.domain.ImcDeviceOrder;
import com.paascloud.provider.model.dto.ImcAddDeviceOrderDto;

/**
 * Created by rongshuai on 2019/11/27 10:23
 */
public interface ImcDeviceOrderService extends IService<ImcDeviceOrder> {
    ImcDeviceOrder saveDeviceOrder(ImcAddDeviceOrderDto imcAddDeviceOrderDto, LoginAuthDto loginAuthDto);

}
