package com.yaobanTech.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/api/route")
public class RouteController {

   /* @Value("${content}")
    private String content;*/

    @GetMapping("/helloworld")
    public String helloworld(){
        System.out.println("content");
        return "content1";
    }
}
