package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.service.InspectService;
import com.yaobanTech.springcloud.service.PlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@RestController
@RefreshScope
@Api(description = "巡查计划接口")
@RequestMapping("/api/plan")
public class PlanController {

    @Autowired
    @Lazy
    private PlanService planService;

    @Autowired
    @Lazy
    private InspectService inspectService;

    @ApiOperation("保存巡查计划")
    @PostMapping("add")
    public RespBean savePlan(@RequestBody HashMap<String, Object> param, HttpServletRequest request) {
        return planService.savePlan(param,request);
    }

    @ApiOperation("修改巡查计划")
    @PostMapping("modify")
    public RespBean updatePlan(@RequestBody HashMap<String, Object> param) {
        return planService.updatePlan(param);
    }

    @ApiOperation("删除巡查计划")
    @GetMapping("remove")
    public RespBean deletePlan(@RequestParam Integer id) {
        return planService.deletePlan(id);
    }

    @ApiOperation("查询路线Id")
    @GetMapping("findPlanId")
    public RespBean findPlanId(@RequestParam Integer id) {
        return planService.findPlanId(id);
    }

    @ApiOperation("查询计划名称和id")
    @GetMapping("findSelection")
    public RespBean findSelection(@RequestParam Integer routeId){
        return planService.findSelection(routeId);
    }


    @ApiOperation("查询计划列表")
    @GetMapping("findAll")
    public RespBean findAll(HttpServletRequest request) throws UnsupportedEncodingException {
        return planService.findAll(request);
    }

    @ApiOperation("查询计划详情")
    @GetMapping("findById")
    public RespBean findById(@RequestParam Integer id) throws UnsupportedEncodingException {
        return planService.findById(id);
    }

    @ApiOperation("查询路线对应的所有计划")
    @GetMapping("findByRouteId")
    public RespBean findByRouteId(@RequestParam Integer routeId) {
        return planService.findByRouteId(routeId);
    }

    @ApiOperation("查询任务详情列表")
    @GetMapping("findInspectListById")
    public RespBean findInspectListById(@RequestParam Integer id) {
        return inspectService.findInspectListById(id);
    }

    @ApiOperation("查询定点巡查类型枚举")
    @GetMapping("findEnumMenu")
    public RespBean findEnumMenu(@RequestParam String mode) {
        return planService.findEnumMenu(mode);
    }

    @ApiOperation("查询单个枚举对象")
    @GetMapping("findEnum")
    public RespBean findSEnum(@RequestParam String code){
        return planService.findEnum(code);
    }

    @ApiOperation("条件查询")
    @GetMapping("findConditon")
    public RespBean findCondition(@RequestParam String routeName,@RequestParam String waterManagementOffice,
                                  @RequestParam String planPorid,@RequestParam String planType,
                                  @RequestParam String startTimeOfPCT,@RequestParam String endTimeOfPCT,
                                  @RequestParam String startTimeOfPST,@RequestParam String endTimeOfPST,
                                  @RequestParam String startTimeOfPET,@RequestParam String endTimeOfPET,
                                  HttpServletRequest request) throws UnsupportedEncodingException {
        return planService.findCondition(routeName,waterManagementOffice,planPorid,planType,startTimeOfPCT,endTimeOfPCT,startTimeOfPST,endTimeOfPST,startTimeOfPET,endTimeOfPET,request);
    }

    @ApiOperation("计划审核")
    @GetMapping("examine")
    public RespBean examinePlan(@RequestParam Integer id,@RequestParam String status) {
        return planService.examinePlan(id,status);
    }

}
