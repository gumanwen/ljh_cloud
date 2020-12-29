package com.yaobanTech.springcloud.service.impl;

import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value ="inspectService")
public interface OauthService {

    @GetMapping("/oauth/getCurrentUser")
    RespBean getCurrentUser(HttpServletRequest request);



}