package com.yaobanTech.springcloud.service.impl;

import com.yaobanTech.springcloud.domain.RespBean;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@FeignClient(value ="fileService")
public interface FileService {

    @GetMapping(value = "/api/file/selectOneByPid")
    RespBean selectOneByPid(@RequestParam("pid") String pid, @RequestParam("type") String type);


    @RequestMapping(value = "/api/file/importFiles", method = RequestMethod.POST,
//            produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    RespBean saveByPid(@RequestPart("fileList") MultipartFile[] fileList,@RequestParam("pid") String pid, @RequestParam("type") String type);

}
