package com.ananops.provider.service;


import com.ananops.base.dto.LoginAuthDto;
import com.ananops.core.support.IService;
import com.ananops.provider.model.domain.ImcInspectionItem;
import com.ananops.provider.model.dto.ImcAddInspectionItemDto;

import java.util.List;

/**
 * Created by rongshuai on 2019/11/28 10:13
 */
public interface ImcInspectionItemService extends IService<ImcInspectionItem> {
    ImcInspectionItem saveInspectionItem(ImcAddInspectionItemDto imcAddInspectionItemDto, LoginAuthDto loginAuthDto);//新增一条巡检子项记录

    List<ImcInspectionItem> getAllItemByTaskId(Long taskId);//根据巡检任务ID，获取当前任务下的所有巡检任务子项

    ImcInspectionItem getItemByItemId(Long itemId);//根据巡检任务子项ID，获取对应的巡检任务子项
}
