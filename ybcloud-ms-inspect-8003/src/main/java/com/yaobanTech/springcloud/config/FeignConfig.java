package com.yaobanTech.springcloud.config;

import com.yaobanTech.springcloud.utils.FieldUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig {

    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = requestAttributes.getRequest();// 老请求
                String token = request.getHeader("Authorization");
                String ttoken = request.getHeader("TW-Authorization");
                if(FieldUtils.isStringNotEmpty(token)){
                    requestTemplate.header("Authorization", token);
                }
                if(FieldUtils.isStringNotEmpty(ttoken)){
                    requestTemplate.header("TW-Authorization", ttoken);
                }
            }
        };
    }
}
