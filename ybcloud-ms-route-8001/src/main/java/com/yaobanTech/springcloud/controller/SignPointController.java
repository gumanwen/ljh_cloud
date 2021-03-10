package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.domain.SignPointQuery;
import com.yaobanTech.springcloud.service.impl.SignPointServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

@RestController
@RefreshScope
@Api(description = "签到点接口")
@RequestMapping("/api/route/signPoint")
public class SignPointController {

    @Autowired
    @Lazy
    private SignPointServiceImpl signPointService;

    @ApiOperation("保存签到点")
    @PostMapping("add")
    public RespBean saveSignPoint(@RequestBody HashMap<String,Object> param){
        return signPointService.saveSignPoint(param);
    }

    @ApiOperation("修改签到点")
    @PostMapping("modify")
    public RespBean updateSignPoint( @RequestBody HashMap<String,Object> param){
        return signPointService.updateSignPoint(param);
    }

    @ApiOperation("删除签到点")
    @GetMapping("remove")
    public RespBean deleteSignPoint(@RequestParam Integer id){
        return signPointService.deleteSignPoint(id);
    }

    @ApiOperation("查询签到点详情")
    @GetMapping("findSignPoint")
    public RespBean findSignPoint(@RequestParam Integer id){
        return signPointService.findSignPoint(id);
    }

    @ApiOperation("查询路线对应签到点列表")
    @GetMapping("findList")
    public RespBean findList(@RequestParam Integer routeId){
        return signPointService.findList(routeId);
    }

    @ApiOperation("查询角色所有签到点列表")
    @GetMapping("findListByUser")
    public RespBean findListByUser(HttpServletRequest request) throws UnsupportedEncodingException {
        return signPointService.findListByUser(request);
    }

    @ApiOperation("根据taskId查询签到点列表")
    @GetMapping("findListByTaskId")
    public RespBean findListByTaskId(@RequestParam Integer routeId,@RequestParam String inspectTaskId){
        return signPointService.findListByTaskId(routeId,inspectTaskId);
    }

    @ApiOperation("查询已签到签到列表")
    @GetMapping("findSignedList")
    public RespBean findSignedList(@RequestParam Integer routeId,@RequestParam String taskId){
        return signPointService.findSignedList(routeId,taskId);
    }

    @ApiOperation("新增任务签到点")
    @GetMapping("taskPoint")
    public RespBean taskPoint(@RequestParam List<String> taskIds, @RequestParam Integer routeId ){
        return signPointService.taskPoint(taskIds,routeId);
    }

    @ApiOperation("条件查询签到点")
    @PostMapping("findCondition")
    public RespBean findCondition(@RequestBody HashMap<String,Object> map){
        return signPointService.findCondition(map);
    }

}
