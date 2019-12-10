package com.paascloud.provider.web.frontend;


import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.domain.ImcDeviceOrder;
import com.paascloud.provider.model.dto.ImcAddDeviceOrderDto;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.paascloud.provider.core.annotation.AnanLogAnnotation;
import com.paascloud.provider.service.ImcDeviceOrderService;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by rongshuai on 2019/12/3 8:41
 */
@RestController
@RequestMapping(value = "/inspectionDeviceOrder",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - ImcInspectionDeviceOrderService",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ImcDeviceOrderController extends BaseController{
    @Resource
    ImcDeviceOrderService imcDeviceOrderService;

    @PostMapping(value = "/save")
    @ApiOperation(httpMethod = "POST",value = "编辑备品备件订单")
    @AnanLogAnnotation
    public Wrapper<ImcDeviceOrder> saveDeviceOrder(@ApiParam(name = "saveDeviceOrder",value = "编辑备品备件订单")@RequestBody ImcAddDeviceOrderDto imcAddDeviceOrderDto){
        LoginAuthDto loginAuthDto = getLoginAuthDto();
        return WrapMapper.ok(imcDeviceOrderService.saveDeviceOrder(imcAddDeviceOrderDto,loginAuthDto));
    }

    @GetMapping(value = "/getDeviceOrderByItemId/{itemId}")
    @ApiOperation(httpMethod = "GET",value = "获取巡检任务子项对应的备品备件订单")
    public Wrapper<List<ImcDeviceOrder>> getDeviceOrderByItemId(@PathVariable Long itemId){
        Example example = new Example(ImcDeviceOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("inspectionItemId",itemId);
        List<ImcDeviceOrder> deviceOrders = imcDeviceOrderService.selectByExample(example);
        return WrapMapper.ok(deviceOrders);
    }

    @GetMapping(value = "/getDeviceOrderByTaskId/{taskId}")
    @ApiOperation(httpMethod = "GET",value = "获取巡检任务对应的备品备件订单")
    public Wrapper<List<ImcDeviceOrder>> getDeviceOrderByTaskId(@PathVariable Long taskId){
        Example example = new Example(ImcDeviceOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("inspectionItemId",taskId);
        List<ImcDeviceOrder> deviceOrders = imcDeviceOrderService.selectByExample(example);
        return WrapMapper.ok(deviceOrders);
    }

}
