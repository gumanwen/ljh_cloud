package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.domain.BizRoute;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.repository.BizRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class RouteService {
    @Autowired
    @Lazy
    private BizRouteRepository bizRouteRepository;


    public RespBean saveRoute(BizRoute bizRoute) {
        if(bizRoute != null) {
            try {
                BizRoute route = bizRouteRepository.save(bizRoute);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("保存失败！");
            }
        }else{
            return RespBean.error("数据为空！");
        }
        return RespBean.ok("保存成功！");
    }

    public RespBean updateRoute(BizRoute bizRoute) {
        if(bizRoute.getId() != null) {
            try {
                BizRoute route = bizRouteRepository.save(bizRoute);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("修改失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("保存成功！");
    }
}
