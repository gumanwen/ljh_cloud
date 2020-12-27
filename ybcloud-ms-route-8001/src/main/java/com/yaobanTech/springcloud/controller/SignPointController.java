package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.domain.BizSignPoint;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.service.impl.SignPointServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RefreshScope
@Api(description = "签到点接口")
@RequestMapping("/api/route/signPoint")
public class SignPointController {

   /* @Value("${content}")
    private String content;*/
    @Autowired
    @Lazy
    private SignPointServiceImpl signPointService;

    @ApiOperation("保存签到点")
    @PostMapping()
    public RespBean saveSignPoint(@RequestBody BizSignPoint signPoint){
        return signPointService.saveSignPoint(signPoint);
    }

    @ApiOperation("修改签到点")
    @PutMapping()
    public RespBean updateSignPoint( @RequestBody HashMap<String,Object> param){
        return signPointService.updateSignPoint(param);
    }

    @ApiOperation("删除签到点")
    @DeleteMapping("{id}")
    public RespBean deleteSignPoint(@PathVariable("id") Integer id){
        return signPointService.deleteSignPoint(id);
    }

    @ApiOperation("查询签到点详情")
    @GetMapping("{id}")
    public RespBean findSignPoint(@PathVariable("id") Integer id){
        return signPointService.findSignPoint(id);
    }

    @ApiOperation("查询签到点列表")
    @GetMapping("findList")
    public RespBean findList(@RequestParam Integer routeId){
        return signPointService.findList(routeId);
    }
}
