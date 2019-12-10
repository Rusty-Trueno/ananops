package com.paascloud.provider.web.frontend;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.domain.ImcPermitsInfo;
import com.paascloud.provider.model.dto.ImcAddPermitsInfoDto;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.paascloud.provider.service.ImcPermitsInfoService;
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
