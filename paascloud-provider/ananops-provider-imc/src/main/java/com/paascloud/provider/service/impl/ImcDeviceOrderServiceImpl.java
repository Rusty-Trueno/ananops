package com.paascloud.provider.service.impl;


import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.base.exception.BusinessException;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.mapper.ImcDeviceOrderMapper;
import com.paascloud.provider.mapper.ImcInspectionItemMapper;
import com.paascloud.provider.mapper.ImcInspectionTaskMapper;
import com.paascloud.provider.model.domain.ImcDeviceOrder;
import com.paascloud.provider.model.domain.ImcInspectionItem;
import com.paascloud.provider.model.domain.ImcInspectionTask;
import com.paascloud.provider.model.dto.ImcAddDeviceOrderDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.paascloud.provider.service.ImcDeviceOrderService;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by rongshuai on 2019/11/27 10:23
 */
@Service
public class ImcDeviceOrderServiceImpl extends BaseService<ImcDeviceOrder> implements ImcDeviceOrderService {
    @Resource
    private ImcDeviceOrderMapper imcDeviceOrderMapper;

    @Resource
    private ImcInspectionTaskMapper imcInspectionTaskMapper;

    @Resource
    private ImcInspectionItemMapper imcInspectionItemMapper;

    public ImcDeviceOrder saveDeviceOrder(ImcAddDeviceOrderDto imcAddDeviceOrderDto, LoginAuthDto loginAuthDto){//编辑备品备件订单
        ImcDeviceOrder imcDeviceOrder = new ImcDeviceOrder();
        BeanUtils.copyProperties(imcAddDeviceOrderDto,imcDeviceOrder);
        imcDeviceOrder.setUpdateInfo(loginAuthDto);
        Long inspectionTaskId = imcDeviceOrder.getInspectionTaskId();//获取当前订单对应的巡检任务ID
        Long inspectionItemId = imcDeviceOrder.getInspectionItemId();//获取当前订单对应的巡检任务子项ID
        Example example1 = new Example(ImcInspectionTask.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("id",inspectionTaskId);
        if(imcInspectionTaskMapper.selectCountByExample(example1)==0){//如果备品备件订单对应的巡检任务不存在
            throw new BusinessException(ErrorCodeEnum.GL9999098,inspectionTaskId);
        }
        List<ImcInspectionTask> imcInspectionTasks = imcInspectionTaskMapper.selectByExample(example1);
        ImcInspectionTask imcInspectionTask = imcInspectionTasks.get(0);
        Example example2 = new Example(ImcInspectionItem.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("id",inspectionItemId);
        if(imcInspectionItemMapper.selectCountByExample(example2)==0){//如果备品备件订单对应的巡检任务子项不存在
            throw new BusinessException(ErrorCodeEnum.GL9999097,inspectionItemId);
        }
        List<ImcInspectionItem> imcInspectionItems = imcInspectionItemMapper.selectByExample(example2);
        ImcInspectionItem imcInspectionItem = imcInspectionItems.get(0);
        if(imcInspectionItem.getInspectionTaskId() != imcInspectionTask.getId()){
            throw new BusinessException(ErrorCodeEnum.GL9999097,inspectionItemId);
        }
        if(imcDeviceOrder.isNew()){//如果需要新增一条备品备件订单
            Long deviceOrderId = super.generateId();
            imcDeviceOrder.setId(deviceOrderId);
            imcDeviceOrderMapper.insert(imcDeviceOrder);
        }else{//如果需要修改已经存在的备品备件订单
            imcDeviceOrderMapper.updateByPrimaryKeySelective(imcDeviceOrder);//只更新变化的字段
        }

        return imcDeviceOrder;
    }

}
