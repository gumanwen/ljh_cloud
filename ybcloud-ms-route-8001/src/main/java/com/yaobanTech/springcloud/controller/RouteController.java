package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.domain.BizRoute;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.service.RouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@Api(description = "巡查路线接口")
@RequestMapping("/api/route")
public class RouteController {

   /* @Value("${content}")
    private String content;*/
    @Autowired
    @Lazy
    private RouteService routeService;

    @ApiOperation("保存巡查路线")
    @PostMapping("/add")
    public RespBean saveRoute(BizRoute route){
        return routeService.saveRoute(route);
    }

    @ApiOperation("修改巡查路线")
    @PostMapping("/modify")
    public RespBean updateRoute(BizRoute route){
        return routeService.updateRoute(route);
    }

    @ApiOperation("删除巡查路线")
    @GetMapping("/remove")
    public RespBean deleteRoute(Integer id){
        return routeService.deleteRoute(id);
    }

    @ApiOperation("条件查询巡查路线")
    @GetMapping("/findList")
    public RespBean findList(String waterManagementOffice,String fixedPointInspectionType,
                             String planInspectionMileage,String createdTime,
                             String routeName,String routeCreator,
                             String routeType){
        return routeService.findCondition(waterManagementOffice,fixedPointInspectionType,planInspectionMileage,createdTime,routeName,routeCreator,routeType);
    }
}
