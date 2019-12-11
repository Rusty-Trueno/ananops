package com.ananops.provider.web.frontend;


import com.ananops.base.dto.LoginAuthDto;
import com.ananops.core.support.BaseController;
import com.ananops.provider.model.domain.ImcPermitsInfo;
import com.ananops.provider.model.dto.ImcAddPermitsInfoDto;
import com.ananops.provider.service.ImcPermitsInfoService;
import com.ananops.wrapper.WrapMapper;
import com.ananops.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by rongshuai on 2019/12/4 21:12
 */
@RestController
@RequestMapping(value = "/permitsInfo",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - ImcPermitsInfo",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ImcPermitsInfoController extends BaseController {
    @Resource
    ImcPermitsInfoService imcPermitsInfoService;

    @PostMapping(value = "/save")
    @ApiOperation(httpMethod = "POST",value = "编辑证照信息")
    public Wrapper<ImcPermitsInfo> savePermitsInfo(@ApiParam(name = "savePermitsInfo",value = "编辑证照信息")@RequestBody ImcAddPermitsInfoDto imcAddPermitsInfoDto){
        LoginAuthDto loginAuthDto = getLoginAuthDto();
        return WrapMapper.ok(imcPermitsInfoService.saveImcPermitsInfo(imcAddPermitsInfoDto,loginAuthDto));
    }

    @GetMapping(value = "/getPermitsInfoByProjectId/{projectId}")
    @ApiOperation(httpMethod = "GET",value = "获取项目对应的全部证照信息")
    public Wrapper<List<ImcPermitsInfo>> getPermitsInfoByProjectId(@PathVariable Long projectId){
        Example example = new Example(ImcPermitsInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("projectId",projectId);
        List<ImcPermitsInfo> imcPermitsInfos = imcPermitsInfoService.selectByExample(example);
        return WrapMapper.ok(imcPermitsInfos);
    }
}
