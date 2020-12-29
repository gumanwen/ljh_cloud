package com.yaobanTech.springcloud.service.feign;

import com.yaobanTech.springcloud.entity.utils.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value ="activitiService")
public interface ActivitiService{

    @PostMapping("/api/activiti/startProcess")
    RespBean startProcess(@RequestBody Map<String,Object> params);

}