package com.yaobanTech.springcloud.service.feign;


import com.yaobanTech.springcloud.entity.utils.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value ="routeService" )
public interface RouteService {

    @GetMapping("/api/route/signPoint/{id}")
    RespBean findsignPoint(@PathVariable("id") Integer id);

    @GetMapping("/api/route/signPoint/findList")
    RespBean findpoints(@RequestParam("routeId") Integer routeId);

    @PutMapping("/api/route/signPoint")
    RespBean updatePoint(@RequestBody Map<String, Object> params);
}