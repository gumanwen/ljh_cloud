package com.yaobanTech.springcloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class fileConfiguration extends WebMvcConfigurerAdapter {

    @Value("${web.upload-path}")
    private String path;

    @Value("${web.upload-vpath}")
    private String vpath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/BPMN/**").addResourceLocations("classpath:/BPMN/");
        //addResourceHandler是指定的虚拟路径，addResourceLocations是自己的物理路径，
        registry.addResourceHandler(vpath+"**").addResourceLocations("file:"+path);

        super.addResourceHandlers(registry);
    }
}