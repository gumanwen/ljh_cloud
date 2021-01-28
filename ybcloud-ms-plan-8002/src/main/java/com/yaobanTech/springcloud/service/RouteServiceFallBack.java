package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.stereotype.Component;

@Component
public class RouteServiceFallBack implements RouteService{
    @Override
    public RespBean findDetail(Integer id) {
        return RespBean.error("调用路线服务失败！");
    }

    @Override
    public String testFeign(Integer routeId) {
        return "fallBack";
    }
}
