package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.domain.LeakPointQuery;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.service.impl.LeakPointServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@RestController
@RefreshScope
@Api(description = "漏点接口")
@RequestMapping("/api/route/leakPoint")
public class LeakPointController {

    @Autowired
    @Lazy
    private LeakPointServiceImpl leakPointService;

    @ApiOperation("保存漏点")
    @RequestMapping("add")
    public RespBean saveHiddenDangerPoint(@RequestParam String param,@RequestPart MultipartFile[] fileList, HttpServletRequest request){
        return leakPointService.saveLeakPoint(param,fileList,request);
    }

    @ApiOperation("修改漏点")
    @PostMapping("modify")
    public RespBean updateHiddenDangerPoint( @RequestBody HashMap<String,Object> param){
        return leakPointService.updateBizLeakPointEntity(param);
    }

    @ApiOperation("删除漏点")
    @GetMapping("remove")
    public RespBean deleteHiddenDangerPoint(@RequestParam Integer id){
        return leakPointService.deleteBizLeakPointEntity(id);
    }

    @ApiOperation("查询漏点详情")
    @GetMapping("findDetail")
    public RespBean findDetail(@RequestParam Integer id,HttpServletRequest request){
        return leakPointService.findDetail(id,request);
    }

    @ApiOperation("查询角色所有漏点列表")
    @GetMapping("findListByUser")
    public RespBean findListByUser(HttpServletRequest request) throws UnsupportedEncodingException {
        return leakPointService.findListByUser(request);
    }

    @ApiOperation("条件查询漏点列表")
    @PostMapping("findCondition")
    public RespBean findCondition(@RequestBody LeakPointQuery leakPointQuery,HttpServletRequest request) throws UnsupportedEncodingException {
        return leakPointService.findCondition(leakPointQuery,request);
    }

    @ApiOperation("忽略漏点")
    @GetMapping("ignore")
    public RespBean ignore(@RequestParam String leakPointCode) throws UnsupportedEncodingException {
        return leakPointService.ignore(leakPointCode);
    }

    @ApiOperation("web: 转工单")
    @PostMapping("/transferToOrder")
    public RespBean transferToOrder(@RequestBody HashMap<String,Object> param) throws IOException {
        return leakPointService.transferToOrder(param);
    }

}
