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
import java.io.UnsupportedEncodingException;
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
    public RespBean saveRoute(@RequestBody HashMap<String,Object> param,HttpServletRequest request) throws UnsupportedEncodingException {
        return routeService.saveRoute(param,request);
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
    public RespBean findList(HttpServletRequest request) throws UnsupportedEncodingException {
        return routeService.findAll(request);
    }

    @ApiOperation("查询未删除路线列表")
    @GetMapping("findExitList")
    public RespBean findExitList(HttpServletRequest request) throws UnsupportedEncodingException {
        return routeService.findExitAll(request);
    }

    @ApiOperation("查询路线名称和id")
    @GetMapping("findSelection")
    public RespBean findSelection(@RequestParam String code){
        return routeService.findSelection(code);
    }

    @ApiOperation("查询定点巡查类型枚举")
    @GetMapping("findEnumMenu")
    public RespBean findEnumMenu(@RequestParam String mode){
        return routeService.findEnumMenu(mode);
    }

    @ApiOperation("查询单个枚举对象")
    @GetMapping("findEnum")
    public RespBean findEnum(@RequestParam String code){
        return routeService.findEnum(code);
    }

    @ApiOperation("条件查询路线信息")
    @PostMapping("findCondition")
    public RespBean findCondition(@RequestBody HashMap<String,Object> hashMap,HttpServletRequest request) throws UnsupportedEncodingException {
        return routeService.findCondition(hashMap,request);
    }

//    @ApiOperation("测试事务")
//    @GetMapping("testFeign")
//    public RespBean testFeign(@RequestParam Integer routeId){
//        return routeService.testFeign(routeId);
//    }

}
