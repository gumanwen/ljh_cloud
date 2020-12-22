package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.domain.BizSignPoint;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.service.SignPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

@RestController
@RefreshScope
@Api(description = "签到点接口")
@RequestMapping("/api/route/signPoint")
public class SignPointController {

   /* @Value("${content}")
    private String content;*/
    @Autowired
    @Lazy
    private SignPointService signPointService;

    @ApiOperation("保存签到点")
    @PostMapping()
    public RespBean saveRoute(@RequestBody BizSignPoint signPoint){
        return signPointService.saveSignPoint(signPoint);
    }

    @ApiOperation("修改签到点")
    @PutMapping("{id}")
    public RespBean updateRoute(@PathVariable("id") Integer id,@RequestBody BizSignPoint signPoint){
        return signPointService.updateSignPoint(id,signPoint);
    }

    @ApiOperation("删除签到点")
    @DeleteMapping("{id}")
    public RespBean deleteRoute(@PathVariable("id") Integer id){
        return signPointService.deleteSignPoint(id);
    }

//    @ApiOperation("条件查询签到点")
//    @GetMapping()
//    public RespBean findList(String waterManagementOffice,String fixedPointInspectionType,
//                             String planInspectionMileage,String createdTime,
//                             String routeName,String routeCreator,
//                             String routeType){
//        return signPointService.findCondition(waterManagementOffice,fixedPointInspectionType,planInspectionMileage,createdTime,routeName,routeCreator,routeType);
//    }
}
