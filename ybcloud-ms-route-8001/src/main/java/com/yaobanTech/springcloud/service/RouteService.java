package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.domain.BizRoute;
import com.yaobanTech.springcloud.domain.BizSignPoint;
import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Service
@FeignClient("routeService")
public interface RouteService {

    @PostMapping("/api/route/way/")
    public RespBean saveRoute(HashMap<String,Object> param);

    @PutMapping("/api/route/way/{id}")
    public RespBean updateRoute(@PathVariable("id") Integer id, @RequestBody BizRoute bizRoute);

    @DeleteMapping("/api/route/way/{id}")
    public RespBean deleteRoute(Integer id);

    @GetMapping("/api/route/way/")
    public RespBean findCondition(String waterManagementOffice,String pointInspectionType,
                                  String planInspectionMileage,String createdTime,
                                  String routeName,String routeCreator,
                                  String routeType);

    @GetMapping("/api/route/way/findAll")
    public RespBean findAll();

    @GetMapping("/api/route/way/findEnumMenu")
    public RespBean findEnumMenu(String mode);


}
