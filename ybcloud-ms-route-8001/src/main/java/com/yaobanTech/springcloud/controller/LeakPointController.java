package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.service.impl.LeakPointServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RefreshScope
@Api(description = "漏点接口")
@RequestMapping("/api/route/leakPoint")
public class LeakPointController {

    @Autowired
    @Lazy
    private LeakPointServiceImpl leakPointService;

    @ApiOperation("保存漏点")
    @PostMapping("add")
    public RespBean saveHiddenDangerPoint(@RequestBody HashMap<String,Object> param,HttpServletRequest request){
        return leakPointService.saveLeakPoint(param,request);
    }

    @ApiOperation("修改漏点")
    @PostMapping("modify")
    public RespBean updateHiddenDangerPoint( @RequestBody HashMap<String,Object> param){
        return leakPointService.updateBizLeakPointEntity(param);
    }

    @ApiOperation("删除漏点")
    @GetMapping("remove")
    public RespBean deleteHiddenDangerPoint(@RequestParam Integer id){
        return leakPointService.deleteBizLeakPointEntity(id);
    }

    @ApiOperation("查询漏点详情")
    @GetMapping("findDetail")
    public RespBean findDetail(@RequestParam Integer id){
        return leakPointService.findDetail(id);
    }

    @ApiOperation("查询角色所有漏点列表")
    @GetMapping("findListByUser")
    public RespBean findListByUser(HttpServletRequest request){
        return leakPointService.findListByUser(request);
    }

}
