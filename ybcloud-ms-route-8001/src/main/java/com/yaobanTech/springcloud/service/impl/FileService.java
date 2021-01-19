package com.yaobanTech.springcloud.service.impl;

import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value ="fileService",fallback = FileServiceFallBack.class)
public interface FileService {

    @GetMapping(value = "/api/file/selectOneByPid")
    RespBean selectOneByPid(@RequestParam("pid") String pid, @RequestParam("type") String type);

}
