package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.service.RouteService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RefreshScope
public class PlanController {
     @Resource
     private RouteService routeService;

     @GetMapping("/get")
     public String get(){
          return routeService.helloworld();
     }
}
