package com.yaobanTech.springcloud.service.impl;

import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.stereotype.Component;

@Component
public class OauthServiceFallBack implements OauthService{

    @Override
    public RespBean getAll(String token, Integer type) {
        return RespBean.error("调用权限服务失败！");
    }
}
