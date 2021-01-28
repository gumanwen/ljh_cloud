package com.yaobanTech.springcloud.service.impl;

import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value ="auth2Service",fallback = OauthServiceFallBack.class)
public interface OauthService {
    @GetMapping("/user/userRight/getNameByUsername")
    RespBean getChineseName(@RequestParam("username")  String username);

    @GetMapping("/user/userRight/getCurrentUser")
    RespBean getCurrentUser(@RequestParam("token") String token);
}