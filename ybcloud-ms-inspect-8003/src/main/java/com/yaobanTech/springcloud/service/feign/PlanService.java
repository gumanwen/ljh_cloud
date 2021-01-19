package com.yaobanTech.springcloud.service.feign;

import com.yaobanTech.springcloud.entity.utils.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value ="planService" )
public interface PlanService {
    @GetMapping("/api/plan/{id}")
    RespBean findrouteId(@PathVariable("id") Integer id);

    @GetMapping("/api/plan/findById")
    RespBean findById(@RequestParam("id") Integer id);

    @GetMapping("/api/plan/findSelection")
    RespBean findSelection(@RequestParam("routeId") Integer routeId);
}