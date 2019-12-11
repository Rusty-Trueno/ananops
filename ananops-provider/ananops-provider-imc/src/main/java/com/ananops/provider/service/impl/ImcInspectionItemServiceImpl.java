package com.ananops.provider.service.impl;


import com.ananops.base.dto.LoginAuthDto;
import com.ananops.base.enums.ErrorCodeEnum;
import com.ananops.base.exception.BusinessException;
import com.ananops.core.support.BaseService;
import com.ananops.provider.mapper.ImcInspectionItemMapper;
import com.ananops.provider.mapper.ImcInspectionTaskMapper;
import com.ananops.provider.model.domain.ImcInspectionItem;
import com.ananops.provider.model.domain.ImcInspectionTask;
import com.ananops.provider.model.dto.ImcAddInspectionItemDto;
import com.ananops.provider.service.ImcInspectionItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by rongshuai on 2019/11/28 10:14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ImcInspectionItemServiceImpl extends BaseService<ImcInspectionItem> implements ImcInspectionItemService {
    @Resource
    private ImcInspectionItemMapper imcInspectionItemMapper;

    @Resource
    private ImcInspectionTaskMapper imcInspectionTaskMapper;

    /**
     *
     * @param imcAddInspectionItemDto
     * @return
     */
    public ImcInspectionItem saveInspectionItem(ImcAddInspectionItemDto imcAddInspectionItemDto, LoginAuthDto loginAuthDto){//编辑巡检任务子项记录
        ImcInspectionItem imcInspectionItem = new ImcInspectionItem();
        BeanUtils.copyProperties(imcAddInspectionItemDto,imcInspectionItem);
        imcInspectionItem.setUpdateInfo(loginAuthDto);
        Long taskId = imcInspectionItem.getInspectionTaskId();
        Example example = new Example(ImcInspectionTask.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",taskId);
        List<ImcInspectionTask> imcInspectionTasks = imcInspectionTaskMapper.selectByExample(example);
        if(imcInspectionTasks.size()==0){//如果没有此巡检任务
            throw new BusinessException(ErrorCodeEnum.GL9999098,taskId);
        }
        if(imcInspectionItem.isNew()){//如果是新增一条巡检任务子项记录
            Long itemId = super.generateId();
            imcInspectionItem.setId(itemId);
            imcInspectionItemMapper.insert(imcInspectionItem);
        }else{//如果是更新已经存在的巡检任务子项
            imcInspectionItemMapper.updateByPrimaryKeySelective(imcInspectionItem);
        }
        return imcInspectionItem;
    }

    /**
     *
     * @param taskId
     * @return
     */
    public List<ImcInspectionItem> getAllItemByTaskId(Long taskId){
        Example example1 = new Example(ImcInspectionTask.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("id",taskId);
        if(imcInspectionTaskMapper.selectCountByExample(example1)==0){
            //如果查询的任务不存在
            throw new BusinessException(ErrorCodeEnum.GL9999098,taskId);
        }
        Example example2 = new Example(ImcInspectionItem.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("inspectionTaskId",taskId);
        List<ImcInspectionItem> imcInspectionItems = imcInspectionItemMapper.selectByExample(example2);
        return imcInspectionItems;
    }

    /**
     *
     * @param itemId
     * @return
     */
    public ImcInspectionItem getItemByItemId(Long itemId){
        Example example = new Example(ImcInspectionItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",itemId);
        if(imcInspectionItemMapper.selectCountByExample(example)==0){
            throw new BusinessException(ErrorCodeEnum.GL9999097,itemId);
        }
        return imcInspectionItemMapper.selectByExample(example).get(0);
    }

    public Integer setBasicInfoFromContract(){//将从合同中获取到的基本信息填写到巡检任务中
        return 1;
    }
}
