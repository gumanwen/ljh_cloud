package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.entity.Files;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yaobanTech.springcloud.entity.utils.RespBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lijh
 * @since 2020-12-27
 */
public interface IFilesService extends IService<Files> {

    RespBean importFiles(MultipartFile[] fileList, String pid, String type) throws IOException;

    RespBean selectOneByPid(String pid,String type);

    RespBean remove(String id);


}
