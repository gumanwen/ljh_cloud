package com.yaobanTech.springcloud.service.feign;

import com.yaobanTech.springcloud.entity.utils.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value ="planService" )
public interface PlanService {

    @GetMapping("/api/plan/{id}")
    RespBean findrouteId(@PathVariable("id") Integer id);

}