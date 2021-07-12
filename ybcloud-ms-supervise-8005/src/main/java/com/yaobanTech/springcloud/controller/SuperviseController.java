package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.entity.utils.RespBean;
import com.yaobanTech.springcloud.service.ISuperviseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
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
@Api(value = "SuperviseController" , tags = "巡查--接口")
@RestController
@RequestMapping("/api/supervise")
public class SuperviseController {

    @Autowired
    private ISuperviseService iSuperviseService;

    @ApiOperation("app & web:获取居民小区用水检查列表")
    @PostMapping("/getComSupervise")
    public RespBean getComSupervise(long pageNo,long pageSize,@RequestBody Map<String, Object> params,HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException, ParseException {
        return iSuperviseService.getComSupervise(pageNo, pageSize, params, request);
    }

    @ApiOperation("app & web:获取施工用水检查列表")
    @PostMapping("/getConSupervise")
    public RespBean getConSupervise(long pageNo,long pageSize,@RequestBody Map<String, Object> params,HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException, ParseException {
        return iSuperviseService.getConSupervise(pageNo, pageSize, params, request);
    }

    @ApiOperation("app & web:获取供水设施用水检查列表")
    @PostMapping("/getFacSupervise")
    public RespBean getFacSupervise(long pageNo,long pageSize,@RequestBody Map<String, Object> params,HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException, ParseException {
        return iSuperviseService.getFacSupervise(pageNo, pageSize, params, request);
    }

    @ApiOperation("app & web:获取特行用水检查列表")
    @PostMapping("/getSpeSupervise")
    public RespBean getSpeSupervise(long pageNo,long pageSize,@RequestBody Map<String, Object> params,HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException, ParseException {
        return iSuperviseService.getSpeSupervise(pageNo, pageSize, params, request);
    }

    @ApiOperation("app 新增居民小区用水检查")
    @PostMapping("/saveComSupervise")
    public RespBean saveComSupervise(@RequestBody Map<String, Object> params) {
        return iSuperviseService.saveComSupervise(params);
    }

    @ApiOperation("app 新增施工用水检查")
    @PostMapping("/saveConSupervise")
    public RespBean saveConSupervise(@RequestBody Map<String, Object> params) {
        return iSuperviseService.saveConSupervise(params);
    }

    @ApiOperation("app 新增供水设施用水检查")
    @PostMapping("/saveFacSupervise")
    public RespBean saveFacSupervise(@RequestBody Map<String, Object> params) {
        return iSuperviseService.saveFacSupervise(params);
    }

    @ApiOperation("app 新增特行用水检查")
    @PostMapping("/saveSpeSupervise")
    public RespBean saveSpeSupervise(@RequestBody Map<String, Object> params) {
        return iSuperviseService.saveSpeSupervise(params);
    }

    @ApiOperation("app 修改居民小区用水检查")
    @PostMapping("/updateComSupervise")
    public RespBean updateComSupervise(@RequestBody Map<String, Object> params) {
        return iSuperviseService.updateComSupervise(params);
    }

    @ApiOperation("app 修改施工用水检查")
    @PostMapping("/updateConSupervise")
    public RespBean updateConSupervise(@RequestBody Map<String, Object> params) {
        return iSuperviseService.updateConSupervise(params);
    }

    @ApiOperation("app 修改供水设施用水检查")
    @PostMapping("/updateFacSupervise")
    public RespBean updateFacSupervise(@RequestBody Map<String, Object> params) {
        return iSuperviseService.updateFacSupervise(params);
    }

    @ApiOperation("app 修改特行用水检查")
    @PostMapping("/updateSpeSupervise")
    public RespBean updateSpeSupervise(@RequestBody Map<String, Object> params) {
        return iSuperviseService.updateSpeSupervise(params);
    }
}

