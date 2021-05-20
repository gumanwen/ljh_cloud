package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.config.ServiceFeignConfiguration;
import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@FeignClient(value ="inspectService",configuration = ServiceFeignConfiguration.class)

public interface InspectService {

    @GetMapping("/api/inspect/plan/getAllInspectByPlanId")
    RespBean findInspectListById(@RequestParam Integer planId);

    @GetMapping("/api/inspect/task/autoCreate")
    RespBean sendInspectInfo(@RequestBody HashMap<String,Object> param);

    @GetMapping("/api/inspect/isDelete")
    Boolean deleteRoute(@RequestParam("routeId") Integer routeId,@RequestParam("planId") Integer planId);

}