package com.yaobanTech.springcloud.controller;


import com.yaobanTech.springcloud.entity.utils.RespBean;
import com.yaobanTech.springcloud.service.IInspectService;
import com.yaobanTech.springcloud.service.feign.PlanService;
import com.yaobanTech.springcloud.service.feign.RouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lijh
 * @since 2020-12-18
 */
@Api(value = "InspectController" , tags = "巡查任务--接口")
@RestController
@RequestMapping("/api/inspect")
public class InspectController {
    @Resource
    private RouteService routeService;

    @Resource
    private PlanService planService;

    @Autowired
    private IInspectService iInspectService;

    @ApiOperation("app & web:获取计划任务列表")
    @GetMapping("/plan/getAllInspect")
    public RespBean getPlanInspect(String type) {
        return iInspectService.getPlanInspect(type);
    }

    @ApiOperation("web:根据计划编号获取计划任务列表")
    @GetMapping("/plan/getAllInspectByPlanId")
    public RespBean getAllInspectByPlanId(@RequestParam Integer planId) {
        return iInspectService.getAllInspectByPlanId(planId);
    }

    @ApiOperation("app：获取临时任务列表")
    @GetMapping("/plan/getTempInspect")
    public RespBean getTempInspect(String type) {
        return iInspectService.getTempInspect(type);
    }

    @ApiOperation("web：新增临时任务")
    @GetMapping("/temp/addTempTask")
    public RespBean addTempTask(Integer routeId,String  routeName ,String inspector,String beginTime,String endTime) {
        return iInspectService.addTempTask(routeId,routeName,inspector,beginTime,endTime);
    }

    @ApiOperation("web：新增计划任务")
    @PostMapping("/temp/addPlanTask")
    public RespBean addPlanTask(Integer routeId,String  routeName ,Integer planId,String planName) throws ParseException {
        return iInspectService.addPlanTask(routeId,routeName,planId,planName);
    }

    @ApiOperation("app & web：获取计划任务详情")
    @GetMapping("/plan/getInspectDetailById")
    public RespBean getInspectDetailById(String inspect_task_id) throws IllegalAccessException {
        return iInspectService.getInspectDetailById(inspect_task_id);
    }

    @ApiOperation("app & web：修改计划任务详情")
    @PostMapping("/plan/updateInspectDetailById")
    public RespBean updateInspectDetailById(@RequestBody Map<String, Object> params) {
        return iInspectService.updateInspectDetailById(params);
    }

    @ApiOperation("app：获取任务的签到点列表")
    @GetMapping("/plan/getCheckInPoints")
    public RespBean getCheckInPoints(Integer routeId) {
        //调用route的rest接口
        return iInspectService.getCheckInPoints(routeId);
    }

    @ApiOperation("app：获取签到点详情")
    @GetMapping("/point/getPointDetail")
    public RespBean getPointDetail(Integer id) {
        //调用route的rest接口
        return iInspectService.getPointDetail(id);
    }

    @ApiOperation("app：修改签到点详情")
    @PostMapping("/point/updatePoint")
    public RespBean updatePoint(@RequestBody Map<String, Object> params) {
        //调用route的rest接口
        return iInspectService.updatePoint(params);
    }

    @ApiOperation("openfeign：保存计划之后自动生成任务")
    @PostMapping("/task/autoCreate")
    public RespBean autoCreate(@RequestBody Map<String, Object> params) throws ParseException {
        //调用route的rest接口
        return iInspectService.autoCreate(params);
    }

    @ApiOperation("web：发起")
    @PostMapping("/task/send")
    public RespBean send(@RequestBody Map<String, Object> params) {
        //调用route的rest接口
        return iInspectService.send(params);
    }

    @ApiOperation("web：获取路线下拉列表")
    @GetMapping("/route/findSelection")
    public RespBean findSelection() {
        return routeService.findSelection();
    }

    @ApiOperation("web：获取计划下拉列表")
    @GetMapping("/plan/findSelection")
    public RespBean findplanSelection() {
        return planService.findSelection();
    }
}

