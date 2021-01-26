package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.domain.BizLeakPointEntity;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.repository.BizLeakPointRepository;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class LeakPointServiceImpl {
    @Autowired
    @Lazy
    private BizLeakPointRepository leakPointRepository;

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
    public RespBean saveLeakPoint(HashMap<String,Object> param,HttpServletRequest request) {
        BizLeakPointEntity bizLeakPointEntity = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizLeakPointEntity.class);
        if(bizLeakPointEntity != null) {
            try {
                String leakPointCode = null;
                if("3".equals(bizLeakPointEntity.getWaterUseOffice())){
                    leakPointCode =redisService.createGenerateCode("漏点","CNL",true,6);
                }
                else if("4".equals(bizLeakPointEntity.getWaterUseOffice())){
                    leakPointCode =redisService.createGenerateCode("漏点","CBL",true,6);
                }
                else if("5".equals(bizLeakPointEntity.getWaterUseOffice())){
                    leakPointCode =redisService.createGenerateCode("漏点","SJL",true,6);
                }else{
                    return RespBean.error("用水管理所参数不符合系统约定，生成编号异常！");
                }
                String header = request.getHeader("Authorization");
                String token =  StringUtils.substringAfter(header, "Bearer ");
                String user = (String) oauthService.getCurrentUser(token).getObj();
                if(oauthService.getCurrentUser(token).getStatus() == 500){
                    throw new RuntimeException("Feign调用权限服务失败");
                }
                bizLeakPointEntity.setCommitDate(new Date());
                bizLeakPointEntity.setEnabled(1);
                bizLeakPointEntity.setCommitBy(user);
                bizLeakPointEntity.setLeakPointCode(leakPointCode);
               leakPointRepository.save(bizLeakPointEntity);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("保存失败！");
            }
        }else{
            return RespBean.error("数据为空！");
        }
        return RespBean.ok("保存成功！");
    }

    public RespBean updateBizLeakPointEntity(HashMap<String,Object> param) {
        BizLeakPointEntity bizLeakPointEntity = null;
        if(!param.isEmpty() && param.get("form") != null) {
            bizLeakPointEntity = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizLeakPointEntity.class);
            if(bizLeakPointEntity.getId() != null){
            try {
                leakPointRepository.save(bizLeakPointEntity);
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

    public RespBean deleteBizLeakPointEntity(Integer id) {
        if(id != null) {
            try { leakPointRepository.deleteBizLeakPointEntity(id);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("删除失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("删除成功！");
    }

    public RespBean findDetail(Integer id) {
        BizLeakPointEntity blpe = null;
        if(id != null) {
            try {
                blpe = leakPointRepository.findBizLeakPointEntity(id);
                RespBean bean = oauthService.getChineseName(blpe.getCommitBy());
                String chineseName = (String)bean.getObj();
                blpe.setCommitBy(chineseName);
                if(bean.getStatus() == 500){
                    throw new RuntimeException("Feign调用权限服务失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("查询成功！",blpe);
    }

     @Transactional(propagation= Propagation.NOT_SUPPORTED)
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
        List<BizLeakPointEntity> list = leakPointRepository.findOfList(user);
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                BizLeakPointEntity bizLeakPointEntity = list.get(i);
                bizLeakPointEntity.setCommitBy(chineseName);
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
