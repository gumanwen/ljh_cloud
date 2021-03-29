package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.entity.utils.RespBean;
import com.yaobanTech.springcloud.service.IFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Api(value = "FileController" , tags = "文件处理--接口")
@RestController
@RequestMapping("/api/file")
@CrossOrigin
public class FileController {

    @Autowired
    private IFilesService iFilesService;

    @ApiOperation(value ="文件上传接口")
    @PostMapping("/importFiles")
    public RespBean importFiles( MultipartFile[] fileList, String pid, String type) throws IOException {
        return iFilesService.importFiles(fileList,pid,type);
    }

    @ApiOperation(value ="根据pid查询文件详情")
    @GetMapping("/selectOneByPid")
    public RespBean selectOneByPid(@RequestParam("pid") String pid,@RequestParam("type") String type){
        return iFilesService.selectOneByPid(pid,type);
    }

    @ApiOperation(value ="根据pid删除文件")
    @GetMapping("/remove")
    public RespBean remove(@RequestParam("id") String id){
        return iFilesService.remove(id);
    }
}
