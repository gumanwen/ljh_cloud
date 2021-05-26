package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.ToolUtils.DateFormatUtils;
import com.yaobanTech.springcloud.ToolUtils.UrlUtils;
import com.yaobanTech.springcloud.domain.*;
import com.yaobanTech.springcloud.repository.BizHiddenDangerPointRepository;
import com.yaobanTech.springcloud.repository.BizSignPointMapper;
import com.yaobanTech.springcloud.repository.SuggestionRepository;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
                LoginUser u = urlUtils.getAll(request);
                String user = u.getLoginname();
                String deptName = u.getDeptName();
                if(deptName.contains("城南")){
                    hiddenDangerPointCode =redisService.createGenerateCode("隐患点","CNYH",true,2);
                }
                else if(deptName.contains("城北")){
                    hiddenDangerPointCode =redisService.createGenerateCode("隐患点","CBYH",true,2);
                }
                else if(deptName.contains("石角")){
                    hiddenDangerPointCode =redisService.createGenerateCode("隐患点","SJYH",true,2);
                }else{
                    return RespBean.error("用水管理所参数不符合系统约定，生成编号异常！");
                }
                bizHiddenDangerPointEntity.setCommitDate(DateFormatUtils.DateToStr(new Date()));
                bizHiddenDangerPointEntity.setEnabled(1);
                bizHiddenDangerPointEntity.setHiddenDangerPointStatus("53");
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
    public RespBean saveHiddenDangerPointWeb(BizHiddenDangerPointEntity bizHiddenDangerPointEntity,HttpServletRequest request) {
        String type = "yhdfj";
        if(bizHiddenDangerPointEntity != null) {
            try {
                String hiddenDangerPointCode = null;
                LoginUser u = urlUtils.getAll(request);
                String user = u.getLoginname();
                String deptName = u.getDeptName();
                if(deptName.contains("城南")){
                    hiddenDangerPointCode =redisService.createGenerateCode("隐患点","CNYH",true,2);
                }
                else if(deptName.contains("城北")){
                    hiddenDangerPointCode =redisService.createGenerateCode("隐患点","CBYH",true,2);
                }
                else if(deptName.contains("石角")){
                    hiddenDangerPointCode =redisService.createGenerateCode("隐患点","SJYH",true,2);
                }else{
                    return RespBean.error("用水管理所参数不符合系统约定，生成编号异常！");
                }
                bizHiddenDangerPointEntity.setCommitDate(DateFormatUtils.DateToStr(new Date()));
                bizHiddenDangerPointEntity.setEnabled(1);
                bizHiddenDangerPointEntity.setHiddenDangerPointStatus("53");
                bizHiddenDangerPointEntity.setCommitBy(user);
                bizHiddenDangerPointEntity.setHiddenDangerPointCode(hiddenDangerPointCode);
                hiddenDangerPointRepository.save(bizHiddenDangerPointEntity);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return RespBean.error("保存失败！");
            }
        }else{
            return RespBean.error("数据为空！");
        }
        return RespBean.ok("保存成功！",bizHiddenDangerPointEntity.getHiddenDangerPointCode());
    }

    @Transactional
    public RespBean updateHiddenDangerPoint(HashMap<String,Object> param) {
        BizHiddenDangerPointEntity bizHiddenDangerPointEntity = null;
        if(!param.isEmpty() && param.get("form") != null) {
            bizHiddenDangerPointEntity = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizHiddenDangerPointEntity.class);
            if(bizHiddenDangerPointEntity.getId() != null){
            try {
                String s = DateFormatUtils.DateToStr(new Date());
                bizHiddenDangerPointEntity.setModifyTime(s);
                bizHiddenDangerPointEntity.setEndDate(s);
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


    public RespBean synchronization(String code,String handle) {
        if(code != null && handle != null){
            try {
                hiddenDangerPointRepository.synchronization(code,handle);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.ok("同步失败！");
            }
        }
        return RespBean.ok("同步成功！");
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
                String chineseName = urlUtils.getNameByUsername(user,request);
                HashMap<String,Object> hiddenDangerStatusEnum = (HashMap)routeService.findEnum(bdpe.getHiddenDangerPointStatus()).getObj();
                HashMap<String,Object> projectTypeEnum = (HashMap)routeService.findEnum(bdpe.getProjectType()).getObj();
                HashMap<String,Object> riskLevelEnum = (HashMap)routeService.findEnum(bdpe.getRiskLevel()).getObj();
                HashMap<String,Object> constructionTypeEnum = (HashMap)routeService.findEnum(bdpe.getConstructionType()).getObj();
                bdpe.setSgfs(bdpe.getConstructionType().split(","));
                bdpe.setGdlx(bdpe.getProjectType().split(","));
                bdpe.setHiddenDangerPointStatusEnum(hiddenDangerStatusEnum);
                bdpe.setProjectTypeEnum(projectTypeEnum);
                bdpe.setRiskLevelEnum(riskLevelEnum);
                bdpe.setConstructionTypeEnum(constructionTypeEnum);
                bdpe.setCommitByCN(chineseName);
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

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespBean findDetailByCode(String code,HttpServletRequest request) {
        BizHiddenDangerPointEntity bdpe = null;
        String type = "yhdfj";
        if(code != null) {
            try {
                bdpe = hiddenDangerPointRepository.findHiddenDangerPoint(code);
                List<BizSuggestionEntity> suggestionEntityList = suggestionRepository.findList(bdpe.getHiddenDangerPointCode());
                String user = bdpe.getCommitBy();
                String chineseName = urlUtils.getNameByUsername(user,request);;
                HashMap<String,Object> hiddenDangerStatusEnum = (HashMap)routeService.findEnum(bdpe.getHiddenDangerPointStatus()).getObj();
                HashMap<String,Object> projectTypeEnum = (HashMap)routeService.findEnum(bdpe.getProjectType()).getObj();
                HashMap<String,Object> riskLevelEnum = (HashMap)routeService.findEnum(bdpe.getRiskLevel()).getObj();
                HashMap<String,Object> constructionTypeEnum = (HashMap)routeService.findEnum(bdpe.getConstructionType()).getObj();
                bdpe.setSgfs(bdpe.getConstructionType().split(","));
                bdpe.setGdlx(bdpe.getProjectType().split(","));
                bdpe.setHiddenDangerPointStatusEnum(hiddenDangerStatusEnum);
                bdpe.setProjectTypeEnum(projectTypeEnum);
                bdpe.setRiskLevelEnum(riskLevelEnum);
                bdpe.setConstructionTypeEnum(constructionTypeEnum);
                bdpe.setCommitByCN(chineseName);
                bdpe.setHandleAdvice(suggestionEntityList);
                RespBean bean = fileService.selectOneByPid(bdpe.getHiddenDangerPointCode(), type);
                List<HashMap<String, Object>> maps = (List<HashMap<String, Object>>) bean.getObj();
                bdpe.setFileList(maps);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("编号为空！");
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
        if(!"".equals(role) && role !=null && role.contains("BZZ")){
            list = hiddenDangerPointRepository.findAll();
        }
        else{
            list = hiddenDangerPointRepository.findList(user);
        }
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                BizHiddenDangerPointEntity bizHiddenDangerPointEntity = list.get(i);
                bizHiddenDangerPointEntity.setCommitByCN(chineseName);;
                List<BizSuggestionEntity> suggestionEntityList = suggestionRepository.findList(bizHiddenDangerPointEntity.getHiddenDangerPointCode());
                HashMap<String,Object> hiddenDangerStatusEnum = (HashMap)routeService.findEnum(bizHiddenDangerPointEntity.getHiddenDangerPointStatus()).getObj();
                HashMap<String,Object> projectTypeEnum = (HashMap)routeService.findEnum(bizHiddenDangerPointEntity.getProjectType()).getObj();
                HashMap<String,Object> riskLevelEnum = (HashMap)routeService.findEnum(bizHiddenDangerPointEntity.getRiskLevel()).getObj();
                HashMap<String,Object> constructionTypeEnum = (HashMap)routeService.findEnum(bizHiddenDangerPointEntity.getConstructionType()).getObj();
                bizHiddenDangerPointEntity.setHiddenDangerPointStatusEnum(hiddenDangerStatusEnum);
                bizHiddenDangerPointEntity.setProjectTypeEnum(projectTypeEnum);
                bizHiddenDangerPointEntity.setRiskLevelEnum(riskLevelEnum);
                bizHiddenDangerPointEntity.setSgfs(bizHiddenDangerPointEntity.getConstructionType().split(","));
                bizHiddenDangerPointEntity.setGdlx(bizHiddenDangerPointEntity.getProjectType().split(","));
                bizHiddenDangerPointEntity.setConstructionTypeEnum(constructionTypeEnum);
                bizHiddenDangerPointEntity.setHandleAdvice(suggestionEntityList);

                //获取报建文件列表
                RespBean respBean = fileService.selectOneByPid(bizHiddenDangerPointEntity.getHiddenDangerPointCode(), "yhdfj");
                List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>) respBean.getObj();
                if(respBean.getStatus() == 500){
                    throw new RuntimeException("Feign调用文件服务失败");
                }
                bizHiddenDangerPointEntity.setFileList(fileList);
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
    public RespBean findCondition(HashMap<String,Object> param, HttpServletRequest request) throws UnsupportedEncodingException {
        HiddenDangerPointQuery hiddenDangerPointQuery = JSONObject.parseObject(JSONObject.toJSONString(param), HiddenDangerPointQuery.class);
        HashMap<String,Object> hashMap = new HashMap<>();
        List<String> codeList = new ArrayList<>();
        List<BizHiddenDangerPointEntity> list = null;
        if(hiddenDangerPointQuery != null){
            LoginUser u = urlUtils.getAll(request);
//            String user = u.getLoginname();
            list = bizSignPointMapper.hiddenDangerPointQuery(hiddenDangerPointQuery);
            if(!list.isEmpty()){
                for (int i = 0; i < list.size(); i++) {
                    BizHiddenDangerPointEntity hiddenDangerPointEntity = list.get(i);
                    String chineseName = urlUtils.getNameByUsername(hiddenDangerPointEntity.getCommitBy(),request);
                    HashMap<String,Object> hiddenDangerStatusEnum = (HashMap)routeService.findEnum(hiddenDangerPointEntity.getHiddenDangerPointStatus()).getObj();
//                    HashMap<String,Object> projectTypeEnum = (HashMap)routeService.findEnum(hiddenDangerPointEntity.getProjectType()).getObj();
//                    HashMap<String,Object> riskLevelEnum = (HashMap)routeService.findEnum(hiddenDangerPointEntity.getRiskLevel()).getObj();
//                    HashMap<String,Object> constructionTypeEnum = (HashMap)routeService.findEnum(hiddenDangerPointEntity.getConstructionType()).getObj();
                    hiddenDangerPointEntity.setHiddenDangerPointStatusEnum(hiddenDangerStatusEnum);
//                    hiddenDangerPointEntity.setProjectTypeEnum(projectTypeEnum);
//                    hiddenDangerPointEntity.setRiskLevelEnum(riskLevelEnum);
//                    hiddenDangerPointEntity.setConstructionTypeEnum(constructionTypeEnum);
                    hiddenDangerPointEntity.setSgfs(hiddenDangerPointEntity.getConstructionType().split(","));
                    hiddenDangerPointEntity.setGdlx(hiddenDangerPointEntity.getProjectType().split(","));
                    hiddenDangerPointEntity.setCommitByCN(chineseName);
                    codeList.add(hiddenDangerPointEntity.getHiddenDangerPointCode());
//                    //获取报建文件列表
//                    RespBean respBean = fileService.selectOneByPid(hiddenDangerPointEntity.getHiddenDangerPointCode(), "yhdfj");
//                    List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>) respBean.getObj();
//                    hiddenDangerPointEntity.setFileList(fileList);
                }
                hashMap.put("HiddenDangerPointList",list);
                hashMap.put("CodeList",codeList);
            }
        }else {
            return RespBean.error("查询条件为空！");
        }
        return RespBean.ok("查询成功！",hashMap);
    }

    public RespBean top() {
        List<BizHiddenDangerPointEntity> res = null;
        List<BizHiddenDangerPointEntity> list = hiddenDangerPointRepository.top();
        if(!list.isEmpty()){
           res = list.stream().map(a -> {
                HashMap<String, Object> risk = (HashMap) routeService.findEnum(a.getRiskLevel()).getObj();
                a.setRiskLevelEnum(risk);
               return a;
           }).collect(Collectors.toList());
        }
        return RespBean.ok("查询成功！",list);
    }

    public RespBean conditionRecord(HashMap<String,Object> map) {
        List<BizHiddenDangerPointEntity> list = null;
        String waterUseOffice = (String)map.get("waterUseOffice");
        String start = (String)map.get("start");
        String end = (String)map.get("end");
        List<BizHiddenDangerPointEntity> dangerPointEntities = bizSignPointMapper.countDangerPointList(waterUseOffice, start, end);
        return RespBean.ok("查询成功！",dangerPointEntities);
    }

    public RespBean compare(HashMap<String,Object> map) {
        List<HashMap<String,Object>> list = new ArrayList<>();
        HashMap<String,Object> hashMap = new HashMap<>();
        HashMap<String,Object> hashMap2 = new HashMap<>();
        String waterUseOffice = (String)map.get("waterUseOffice");
        String start = (String)map.get("start");
        String end = (String)map.get("end");
        //统计总量
        Integer sum = bizSignPointMapper.countSum(waterUseOffice,start,end);
        if(sum == 0){
            return RespBean.ok("未查询到任何数据！");
        }
        //统计隐患点
        Double countDangerPoint = bizSignPointMapper.countDangerPoint(waterUseOffice,start,end);
        //统计漏点
        Double countLeakPoint = bizSignPointMapper.countLeakPoint(waterUseOffice,start,end);
        //计算隐患点/漏点占比
        double perH = 0;
        double perl = 0;
        try {
            perH = countDangerPoint / sum;
            BigDecimal bg = new BigDecimal(perH);
            perH = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            perl = countLeakPoint / sum;
            BigDecimal bg2 = new BigDecimal(perl);
            perl = bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        hashMap.put("item","隐患点比率");
        hashMap.put("count",countDangerPoint);
        hashMap.put("percent",perH);
        list.add(hashMap);
        hashMap2.put("item","漏点比率");
        hashMap2.put("count",countLeakPoint);
        hashMap2.put("percent",perl);
        list.add(hashMap2);
        return RespBean.ok("查询成功！",list);
    }

//    public RespBean handle() {
//        List<HashMap<String,Object>> list = new ArrayList<>();
//        HashMap<String,Object> hashMap = new HashMap<>();
//        //查询全部
//        Integer all = hiddenDangerPointRepository.countAll();
//        //查询已解决
//        Integer sum = hiddenDangerPointRepository.countFollowed();
//
//        //计算隐患点/漏点占比
//        double perH = 0;
//        double perl = 0;
//        try {
//            BigDecimal bg = new BigDecimal(perH);
//            perH = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            perl = 1-perH;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        hashMap.put("item","隐患点比率");
//        hashMap.put("count",);
//        return RespBean.ok("查询成功！",hashMap);
//    }

}
