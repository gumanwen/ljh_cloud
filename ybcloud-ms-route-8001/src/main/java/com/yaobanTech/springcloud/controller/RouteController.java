package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.service.impl.RouteServiceImpl;
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
@Api(description = "巡查路线接口")
@RequestMapping("/api/route/way")
public class RouteController {

    @Autowired
    @Lazy
    private RouteServiceImpl routeService;

    @ApiOperation("保存巡查路线")
    @PostMapping("add")
    public RespBean saveRoute(@RequestBody HashMap<String,Object> param){
        return routeService.saveRoute(param);
    }

    @ApiOperation("修改巡查路线")
    @PostMapping("modify")
    public RespBean updateRoute(@RequestBody HashMap<String,Object> param){
        return routeService.updateRoute(param);
    }

    @ApiOperation("删除巡查路线")
    @GetMapping("remove")
    public RespBean deleteRoute(@RequestParam Integer id){
        return routeService.deleteRoute(id);
    }

    @ApiOperation("查询路线详情")
    @GetMapping("findDetail")
    public RespBean findDetail(@RequestParam Integer id){
        return routeService.findDetail(id);
    }

    @ApiOperation("查询路线列表")
    @GetMapping("findList")
    public RespBean findList(){
        return routeService.findAll();
    }

    @ApiOperation("查询路线名称和id")
    @GetMapping("findSelection")
    public RespBean findSelection(){
        return routeService.findSelection();
    }

    @ApiOperation("查询定点巡查类型枚举")
    @GetMapping("findEnumMenu")
    public RespBean findEnumMenu(@RequestParam String mode){
        return routeService.findEnumMenu(mode);
    }

}
