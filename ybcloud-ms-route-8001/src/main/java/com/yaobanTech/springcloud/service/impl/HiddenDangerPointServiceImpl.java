package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.domain.*;
import com.yaobanTech.springcloud.domain.enumDef.EnumMenu;
import com.yaobanTech.springcloud.repository.BizHiddenDangerPointRepository;
import com.yaobanTech.springcloud.repository.BizRouteRepository;
import com.yaobanTech.springcloud.repository.BizSignPointRepository;
import com.yaobanTech.springcloud.repository.BizSignedPointRepository;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class HiddenDangerPointServiceImpl {
    @Autowired
    @Lazy
    private BizHiddenDangerPointRepository hiddenDangerPointRepository;

    @Autowired
    @Lazy
    private OauthService oauthService;

    @Autowired
    @Lazy
    private FileService fileService;

    @Autowired
    @Lazy
    private RedisService redisService;

    @GlobalTransactional
    public RespBean saveHiddenDangerPoint(HashMap<String,Object> param,HttpServletRequest request) {
        BizHiddenDangerPointEntity bizHiddenDangerPointEntity = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizHiddenDangerPointEntity.class);
        if(bizHiddenDangerPointEntity != null) {
            try {
                String hiddenDangerPointCode = null;
                if("3".equals(bizHiddenDangerPointEntity.getWaterUseOffice())){
                    hiddenDangerPointCode =redisService.createGenerateCode("隐患点","CNY",true,6);
                }
               else if("4".equals(bizHiddenDangerPointEntity.getWaterUseOffice())){
                    hiddenDangerPointCode =redisService.createGenerateCode("隐患点","CBY",true,6);
                }
                else if("5".equals(bizHiddenDangerPointEntity.getWaterUseOffice())){
                    hiddenDangerPointCode =redisService.createGenerateCode("隐患点","SJY",true,6);
                }else{
                    return RespBean.error("用水管理所参数不符合系统约定，生成编号异常！");
                }
                String header = request.getHeader("Authorization");
                String token =  StringUtils.substringAfter(header, "Bearer ");
                String user = (String) oauthService.getCurrentUser(token).getObj();
                if(oauthService.getCurrentUser(token).getStatus() == 500){
                    throw new RuntimeException("Feign调用权限服务失败");
                }
                bizHiddenDangerPointEntity.setCommitDate(new Date());
                bizHiddenDangerPointEntity.setEnabled(1);
                bizHiddenDangerPointEntity.setCommitBy(user);
                bizHiddenDangerPointEntity.setHiddenDangerPointCode(hiddenDangerPointCode);
               hiddenDangerPointRepository.save(bizHiddenDangerPointEntity);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("保存失败！");
            }
        }else{
            return RespBean.error("数据为空！");
        }
        return RespBean.ok("保存成功！");
    }

    public RespBean updateHiddenDangerPoint(HashMap<String,Object> param) {
        BizHiddenDangerPointEntity bizHiddenDangerPointEntity = null;
        if(!param.isEmpty() && param.get("form") != null) {
            bizHiddenDangerPointEntity = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizHiddenDangerPointEntity.class);
            if(bizHiddenDangerPointEntity.getId() != null){
            try {
                hiddenDangerPointRepository.save(bizHiddenDangerPointEntity);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("修改失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("修改成功！");
        }else{
            return RespBean.error("参数为空！");
        }
    }

    public RespBean deleteHiddenDangerPoint(Integer id) {
        if(id != null) {
            try { hiddenDangerPointRepository.deleteHiddenDangerPoint(id);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("删除失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("删除成功！");
    }

    @GlobalTransactional
    public RespBean findDetail(Integer id) {
        BizHiddenDangerPointEntity bdpe = null;
        if(id != null) {
            try {
                bdpe = hiddenDangerPointRepository.findHiddenDangerPoint(id);
                String chineseName = (String) oauthService.getChineseName(bdpe.getCommitBy()).getObj();
                bdpe.setCommitBy(chineseName);
                if(oauthService.getChineseName(bdpe.getCommitBy()).getStatus() == 500){
                    throw new RuntimeException("Feign调用权限服务失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("查询成功！",bdpe);
    }

    @GlobalTransactional
    public RespBean findListByUser(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token =  StringUtils.substringAfter(header, "Bearer ");
        String user = (String) oauthService.getCurrentUser(token).getObj();
        if(oauthService.getCurrentUser(token).getStatus() == 500){
            throw new RuntimeException("Feign调用权限服务失败");
        }
        String chineseName = (String) oauthService.getChineseName(user).getObj();
        if(oauthService.getChineseName(user).getStatus() == 500){
            throw new RuntimeException("Feign调用权限服务失败");
        }
        List<BizHiddenDangerPointEntity> list = hiddenDangerPointRepository.findList(user);
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                BizHiddenDangerPointEntity bizHiddenDangerPointEntity = list.get(i);
                bizHiddenDangerPointEntity.setCommitBy(chineseName);
//                if(points.size()>0){
//                    for(int j =0; j<points.size();j++){
//                        //获取报建文件列表
//                        if(FieldUtils.isObjectNotEmpty(points.get(j).getFileType())) {
//                            RespBean respBean = fileService.selectOneByPid(String.valueOf((Integer) points.get(j).getId()), (String) points.get(j).getFileType());
//                            List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>) respBean.getObj();
//                            if(respBean.getStatus() == 500){
//                                throw new RuntimeException("Feign调用文件服务失败");
//                            }
//                            Map signPointTypeEnum = (Map) EnumMenu.findEnum(points.get(j).getSignPointType()).getObj();
//                            points.get(j).setFileList(fileList);
//                            points.get(j).setWaterUseOfficeEnum(waterManagementOfficeEnum);
//                            points.get(j).setSignPointTypeEnum(pointInspectionTypeEnum);
//                            points.get(j).setRouteTypeEnum(routeTypeEnum);
//                        }
//                        list.add(points.get(j));
//                    }
//                }
            }
        }
        return RespBean.ok("查询成功！",list);
    }

}
