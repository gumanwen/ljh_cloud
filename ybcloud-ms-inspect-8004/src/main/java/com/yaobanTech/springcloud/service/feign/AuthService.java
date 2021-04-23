package com.yaobanTech.springcloud.service.feign;

import com.yaobanTech.springcloud.entity.utils.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value ="auth2Service" )
public interface AuthService {
    @GetMapping("/user/userRight/getNameByUsername")
    RespBean getNameByUsername(@RequestParam("username")  String username);
    @GetMapping("/user/userRight/getCurrentUser")
    RespBean getCurrentUser(@RequestParam("token") String token);
    @GetMapping("/user/userRight/getCurrentUserAndRole")
    RespBean getCurrentUserAndRole(@RequestParam("token") String token);
    @GetMapping("/user/userRight/getAll")
    RespBean getAll(@RequestParam("token") String token,@RequestParam("type") Integer type);
    @GetMapping("/user/userRight/selectUserByRole")
    RespBean selectUserByRole(@RequestParam("role")  String role);
}
