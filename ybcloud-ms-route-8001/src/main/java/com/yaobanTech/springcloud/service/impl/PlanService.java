package com.yaobanTech.springcloud.service.impl;

import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value ="planService",fallback = PlanServiceFallBack.class)
public interface PlanService {

    @GetMapping(value = "/api/plan/findByRouteId")
    RespBean findByRouteId(@RequestParam("routeId") Integer routeId);

}
