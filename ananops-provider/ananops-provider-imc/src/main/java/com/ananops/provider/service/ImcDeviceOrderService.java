package com.ananops.provider.service;


import com.ananops.base.dto.LoginAuthDto;
import com.ananops.core.support.IService;
import com.ananops.provider.model.domain.ImcDeviceOrder;
import com.ananops.provider.model.dto.ImcAddDeviceOrderDto;

/**
 * Created by rongshuai on 2019/11/27 10:23
 */
public interface ImcDeviceOrderService extends IService<ImcDeviceOrder> {
    ImcDeviceOrder saveDeviceOrder(ImcAddDeviceOrderDto imcAddDeviceOrderDto, LoginAuthDto loginAuthDto);

}
