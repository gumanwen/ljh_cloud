package com.yaobanTech.springcloud.controller;


import com.yaobanTech.springcloud.entity.utils.RespBean;
import com.yaobanTech.springcloud.service.IInspectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private IInspectService iInspectService;

    @ApiOperation("获取计划任务列表")
    @GetMapping("/plan/getAllInspect")
    public RespBean getPlanInspect(String type){
        return iInspectService.getPlanInspect(type);
    }

    @ApiOperation("获取临时任务列表")
    @GetMapping("/plan/getTempInspect")
    public RespBean getTempInspect(String type){
        return iInspectService.getTempInspect(type);
    }

    @ApiOperation("新增临时任务")
    @PostMapping("/temp/addTempTask")
    public RespBean addTempTask(@RequestBody Map<String,Object> params){
        return iInspectService.addTempTask(params);
    }

    @ApiOperation("获取计划任务详情")
    @GetMapping("/plan/getInspectDetailById")
    public RespBean getInspectDetailById(String inspect_task_id){
        return iInspectService.getInspectDetailById(inspect_task_id);
    }

    @ApiOperation("修改计划任务详情")
    @GetMapping("/plan/updateInspectDetailById")
    public RespBean updateInspectDetailById(@RequestBody Map<String,Object> params){
        return iInspectService.updateInspectDetailById(params);
    }

    @ApiOperation("获取任务的签到点列表")
    @GetMapping("/plan/getCheckInPoints")
    public RespBean getCheckInPoints(Integer planId){
        //调用route的rest接口
        return iInspectService.getCheckInPoints(planId);
    }

    @ApiOperation("获取签到点详情")
    @GetMapping("/point/getPointDetail")
    public RespBean getPointDetail(Integer id){
        //调用route的rest接口
        return iInspectService.getPointDetail(id);
    }

    @ApiOperation("修改签到点详情")
    @PutMapping("/point/updatePoint")
    public RespBean updatePoint(@RequestBody Map<String,Object> params){
        //调用route的rest接口
        return iInspectService.updatePoint(params);
    }
}
