package com.yaobanTech.springcloud.service.impl;

import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.stereotype.Component;

@Component
public class PlanServiceFallBack implements PlanService{

    @Override
    public RespBean findByRouteId(Integer routeId) {
        return RespBean.error("调用路线服务失败！");
    }
}
