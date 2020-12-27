package com.yaobanTech.springcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient//nacos服务注册
@EnableSwagger2//swagger2配置
@EnableCaching//启动缓存
@EnableTransactionManagement//启动事务管理RouteService
@MapperScan("com.yaobanTech.springcloud.mapper")
@EnableFeignClients(basePackages = "com.yaobanTech.springcloud.service.feign")
public class  InspectApplication {
    public static void main(String[] args) {
        SpringApplication.run(InspectApplication.class, args);
    }
}
