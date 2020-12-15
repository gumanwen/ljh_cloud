package com.yaobanTech.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient("routeService")
public interface RouteService {

    @GetMapping("/api/route/helloworld")
    public String helloworld();
}
