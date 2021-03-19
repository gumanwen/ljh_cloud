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

    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
   /* @PostMapping("/import")
    public RespBean importData(MultipartFile file, HttpServletRequest req) throws IOException {
        String format = sdf.format(new Date());
        String realPath = req.getServletContext().getRealPath("/upload") + format;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File(folder,newName));
        String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/upload" + format + newName;
        System.out.println(url);
        return RespBean.ok("上传成功!");
    }*/

    @ApiOperation(value ="文件上传接口")
    @RequestMapping ("/importFiles")
    public RespBean importFiles(MultipartFile[] fileList, String pid, String type) throws IOException {
        return iFilesService.importFiles(fileList,pid,type);
    }

    @ApiOperation(value ="根据pid查询文件详情")
    @GetMapping("/selectOneByPid")
    public RespBean selectOneByPid(@RequestParam("pid") String pid,@RequestParam("type") String type){
        return iFilesService.selectOneByPid(pid,type);
    }
}
