package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.domain.BizPlan;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.service.PlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RefreshScope
@Api(description = "巡查计划接口")
@RequestMapping("/api/plan")
public class PlanController {

    @Autowired
    @Lazy
    private PlanService planService;

    @ApiOperation("保存巡查计划")
    @PostMapping()
    public RespBean savePlan(@RequestBody HashMap<String, Object> param) {
        return planService.savePlan(param);
    }

    @ApiOperation("修改巡查计划")
    @PutMapping("{id}")
    public RespBean updatePlan(@PathVariable("id") Integer id, @RequestBody BizPlan plan) {
        return planService.updatePlan(id, plan);
    }

    @ApiOperation("删除巡查计划")
    @DeleteMapping("{id}")
    public RespBean deletePlan(@PathVariable("id") Integer id) {
        return planService.deletePlan(id);
    }

    @ApiOperation("查询路线Id")
    @GetMapping("{id}")
    public RespBean findPlanId(@PathVariable("id") Integer id) {
        return planService.findPlanId(id);
    }

//    @ApiOperation("条件查询巡查路线")
//    @GetMapping()
//    public RespBean findCondition(@RequestParam String waterManagementOffice,String pointInspectionType,
//                                  String planInspectionMileage,String createdTime,
//                                  String routeName,String routeCreator,
//                                  String routeType){
//        return planService.findCondition(waterManagementOffice,pointInspectionType,planInspectionMileage,createdTime,routeName,routeCreator,routeType);
//    }

    @ApiOperation("查询所有计划信息")
    @GetMapping("findAll")
    public RespBean findAll() {
        return planService.findAll();
    }

    @ApiOperation("查询定点巡查类型枚举")
    @GetMapping("findEnumMenu")
    public RespBean findEnumMenu(@RequestParam String mode) {
        return planService.findEnumMenu(mode);
    }
}
