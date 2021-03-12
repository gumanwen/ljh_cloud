package com.yaobanTech.springcloud;

import com.yaobanTech.springcloud.config.DatabaseConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.yaobanTech.springcloud.service")
@Import(DatabaseConfiguration.class) //DataSourceConfig是上面配置文件的
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PlanApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlanApplication.class, args);
    }
}
