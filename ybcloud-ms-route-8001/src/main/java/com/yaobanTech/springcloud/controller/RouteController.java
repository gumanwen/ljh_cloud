package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.domain.BizRoute;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/api/route")
public class RouteController {

   /* @Value("${content}")
    private String content;*/
    @Autowired
    @Lazy
    private RouteService routeService;

    @PostMapping("/add")
    public RespBean saveRoute(BizRoute route){
        return routeService.saveRoute(route);
    }

    @PostMapping("/modify")
    public RespBean updateRoute(BizRoute route){
        return routeService.updateRoute(route);
    }

    @GetMapping("/remove")
    public RespBean deleteRoute(Integer id){
        return routeService.deleteRoute(id);
    }
}
