package com.paascloud.provider.web.frontend;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.base.exception.BusinessException;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.domain.ImcInspectionItem;
import com.paascloud.provider.model.dto.ImcAddInspectionItemDto;
import com.paascloud.provider.model.dto.ImcItemChangeStatusDto;
import com.paascloud.provider.model.enums.ItemStatusEnum;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.paascloud.provider.core.annotation.AnanLogAnnotation;
import com.paascloud.provider.service.ImcInspectionItemService;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by rongshuai on 2019/11/28 10:10
 */
@RestController
@RequestMapping(value = "/inspectionItem",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - ImcInspectionItemService",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ImcInspectionItemController extends BaseController {
    @Resource
    ImcInspectionItemService imcInspectionItemService;

    @PostMapping(value = "/save")
    @ApiOperation(httpMethod = "POST",value = "编辑巡检任务子项记录")
    @AnanLogAnnotation
    public Wrapper<ImcInspectionItem> saveInspectionItem(@ApiParam(name = "saveInspectionItem",value = "新增一条巡检任务子项记录")@RequestBody ImcAddInspectionItemDto imcAddInspectionItemDto){
        LoginAuthDto loginAuthDto = getLoginAuthDto();
        return WrapMapper.ok(imcInspectionItemService.saveInspectionItem(imcAddInspectionItemDto,loginAuthDto));
    }

    @GetMapping(value = "/getAllItemByTaskId/{taskId}")
    @ApiOperation(httpMethod = "GET",value = "根据巡检任务ID，获取其对应的全部任务子项")
    public Wrapper<List<ImcInspectionItem>> getAllItemByTaskId(@PathVariable Long taskId){
        return WrapMapper.ok(imcInspectionItemService.getAllItemByTaskId(taskId));
    }

    @GetMapping(value = "/getItemByItemId/{itemId}")
    @ApiOperation(httpMethod = "GET",value = "根据巡检任务子项的ID，获取对应的巡检任务子项")
    public Wrapper<ImcInspectionItem> getItemByItemId(@PathVariable Long itemId){
        return WrapMapper.ok(imcInspectionItemService.getItemByItemId(itemId));
    }

    @PostMapping(value = "/modifyItemStatusByItemId")
    @ApiOperation(httpMethod = "POST",value = "更改巡检任务子项的状态")
    @AnanLogAnnotation
    public Wrapper<ImcItemChangeStatusDto> modifyItemStatusByItemId(@ApiParam(name = "modifyItemStatus",value = "根据巡检任务子项ID，更改子项的状态")@RequestBody ImcItemChangeStatusDto imcItemChangeStatusDto){
        Long itemId = imcItemChangeStatusDto.getItemId();
        Integer status = imcItemChangeStatusDto.getStatus();
        Example example = new Example(ImcInspectionItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",itemId);
        if(imcInspectionItemService.selectCountByExample(example)==0){
            //如果当前巡检任务子项不存在
            throw new BusinessException(ErrorCodeEnum.GL9999097,itemId);
        }
        //如果当前巡检任务子项存在
        imcItemChangeStatusDto.setStatusMsg(ItemStatusEnum.getStatusMsg(status));
        ImcInspectionItem imcInspectionItem = new ImcInspectionItem();
        imcInspectionItem.setId(itemId);
        imcInspectionItem.setStatus(status);
        LoginAuthDto loginAuthDto = getLoginAuthDto();
        imcInspectionItem.setUpdateInfo(loginAuthDto);
        imcInspectionItemService.update(imcInspectionItem);
        return WrapMapper.ok(imcItemChangeStatusDto);
    }


}
