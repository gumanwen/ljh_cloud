package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.ToolUtils.DateFormatUtils;
import com.yaobanTech.springcloud.ToolUtils.UrlUtils;
import com.yaobanTech.springcloud.domain.BizLeakPointEntity;
import com.yaobanTech.springcloud.domain.LeakPointQuery;
import com.yaobanTech.springcloud.domain.LoginUser;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.repository.BizLeakPointRepository;
import com.yaobanTech.springcloud.repository.BizSignPointMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

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
    public RespBean saveLeakPoint(HashMap<String,Object> param,HttpServletRequest request) {
        BizLeakPointEntity bizLeakPointEntity = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizLeakPointEntity.class);
        String type = null;
        if(bizLeakPointEntity != null) {
            try {
                String leakPointCode = null;
                if("3".equals(bizLeakPointEntity.getWaterUseOffice())){
                    leakPointCode =redisService.createGenerateCode("漏点","CNL",true,4);
                }
                else if("4".equals(bizLeakPointEntity.getWaterUseOffice())){
                    leakPointCode =redisService.createGenerateCode("漏点","CBL",true,4);
                }
                else if("5".equals(bizLeakPointEntity.getWaterUseOffice())){
                    leakPointCode =redisService.createGenerateCode("漏点","SJL",true,4);
                }else{
                    return RespBean.error("用水管理所参数不符合系统约定，生成编号异常！");
                }
                String header = request.getHeader("Authorization");
                String token =  StringUtils.substringAfter(header, "Bearer ");
                LoginUser u = urlUtils.getAll(request);
                String user = u.getLoginname();
                bizLeakPointEntity.setCommitDate(DateFormatUtils.DateToStr(new Date()));
                bizLeakPointEntity.setEnabled(1);
                bizLeakPointEntity.setCommitBy(user);
                bizLeakPointEntity.setLeakPointStatus("53");
                bizLeakPointEntity.setLeakPointCode(leakPointCode);
                leakPointRepository.save(bizLeakPointEntity);
                fileService.saveByPid(bizLeakPointEntity.getLeakPointCode(), bizLeakPointEntity.getFileList(),type) ;
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
        if(id != null) {
            try {
                blpe = leakPointRepository.findBizLeakPointEntity(id);
                String user = blpe.getCommitBy();
                String chineseName = (String)oauthService.getChineseName(user).getObj();
                HashMap<String,Object> leakPointStatusEnum = (HashMap)routeService.findEnum(blpe.getLeakPointStatus()).getObj();
                HashMap<String,Object> abnormalPhenomenaEnum = (HashMap)routeService.findEnum(blpe.getAbnormalPhenomena()).getObj();
                blpe.setCommitByCN(chineseName);
                blpe.setAbnormalPhenomenaEnum(abnormalPhenomenaEnum);
                blpe.setLeakPointStatusEnum(leakPointStatusEnum);
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

        List<BizLeakPointEntity> list = leakPointRepository.findOfList(user);
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
            }
        }
        return RespBean.ok("查询成功！",list);
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
                hashMap.put("LeakPointList",list);
                hashMap.put("CodeList",codeList);
            }
       }else {
            return RespBean.error("查询条件为空！");
        }
        return RespBean.ok("查询成功！",hashMap);
    }

}
