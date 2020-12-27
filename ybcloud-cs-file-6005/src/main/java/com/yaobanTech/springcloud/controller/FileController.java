package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.entity.utils.RespBean;
import com.yaobanTech.springcloud.service.IFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Api(value = "FileController" , tags = "文件处理--接口")
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private IFilesService iFilesService;

    @ApiOperation(value ="文件上传接口")
    @PostMapping("/importFiles")
    public RespBean importFiles(MultipartFile[] fileList, String pid, String type) throws IOException {
        return iFilesService.importFiles(fileList,pid,type);
    }

    @ApiOperation(value ="根据pid查询文件详情")
    @GetMapping("/selectOneByPid")
    public RespBean selectOneByPid(String pid,String type){
        return iFilesService.selectOneByPid(pid,type);
    }
}
