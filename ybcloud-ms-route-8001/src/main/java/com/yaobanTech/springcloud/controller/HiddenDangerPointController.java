package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.domain.HiddenDangerPointQuery;
import com.yaobanTech.springcloud.domain.LeakPointQuery;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.service.impl.HiddenDangerPointServiceImpl;
import com.yaobanTech.springcloud.service.impl.SignPointServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

@RestController
@RefreshScope
@Api(description = "隐患点接口")
@RequestMapping("/api/route/hiddenDangerPoint")
public class HiddenDangerPointController {

    @Autowired
    @Lazy
    private HiddenDangerPointServiceImpl hiddenDangerPointService;

    @ApiOperation("保存隐患点")
    @PostMapping("add")
    public RespBean saveHiddenDangerPoint(String param,MultipartFile[] fileList, HttpServletRequest request){
        return hiddenDangerPointService.saveHiddenDangerPoint(param,fileList,request);
    }

    @ApiOperation("修改隐患点")
    @PostMapping("modify")
    public RespBean updateHiddenDangerPoint( @RequestBody HashMap<String,Object> param){
        return hiddenDangerPointService.updateHiddenDangerPoint(param);
    }

    @ApiOperation("删除隐患点")
    @GetMapping("remove")
    public RespBean deleteHiddenDangerPoint(@RequestParam Integer id){
        return hiddenDangerPointService.deleteHiddenDangerPoint(id);
    }

    @ApiOperation("查询隐患点详情")
    @GetMapping("findDetail")
    public RespBean findDetail(@RequestParam Integer id,HttpServletRequest request){
        return hiddenDangerPointService.findDetail(id,request);
    }

    @ApiOperation("查询角色所有隐患点列表")
    @GetMapping("findListByUser")
    public RespBean findListByUser(HttpServletRequest request) throws UnsupportedEncodingException {
        return hiddenDangerPointService.findListByUser(request);
    }

    @ApiOperation("忽略隐患点")
    @GetMapping("ignore")
    public RespBean ignore(@RequestParam String hiddenDangerPointCode) throws UnsupportedEncodingException {
        return hiddenDangerPointService.ignore(hiddenDangerPointCode);
    }

    @ApiOperation("条件查询隐患点列表")
    @PostMapping("findCondition")
    public RespBean findCondition(@RequestBody HiddenDangerPointQuery hiddenDangerPointQuery, HttpServletRequest request) throws UnsupportedEncodingException {
        return hiddenDangerPointService.findCondition(hiddenDangerPointQuery,request);
    }

}
