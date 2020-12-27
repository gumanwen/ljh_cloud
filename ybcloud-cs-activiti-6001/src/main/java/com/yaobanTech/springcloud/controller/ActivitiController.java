package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.service.ActivitiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@Api(value = "流程Controller" , tags = "流程")
@RequestMapping("/api/activiti")
public class ActivitiController {

    @Autowired
    ActivitiService activitiService;

    @GetMapping("Deployment")
    @ApiOperation("部署流程")
    public HashMap<Object,Object> deployment( String deploymentId, String deploymentName){
        return activitiService.deployment(deploymentId,deploymentName);
    }

}
