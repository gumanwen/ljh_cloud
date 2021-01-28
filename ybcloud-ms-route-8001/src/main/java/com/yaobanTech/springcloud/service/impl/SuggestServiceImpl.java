package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.domain.BizSuggestionEntity;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.repository.BizHiddenDangerPointRepository;
import com.yaobanTech.springcloud.repository.BizLeakPointRepository;
import com.yaobanTech.springcloud.repository.SuggestionRepository;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class SuggestServiceImpl {
    @Autowired
    @Lazy
    private SuggestionRepository suggestionRepository;

    @Autowired
    @Lazy
    private BizHiddenDangerPointRepository hiddenDangerPointRepository;

    @Autowired
    @Lazy
    private BizLeakPointRepository leakPointRepository;

    @Autowired
    @Lazy
    private OauthService oauthService;

    @GlobalTransactional
    public RespBean saveSuggestion(HashMap<String,Object> param,HttpServletRequest request) {
        BizSuggestionEntity bizSuggestionEntity = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizSuggestionEntity.class);
        if(bizSuggestionEntity != null && bizSuggestionEntity.getFCode() != null) {
            try {
                String header = request.getHeader("Authorization");
                String token =  StringUtils.substringAfter(header, "Bearer ");
                String user = (String) oauthService.getCurrentUser(token).getObj();
                if(oauthService.getCurrentUser(token).getStatus() == 500){
                    throw new RuntimeException("Feign调用权限服务失败");
                }
                bizSuggestionEntity.setCommitDate(new Date());
                bizSuggestionEntity.setEnabled(1);
                bizSuggestionEntity.setCommitBy(user);
                suggestionRepository.save(bizSuggestionEntity);
                String fCode = bizSuggestionEntity.getFCode();
                if(fCode.contains("CNY")||fCode.contains("CBY")||fCode.contains("SJY")){
                    hiddenDangerPointRepository.updateHiddenDangerPoint(bizSuggestionEntity.getFCode());
                }
                if(fCode.contains("CNL")||fCode.contains("CBL")||fCode.contains("SJL")){
                    leakPointRepository.updateBizLeakPoint(bizSuggestionEntity.getFCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("保存失败！");
            }
        }else{
            return RespBean.error("数据为空！");
        }
        return RespBean.ok("保存成功！");
    }

    public RespBean updateSuggestion(HashMap<String,Object> param) {
        BizSuggestionEntity bizSuggestionEntity = null;
        if(!param.isEmpty() && param.get("form") != null) {
            bizSuggestionEntity = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizSuggestionEntity.class);
            if(bizSuggestionEntity.getId() != null){
            try {
                suggestionRepository.save(bizSuggestionEntity);
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

    public RespBean deleteSuggest(Integer id) {
        if(id != null) {
            try { suggestionRepository.deleteBizSuggestionEntity(id);
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
        BizSuggestionEntity bse = null;
        if(id != null) {
            try {
                bse = suggestionRepository.findBizSuggestionEntity(id);
                String chineseName = (String) oauthService.getChineseName(bse.getCommitBy()).getObj();
                bse.setCommitBy(chineseName);
                if(oauthService.getChineseName(bse.getCommitBy()).getStatus() == 500){
                    throw new RuntimeException("Feign调用权限服务失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("查询成功！",bse);
    }

    @GlobalTransactional
    public RespBean findListByFCode(String fCode,HttpServletRequest request) {
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
        List<BizSuggestionEntity> list = suggestionRepository.findList(fCode);
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                BizSuggestionEntity sde = list.get(i);
                sde.setCommitBy(chineseName);
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
