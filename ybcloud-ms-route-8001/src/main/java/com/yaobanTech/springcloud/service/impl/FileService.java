package com.yaobanTech.springcloud.service.impl;

import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@FeignClient(value ="fileService")
public interface FileService {

    @GetMapping(value = "/api/file/selectOneByPid")
    RespBean selectOneByPid(@RequestParam("pid") String pid, @RequestParam("type") String type);

    @PostMapping(value = "/api/file/importFiles")
    RespBean saveByPid(@RequestParam("pid") String pid,@RequestParam("fileList") MultipartFile[] fileList, @RequestParam("type") String type);

}
