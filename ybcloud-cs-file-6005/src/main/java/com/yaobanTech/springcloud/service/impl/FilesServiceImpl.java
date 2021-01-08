package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaobanTech.springcloud.entity.Files;
import com.yaobanTech.springcloud.entity.utils.RespBean;
import com.yaobanTech.springcloud.mapper.FilesMapper;
import com.yaobanTech.springcloud.service.IFilesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaobanTech.springcloud.utils.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lijh
 * @since 2020-12-27
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {

    @Autowired
    private FilesMapper filesMapper;

    Logger logger = LoggerFactory.getLogger(FilesServiceImpl.class);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd/");

    @Value("${web.upload-path}")
    private String path;

    @Value("${web.upload-vpath}")
    private String vpath;

    /*@Value("${server.ip}")
    private String ip;

    @Value("${server.port}")
    private String port;*/

    @Override
    public RespBean importFiles(MultipartFile[] fileList, String pid, String type) throws IOException {
        //acceptId:代表的是附件关联的主键
        String format = sdf.format(new Date());
        String realPath = path  +"upload/"+ format;
        logger.info(realPath);
        File folder = new File(realPath);
        try {
            if(!"".equals(pid) && pid != "") {
                if (fileList.length < 1) {
                    return RespBean.error("file is empty! 文件是空");
                }
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                for (int i = 0; i < fileList.length; i++) {
                    String oldName = fileList[i].getOriginalFilename();
                    String filename = oldName.substring(0, oldName.lastIndexOf("."));
                    String typename= oldName.substring(oldName.lastIndexOf(".")+1);
                    String mimeType ="";
                    if("png".equals(typename.toLowerCase()) || "jpg".equals(typename.toLowerCase()) || "jpeg".equals(typename.toLowerCase())){
                        mimeType ="image/"+typename.toLowerCase();
                        //mp4|flv|avi|rm|rmvb|wmv
                    }else  if("mp4".equals(typename.toLowerCase()) || "flv".equals(typename.toLowerCase()) || "avi".equals(typename.toLowerCase()) || "rm".equals(typename.toLowerCase()) || "rmvb".equals(typename.toLowerCase()) || "wmv".equals(typename.toLowerCase()) ){
                        mimeType ="video/"+typename.toLowerCase();
                    }
                    String newName = pid + "_Default_" + oldName.substring(oldName.lastIndexOf("."));
                    fileList[i].transferTo(new File(folder, newName));
                    String url = vpath + "upload/" + format + newName;
                    logger.info(filename + url);
                    //存到附件表
                    Files file = new Files();
                    file.setPid(pid);
                    file.setName(newName);
                    file.setUrl(url);
                    file.setType(type);
                    file.setMimeType(mimeType);
                    file.setRemark("附件");
                    filesMapper.insert(file);
                }
                return RespBean.ok("上传成功!");
            }else{
                return  RespBean.error("上传失败，缺少id！！");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return RespBean.ok("上传成功!");
    }

    @Override
    public RespBean selectOneByPid(String pid,String type) {
        if(FieldUtils.isStringNotEmpty(pid)){
            QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
            List<HashMap<String,Object>> result = new ArrayList<>();
            List<Files> files = filesMapper.selectList(queryWrapper.eq("isvalid",1).eq(FieldUtils.isStringNotEmpty(pid),"pid",pid).eq(FieldUtils.isStringNotEmpty(type),"type",type));
            if(files.size()>0){
                for(int i=0;i<files.size();i++){
                    result.add(JSON.parseObject(JSON.toJSONString(files.get(i)), (Type) Map.class));
                }
            }
            return RespBean.ok("文件列表").setObj(files);
        }else{
            return RespBean.error("缺少编号pid");
        }
    }
}
