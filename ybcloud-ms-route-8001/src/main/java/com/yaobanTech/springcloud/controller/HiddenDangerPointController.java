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
    @RequestMapping ("add")
    public RespBean saveHiddenDangerPoint( @RequestParam String param,@RequestPart MultipartFile[] fileList, HttpServletRequest request){
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

    @ApiOperation("根据id查询隐患点详情")
    @GetMapping("findDetail")
    public RespBean findDetail(@RequestParam Integer id,HttpServletRequest request){
        return hiddenDangerPointService.findDetail(id,request);
    }

    @ApiOperation("根据编号查询隐患点详情")
    @GetMapping("findDetailByCode")
    public RespBean findDetailByCode(@RequestParam String code,HttpServletRequest request){
        return hiddenDangerPointService.findDetailByCode(code,request);
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
    public RespBean findCondition(@RequestBody HashMap<String,Object> hashMap, HttpServletRequest request) throws UnsupportedEncodingException {
        return hiddenDangerPointService.findCondition(hashMap,request);
    }

    @ApiOperation("查询最新10条隐患点列表")
    @GetMapping("top")
    public RespBean top() {
        return hiddenDangerPointService.top();
    }

    @ApiOperation("条件查询隐患安全记录")
    @PostMapping("conditionRecord")
    public RespBean conditionRecord(@RequestBody HashMap<String,Object> hashMap) {
        return hiddenDangerPointService.conditionRecord(hashMap);
    }

    @ApiOperation("问题对比分析")
    @PostMapping("compare")
    public RespBean compare(@RequestBody HashMap<String,Object> hashMap) {
        return hiddenDangerPointService.compare(hashMap);
    }

//    @ApiOperation("问题处理分析")
//    @PostMapping("handle")
//    public RespBean handle() {
//        return hiddenDangerPointService.handle();
//    }

}
