package com.yaobanTech.springcloud;

import com.yaobanTech.springcloud.config.DatabaseConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient//nacos服务注册
@EnableSwagger2//swagger2配置
@EnableCaching//启动缓存
@EnableTransactionManagement//启动事务管理RouteService
@MapperScan("com.yaobanTech.springcloud.mapper")
@EnableFeignClients
@Import(DatabaseConfiguration.class) //DataSourceConfig是上面配置文件的
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FileApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}
