package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.ToolUtils.DateFormatUtils;
import com.yaobanTech.springcloud.ToolUtils.UrlUtils;
import com.yaobanTech.springcloud.domain.*;
import com.yaobanTech.springcloud.repository.BizLeakPointRepository;
import com.yaobanTech.springcloud.repository.BizSignPointMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeakPointServiceImpl {
    @Autowired
    @Lazy
    private BizLeakPointRepository leakPointRepository;

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
    private UrlUtils urlUtils;

    @GlobalTransactional
    public RespBean saveLeakPoint(String param, MultipartFile[] fileList,HttpServletRequest request) {
        BizLeakPointEntity bizLeakPointEntity = JSONObject.parseObject(param, BizLeakPointEntity.class);
        String type = "ldfj";
        if(bizLeakPointEntity != null) {
            try {
                String leakPointCode = null;
                LoginUser u = urlUtils.getAll(request);
                String user = u.getLoginname();
                String deptName = u.getDeptName();
                if(deptName.contains("城南")){
                    leakPointCode =redisService.createGenerateCode("隐患点","CNYH",true,2);
                }
                else if(deptName.contains("城北")){
                    leakPointCode =redisService.createGenerateCode("隐患点","CBYH",true,2);
                }
                else if(deptName.contains("石角")){
                    leakPointCode =redisService.createGenerateCode("隐患点","SJYH",true,2);
                }else{
                    return RespBean.error("用水管理所参数不符合系统约定，生成编号异常！");
                }
                bizLeakPointEntity.setCommitDate(DateFormatUtils.DateToStr(new Date()));
                bizLeakPointEntity.setEnabled(1);
                bizLeakPointEntity.setCommitBy(user);
                bizLeakPointEntity.setLeakPointStatus("53");
                bizLeakPointEntity.setLeakPointCode(leakPointCode);
                leakPointRepository.save(bizLeakPointEntity);
                fileService.saveByPid(fileList,bizLeakPointEntity.getLeakPointCode(),type) ;
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("保存失败！");
            }
        }else{
            return RespBean.error("数据为空！");
        }
        return RespBean.ok("保存成功！");
    }

    @Transactional
    public RespBean updateBizLeakPointEntity(HashMap<String,Object> param) {
        BizLeakPointEntity bizLeakPointEntity = null;
        if(!param.isEmpty()) {
            bizLeakPointEntity = JSONObject.parseObject(JSONObject.toJSONString(param), BizLeakPointEntity.class);
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

    @Transactional
    public RespBean ignore(String leakPointCode) {
        if(leakPointCode != null) {
             leakPointRepository.updateLeakPointStatus(leakPointCode);
             return RespBean.ok("修改成功！");
        }else{
            return RespBean.error("Id为空！");
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

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespBean findDetail(Integer id,HttpServletRequest request) {
        BizLeakPointEntity blpe = null;
        String type = "ldfj";
        if(id != null) {
            try {
                blpe = leakPointRepository.findBizLeakPointEntity(id);
                String user = blpe.getCommitBy();
                String chineseName = (String)urlUtils.getNameByUsername(user,request);
                HashMap<String,Object> leakPointStatusEnum = (HashMap)routeService.findEnum(blpe.getLeakPointStatus()).getObj();
                HashMap<String,Object> abnormalPhenomenaEnum = (HashMap)routeService.findEnum(blpe.getAbnormalPhenomena()).getObj();
                blpe.setCommitByCN(chineseName);
                blpe.setAbnormalPhenomenaEnum(abnormalPhenomenaEnum);
                blpe.setLeakPointStatusEnum(leakPointStatusEnum);
                RespBean bean = fileService.selectOneByPid(blpe.getLeakPointCode(), type);
                List<HashMap<String, Object>> maps = (List<HashMap<String, Object>>) bean.getObj();
                blpe.setFileList(maps);
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
     public RespBean findListByUser(HttpServletRequest request) throws UnsupportedEncodingException {
        LoginUser u = urlUtils.getAll(request);
        String user = u.getLoginname();
        String chineseName = u.getName();
         String role = u.getRoleLists();
         List<BizLeakPointEntity> list = null;
         if(!"".equals(role) && role !=null && role.contains("BZZ")){
             list = leakPointRepository.findAll();
         }
         else{
             list = leakPointRepository.findOfList(user);
         }
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                BizLeakPointEntity bizLeakPointEntity = list.get(i);
                HashMap<String,Object> leakPointStatusEnum = (HashMap)routeService.findEnum(bizLeakPointEntity.getLeakPointStatus()).getObj();
                HashMap<String,Object> abnormalPhenomenaEnum = (HashMap)routeService.findEnum(bizLeakPointEntity.getAbnormalPhenomena()).getObj();
                HashMap<String,Object> assetTypeEnum = (HashMap)routeService.findEnum(bizLeakPointEntity.getAssetType()).getObj();

                bizLeakPointEntity.setCommitByCN(chineseName);
                bizLeakPointEntity.setLeakPointStatusEnum(leakPointStatusEnum);
                bizLeakPointEntity.setAbnormalPhenomenaEnum(abnormalPhenomenaEnum);
                bizLeakPointEntity.setAssetTypeEnum(assetTypeEnum);
                //获取报建文件列表
                RespBean respBean = fileService.selectOneByPid(bizLeakPointEntity.getLeakPointCode(), "ldfj");
                List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>) respBean.getObj();
                if(respBean.getStatus() == 500){
                    throw new RuntimeException("Feign调用文件服务失败");
                }
                bizLeakPointEntity.setFileList(fileList);
            }
        }
         List<BizLeakPointEntity> collect = list.stream().sorted(Comparator.comparing(BizLeakPointEntity::getEnabled).reversed().thenComparing(BizLeakPointEntity::getCommitDate).reversed()).collect(Collectors.toList());
         return RespBean.ok("查询成功！",collect);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
     public RespBean findCondition(LeakPointQuery leakPointQuery,HttpServletRequest request) throws UnsupportedEncodingException {
        HashMap<String,Object> hashMap = new HashMap<>();
        List<String> codeList = new ArrayList<>();
        List<BizLeakPointEntity> list = new ArrayList<>();
        if(leakPointQuery != null){
            LoginUser u = urlUtils.getAll(request);
            String user = u.getLoginname();
            String chineseName = u.getName();
            list = bizSignPointMapper.leakPointQuery(leakPointQuery);
            if(!list.isEmpty()){
                for (int i = 0; i < list.size(); i++) {
                    BizLeakPointEntity bizLeakPointEntity = list.get(i);
                    HashMap<String,Object> leakPointStatusEnum = (HashMap)routeService.findEnum(bizLeakPointEntity.getLeakPointStatus()).getObj();
                    HashMap<String,Object> abnormalPhenomenaEnum = (HashMap)routeService.findEnum(bizLeakPointEntity.getAbnormalPhenomena()).getObj();
                    bizLeakPointEntity.setCommitByCN(chineseName);
                    bizLeakPointEntity.setLeakPointStatusEnum(leakPointStatusEnum);
                    bizLeakPointEntity.setAbnormalPhenomenaEnum(abnormalPhenomenaEnum);
                    codeList.add(bizLeakPointEntity.getLeakPointCode());
                }
                hashMap.put("LeakPointList",list);
                hashMap.put("CodeList",codeList);
            }
       }else {
            return RespBean.error("查询条件为空！");
        }
        return RespBean.ok("查询成功！",hashMap);
    }

}
