package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.service.impl.SuggestServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RefreshScope
@Api(description = "意见接口")
@RequestMapping("/api/suggestion")
public class SuggestController {

    @Autowired
    @Lazy
    private SuggestServiceImpl suggestService;

    @ApiOperation("保存意见")
    @PostMapping("add")
    public RespBean saveHiddenDangerPoint(@RequestBody HashMap<String,Object> param,HttpServletRequest request){
        return suggestService.saveSuggestion(param,request);
    }

    @ApiOperation("修改意见")
    @PostMapping("modify")
    public RespBean updateHiddenDangerPoint( @RequestBody HashMap<String,Object> param){
        return suggestService.updateSuggestion(param);
    }

    @ApiOperation("删除意见")
    @GetMapping("remove")
    public RespBean deleteHiddenDangerPoint(@RequestParam Integer id){
        return suggestService.deleteSuggest(id);
    }

    @ApiOperation("查询意见详情")
    @GetMapping("findDetail")
    public RespBean findDetail(@RequestParam Integer id){
        return suggestService.findDetail(id);
    }

    @ApiOperation("查询外键对应所有意见")
    @GetMapping("findListByUser")
    public RespBean findListByUser(@RequestParam String fCode,HttpServletRequest request){
        return suggestService.findListByFCode(fCode,request);
    }

}
