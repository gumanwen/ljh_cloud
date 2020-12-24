package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.domain.BizRoute;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.service.RouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RefreshScope
@Api(description = "巡查路线接口")
@RequestMapping("/api/route/way")
public class RouteController {

    @Autowired
    @Lazy
    private RouteService routeService;

    @ApiOperation("保存巡查路线")
    @PostMapping()
    public RespBean saveRoute(@RequestBody HashMap<String,Object> param){
        return routeService.saveRoute(param);
    }

    @ApiOperation("修改巡查路线")
    @PutMapping("{id}")
    public RespBean updateRoute(@PathVariable("id")Integer id,@RequestBody BizRoute route){
        return routeService.updateRoute(id,route);
    }

    @ApiOperation("删除巡查路线")
    @DeleteMapping("{id}")
    public RespBean deleteRoute(@PathVariable("id")Integer id){
        return routeService.deleteRoute(id);
    }

    @ApiOperation("条件查询巡查路线")
    @GetMapping()
    public RespBean findCondition(@RequestParam String waterManagementOffice,String pointInspectionType,
                             String planInspectionMileage,String createdTime,
                             String routeName,String routeCreator,
                             String routeType){
        return routeService.findCondition(waterManagementOffice,pointInspectionType,planInspectionMileage,createdTime,routeName,routeCreator,routeType);
    }

    @ApiOperation("查询所有路线信息")
    @GetMapping("findAll")
    public RespBean findAll(){
        return routeService.findAll();
    }

    @ApiOperation("查询定点巡查类型枚举")
    @GetMapping("findEnumMenu")
    public RespBean findEnumMenu(@RequestParam String mode){
        return routeService.findEnumMenu(mode);
    }
}
