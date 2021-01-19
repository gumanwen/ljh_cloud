package com.yaobanTech.springcloud.service.impl;

import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.stereotype.Component;

@Component
public class FileServiceFallBack implements FileService{
    @Override
    public RespBean selectOneByPid(String pid, String type) {
        return RespBean.error("调用文件服务失败！");
    }
}
