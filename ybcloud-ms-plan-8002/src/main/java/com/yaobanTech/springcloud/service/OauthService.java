package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value ="auth2Service" )
public interface OauthService {

    @GetMapping("/user/userRight/getNameByUsername")
    RespBean getChineseName(@RequestParam("username")  String username,@RequestParam("type") Integer type);

    @GetMapping("/user/userRight/getAll")
    RespBean getAll(@RequestParam("token") String token,@RequestParam("type") Integer type);
}

