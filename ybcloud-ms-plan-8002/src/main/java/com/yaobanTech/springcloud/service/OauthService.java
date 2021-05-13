package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value ="auth2Service" )
public interface OauthService {

    @GetMapping("/user/userRight/getNameByUsername")
    RespBean getChineseName(@RequestParam("username")  String username,@RequestParam("type") Integer type,@RequestParam("token") String token);

    @GetMapping("/user/userRight/getAllNameByUsername")
    RespBean getNameList(@RequestParam("nameList") List<String> nameList);

    @GetMapping("/user/userRight/getAll")
    RespBean getAll(@RequestParam("token") String token,@RequestParam("type") Integer type);
}

