package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.domain.BizSignPoint;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.service.SignPointService;
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
@Api(description = "签到点接口")
@RequestMapping("/api/signPoint")
public class SignPointController {

   /* @Value("${content}")
    private String content;*/
    @Autowired
    @Lazy
    private SignPointService signPointService;

    @ApiOperation("保存签到点")
    @PostMapping("/add")
    public RespBean saveRoute(BizSignPoint signPoint){
        return signPointService.saveSignPoint(signPoint);
    }

    @ApiOperation("修改签到点")
    @PostMapping("/modify")
    public RespBean updateRoute(BizSignPoint signPoint){
        return signPointService.updateSignPoint(signPoint);
    }

    @ApiOperation("删除签到点")
    @GetMapping("/remove")
    public RespBean deleteRoute(Integer id){
        return signPointService.deleteSignPoint(id);
    }

//    @ApiOperation("条件查询签到点")
//    @GetMapping("/findList")
//    public RespBean findList(String waterManagementOffice,String fixedPointInspectionType,
//                             String planInspectionMileage,String createdTime,
//                             String routeName,String routeCreator,
//                             String routeType){
//        return signPointService.findCondition(waterManagementOffice,fixedPointInspectionType,planInspectionMileage,createdTime,routeName,routeCreator,routeType);
//    }
}
