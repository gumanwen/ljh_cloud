package com.yaobanTech.springcloud.service.feign;


import com.yaobanTech.springcloud.entity.utils.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@FeignClient(value ="routeService" )
public interface RouteService {

    @GetMapping("/api/route/signPoint/findSignPoint")
    RespBean findsignPoint(@RequestParam("id")Integer id);

    @GetMapping("/api/route/signPoint/findList")
    RespBean findpoints(@RequestParam("routeId") Integer routeId);

    @PostMapping("/api/route/signPoint/modify")
    RespBean updatePoint(@RequestBody Map<String, Object> params);

    @GetMapping("/api/route/way/findDetail")
    RespBean findDetail(@RequestParam("id")Integer id);

    @GetMapping("/api/route/way/findSelection")
    RespBean findSelection();

    @GetMapping("/api/route/signPoint/findSignedList")
    RespBean findSignedList(@RequestParam("routeId") Integer routeId);
}