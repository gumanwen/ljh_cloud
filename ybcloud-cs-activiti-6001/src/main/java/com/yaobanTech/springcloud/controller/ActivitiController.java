package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.entity.utils.RespBean;
import com.yaobanTech.springcloud.service.ActivitiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "流程Controller" , tags = "流程")
@RequestMapping("/api/activiti")
public class ActivitiController {

    @Autowired
    ActivitiService activitiService;

    /*@ApiOperation("部署流程")
    @GetMapping("Deployment")
    public RespBean deployment( String deploymentId, String deploymentName){
        return activitiService.deployment(deploymentId,deploymentName);
    }*/

    @ApiOperation("启动流程")
    @PostMapping("startProcess")
    public RespBean startProcess(@RequestBody Map<String,Object> params){
        return activitiService.startProcess(params);
    }

    @ApiOperation("执行任务")
    @PostMapping("completeTask")
    public RespBean completeTask(@RequestBody Map<String,Object> params){
        return activitiService.completeTask(params);
    }
}
