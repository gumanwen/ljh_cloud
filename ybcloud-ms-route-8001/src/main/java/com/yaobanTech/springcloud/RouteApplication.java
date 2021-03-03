package com.yaobanTech.springcloud;

import com.yaobanTech.springcloud.config.DatabaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@EnableDiscoveryClient
@EnableFeignClients
//@ImportResource("classpath:ureport-console-context.xml") // 加载ureport对应的xml配置文件
@Import(DatabaseConfiguration.class) //DataSourceConfig是上面配置文件的
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RouteApplication {
    public static void main(String[] args) {
        SpringApplication.run(RouteApplication.class, args);
    }
}
