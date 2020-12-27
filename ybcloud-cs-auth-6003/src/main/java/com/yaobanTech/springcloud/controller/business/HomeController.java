package com.yaobanTech.springcloud.controller.business;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页,不被拦截的接口,系统中配置
 *
 * @author Tom
 * @date 2020-09-04
 */
@RestController
public class HomeController {

    @RequestMapping("/home")
    public String home() {
        return "home page";
    }

    @RequestMapping("/")
    public String index() {
        return "index page";
    }

}

