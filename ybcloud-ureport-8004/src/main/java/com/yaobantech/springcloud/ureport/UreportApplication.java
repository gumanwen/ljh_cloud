package com.yaobantech.springcloud.ureport;

import com.bstek.ureport.console.UReportServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import javax.servlet.Servlet;

@ImportResource("classpath:ureport-console-context.xml") // 加载ureport对应的xml配置文件
@SpringBootApplication
public class UreportApplication {

    public static void main(String[] args) {
        SpringApplication.run(UreportApplication.class, args);
    }

    /**
     * ureport2报表Servlet配置
     */
    @Bean
    public ServletRegistrationBean<Servlet> ureport2Servlet(){
        return new ServletRegistrationBean<>(new UReportServlet(), "/ureport/*");
    }

}
