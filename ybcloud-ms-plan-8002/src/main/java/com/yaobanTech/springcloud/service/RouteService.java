package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value ="routeService")
public interface RouteService {
    @GetMapping("/api/route/way/findDetail")
    RespBean findDetail(@RequestParam("id") Integer id);

    @GetMapping("/api/route/way/findEnum")
    RespBean findEnum(@RequestParam String code);

    @GetMapping("/api/route/way/findListByIds")
    RespBean getListByIds(@RequestParam("routeIds") List<Integer> routeIds);
}