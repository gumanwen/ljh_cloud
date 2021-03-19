package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.ToolUtils.DateFormatUtils;
import com.yaobanTech.springcloud.ToolUtils.UrlUtils;
import com.yaobanTech.springcloud.domain.*;
import com.yaobanTech.springcloud.repository.BizHiddenDangerPointRepository;
import com.yaobanTech.springcloud.repository.BizSignPointMapper;
import com.yaobanTech.springcloud.repository.SuggestionRepository;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HiddenDangerPointServiceImpl {

    @Autowired
    @Lazy
    private BizHiddenDangerPointRepository hiddenDangerPointRepository;

    @Autowired
    @Lazy
    private SuggestionRepository suggestionRepository;

    @Autowired
    @Lazy
    private BizSignPointMapper bizSignPointMapper;

    @Autowired
    @Lazy
    private RouteServiceImpl routeService;

    @Autowired
    @Lazy
    private OauthService oauthService;

    @Autowired
    @Lazy
    private FileService fileService;

    @Autowired
    @Lazy
    private RedisService redisService;

    @Autowired
    @Lazy
    private UrlUtils urlUtils;

    @Resource
    private HiddenDangerPointServiceImpl hiddenDangerPointService;

    @Transactional
    @GlobalTransactional
    public RespBean saveHiddenDangerPoint(String param,MultipartFile[] files,HttpServletRequest request) {
        BizHiddenDangerPointEntity bizHiddenDangerPointEntity = JSONObject.parseObject(param, BizHiddenDangerPointEntity.class);
        String type = "yhdfj";
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
                LoginUser u = urlUtils.getAll(request);
                String user = u.getLoginname();
                /*if(oauthService.getCurrentUser(token).getStatus() == 500){
                    throw new RuntimeException("Feign调用权限服务失败");
                }*/
                bizHiddenDangerPointEntity.setCommitDate(DateFormatUtils.DateToStr(new Date()));
                bizHiddenDangerPointEntity.setEnabled(1);
                bizHiddenDangerPointEntity.setHiddenDangerStatus("53");
                bizHiddenDangerPointEntity.setCommitBy(user);
                bizHiddenDangerPointEntity.setHiddenDangerPointCode(hiddenDangerPointCode);
                hiddenDangerPointRepository.save(bizHiddenDangerPointEntity);
                fileService.saveByPid(files,bizHiddenDangerPointEntity.getHiddenDangerPointCode(), type) ;

            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return RespBean.error("保存失败！");
            }
        }else{
            return RespBean.error("数据为空！");
        }
        return RespBean.ok("保存成功！");
    }

    @Transactional
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

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespBean findDetail(Integer id,HttpServletRequest request) {
        BizHiddenDangerPointEntity bdpe = null;
        String type = "yhdfj";
        if(id != null) {
            try {
                bdpe = hiddenDangerPointRepository.findHiddenDangerPoint(id);
                List<BizSuggestionEntity> suggestionEntityList = suggestionRepository.findList(bdpe.getHiddenDangerPointCode());
                String user = bdpe.getCommitBy();
                String chineseName = (String)oauthService.getChineseName(user).getObj();
                HashMap<String,Object> hiddenDangerStatusEnum = (HashMap)routeService.findEnum(bdpe.getHiddenDangerStatus()).getObj();
                HashMap<String,Object> projectTypeEnum = (HashMap)routeService.findEnum(bdpe.getProjectType()).getObj();
                HashMap<String,Object> riskLevelEnum = (HashMap)routeService.findEnum(bdpe.getRiskLevel()).getObj();
                HashMap<String,Object> constructionTypeEnum = (HashMap)routeService.findEnum(bdpe.getConstructionType()).getObj();
                bdpe.setHiddenDangerPointStatusEnum(hiddenDangerStatusEnum);
                bdpe.setProjectTypeEnum(projectTypeEnum);
                bdpe.setRiskLevelEnum(riskLevelEnum);
                bdpe.setConstructionTypeEnum(constructionTypeEnum);
//                bdpe.setCommitByCN(chineseName);
                bdpe.setHandleAdvice(suggestionEntityList);
                RespBean bean = fileService.selectOneByPid(bdpe.getHiddenDangerPointCode(), type);
                List<HashMap<String, Object>> maps = (List<HashMap<String, Object>>) bean.getObj();
                bdpe.setFileList(maps);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("查询成功！",bdpe);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public RespBean findListByUser(HttpServletRequest request) throws UnsupportedEncodingException {
        LoginUser u = urlUtils.getAll(request);
        String chineseName = u.getName();
        String user = u.getLoginname();
        String role = u.getRoleLists();
        List<BizHiddenDangerPointEntity> list = null;
        if(role.contains("BZZ")){
            list = hiddenDangerPointRepository.findAll();
        }else{
            list = hiddenDangerPointRepository.findList(user);
        }
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                BizHiddenDangerPointEntity bizHiddenDangerPointEntity = list.get(i);
//                bizHiddenDangerPointEntity.setCommitByCN(chineseName);;
                List<BizSuggestionEntity> suggestionEntityList = suggestionRepository.findList(bizHiddenDangerPointEntity.getHiddenDangerPointCode());
                HashMap<String,Object> hiddenDangerStatusEnum = (HashMap)routeService.findEnum(bizHiddenDangerPointEntity.getHiddenDangerStatus()).getObj();
                HashMap<String,Object> projectTypeEnum = (HashMap)routeService.findEnum(bizHiddenDangerPointEntity.getProjectType()).getObj();
                HashMap<String,Object> riskLevelEnum = (HashMap)routeService.findEnum(bizHiddenDangerPointEntity.getRiskLevel()).getObj();
                HashMap<String,Object> constructionTypeEnum = (HashMap)routeService.findEnum(bizHiddenDangerPointEntity.getConstructionType()).getObj();
                bizHiddenDangerPointEntity.setHiddenDangerPointStatusEnum(hiddenDangerStatusEnum);
                bizHiddenDangerPointEntity.setProjectTypeEnum(projectTypeEnum);
                bizHiddenDangerPointEntity.setRiskLevelEnum(riskLevelEnum);
                bizHiddenDangerPointEntity.setConstructionTypeEnum(constructionTypeEnum);
                bizHiddenDangerPointEntity.setHandleAdvice(suggestionEntityList);

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
        List<BizHiddenDangerPointEntity> collect = list.stream().sorted(Comparator.comparing(BizHiddenDangerPointEntity::getEnabled).reversed().thenComparing(BizHiddenDangerPointEntity::getCommitDate).reversed()).collect(Collectors.toList());
        return RespBean.ok("查询成功！",collect);
    }

    @Transactional
    public RespBean ignore(String hiddenDangerPointCode) {
        if(hiddenDangerPointCode != null) {
            hiddenDangerPointRepository.updateHiddenDangerPointStatus(hiddenDangerPointCode);
            return RespBean.ok("修改成功！");
        }else{
            return RespBean.error("Id为空！");
        }
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public RespBean findCondition(HiddenDangerPointQuery hiddenDangerPointQuery, HttpServletRequest request) throws UnsupportedEncodingException {
        HashMap<String,Object> hashMap = new HashMap<>();
        List<String> codeList = new ArrayList<>();
        List<BizHiddenDangerPointEntity> list = null;
        if(hiddenDangerPointQuery != null){
            LoginUser u = urlUtils.getAll(request);
            String user = u.getLoginname();
            String chineseName = u.getName();
            list = bizSignPointMapper.hiddenDangerPointQuery(hiddenDangerPointQuery);
            if(!list.isEmpty()){
                for (int i = 0; i < list.size(); i++) {
                    BizHiddenDangerPointEntity hiddenDangerPointEntity = list.get(i);
                    HashMap<String,Object> hiddenDangerStatusEnum = (HashMap)routeService.findEnum(hiddenDangerPointEntity.getHiddenDangerStatus()).getObj();
                    HashMap<String,Object> projectTypeEnum = (HashMap)routeService.findEnum(hiddenDangerPointEntity.getProjectType()).getObj();
                    HashMap<String,Object> riskLevelEnum = (HashMap)routeService.findEnum(hiddenDangerPointEntity.getRiskLevel()).getObj();
                    HashMap<String,Object> constructionTypeEnum = (HashMap)routeService.findEnum(hiddenDangerPointEntity.getConstructionType()).getObj();
                    hiddenDangerPointEntity.setHiddenDangerPointStatusEnum(hiddenDangerStatusEnum);
                    hiddenDangerPointEntity.setProjectTypeEnum(projectTypeEnum);
                    hiddenDangerPointEntity.setRiskLevelEnum(riskLevelEnum);
                    hiddenDangerPointEntity.setConstructionTypeEnum(constructionTypeEnum);
//                    hiddenDangerPointEntity.setCommitByCN(chineseName);
                    codeList.add(hiddenDangerPointEntity.getHiddenDangerPointCode());
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
                hashMap.put("HiddenDangerPointList",list);
                hashMap.put("CodeList",codeList);
            }
        }else {
            return RespBean.error("查询条件为空！");
        }
        return RespBean.ok("查询成功！",hashMap);
    }

}
