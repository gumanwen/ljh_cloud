package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.ToolUtils.DateFormatUtils;
import com.yaobanTech.springcloud.ToolUtils.UrlUtils;
import com.yaobanTech.springcloud.domain.*;
import com.yaobanTech.springcloud.domain.enumDef.EnumMenu;
import com.yaobanTech.springcloud.repository.BizRouteRepository;
import com.yaobanTech.springcloud.repository.BizSignPointMapper;
import com.yaobanTech.springcloud.repository.BizSignPointRepository;
import com.yaobanTech.springcloud.repository.BizSignedPointRepository;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SignPointServiceImpl {
    @Autowired
    @Lazy
    private BizSignPointRepository signPointRepository;

    @Autowired
    @Lazy
    private BizSignedPointRepository signedPointRepository;

    @Autowired
    @Lazy
    private BizRouteRepository bizRouteRepository;

    @Autowired
    @Lazy
    private HiddenDangerPointServiceImpl hiddenDangerPointService;

    @Autowired
    @Lazy
    private BizSignPointMapper bizSignPointMapper;

    @Autowired
    @Lazy
    private OauthService oauthService;

    @Autowired
    @Lazy
    private InspectService inspectService;

    @Autowired
    @Lazy
    private FileService fileService;

    @Autowired
    @Lazy
    private UrlUtils urlUtils;

    public RespBean saveSignPoint(HashMap<String,Object> param) {
        BizSignPoint bizSignPoint = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizSignPoint.class);
        if(bizSignPoint != null) {
            try {
                bizSignPoint.setEnabled(1);
                BizSignPoint signPoint = signPointRepository.save(bizSignPoint);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("保存失败！");
            }
        }else{
            return RespBean.error("数据为空！");
        }
        return RespBean.ok("保存成功！");
    }

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespBean updateSignPoint(HashMap<String,Object> param) {
        BizSignedPoint bizSignedPoint = null;
        Integer id = null;
        if(!param.isEmpty() && param.get("form") != null) {
             bizSignedPoint = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizSignedPoint.class);
//            BizSignPoint signPoint = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizSignPoint.class);
            if(bizSignedPoint != null){
            try {
                bizSignedPoint.setModifyTime(new Date());
//                signPointRepository.save(signPoint);
                bizSignedPoint.setSignPointStatus("合格");
                bizSignedPoint.setSignedTime(DateFormatUtils.DateToStr(new Date()));
                BizSignedPoint signedPoint = signedPointRepository.save(bizSignedPoint);
                id = signedPoint.getId();
                if( bizSignedPoint.getTroubleCode() != null && bizSignedPoint.getTroubleCode().contains("Y")){
                  hiddenDangerPointService.synchronization(bizSignedPoint.getTroubleCode(),bizSignedPoint.getHandleSuggestion());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("修改失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("修改成功！",id);
        }else{
            return RespBean.error("参数为空！");
        }
    }

    public RespBean deleteSignPoint(Integer id) {
        if(id != null) {
            try {
                BizSignPoint signPoint = signPointRepository.deleteSignPoint(id);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("删除失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("删除成功！");
    }

    public RespBean findSignedPoint(Integer id) {
        BizSignedPoint byId = null;
        if(id != null) {
            try {
                byId = signedPointRepository.findbyId(id);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("查询成功！",byId);
    }

    public RespBean findSignPoint(Integer id) {
        BizSignPoint byId = null;
        if(id != null) {
            try {
                byId = signPointRepository.findSignPointById(id);

            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("查询成功！",byId);
    }

    @GlobalTransactional
    public RespBean findListByUser(HttpServletRequest request) throws UnsupportedEncodingException {
        LoginUser u = urlUtils.getAll(request);
        String user = u.getLoginname();
        String chineseName = u.getName();
        String role = u.getRoleLists();
        List<BizRoute> routeList = null;
        if(!"".equals(role) && role !=null && role.contains("BZZ")){
            routeList = bizRouteRepository.findAll();
        }
        else{
            routeList = bizRouteRepository.findList(user);
        }
        List<BizSignPoint> list = new ArrayList<>();
        if(!routeList.isEmpty()){
            for (int i = 0; i < routeList.size(); i++) {
                BizRoute route = routeList.get(i);
                String pointInspectionType = route.getPointInspectionType();
                String routeType = route.getRouteType();
                String waterManagementOffice = route.getWaterManagementOffice();
                String routeName = route.getRouteName();
                Map pointInspectionTypeEnum = (Map) EnumMenu.findEnum(pointInspectionType).getObj();
                Map routeTypeEnum = (Map) EnumMenu.findEnum(routeType).getObj();
                Map waterManagementOfficeEnum = (Map) EnumMenu.findEnum(waterManagementOffice).getObj();
                List<BizSignPoint> points = route.getBizSignPoints();
                if(!points.isEmpty()){
                    for(int j =0; j<points.size();j++){
                        //获取报建文件列表
                        if(FieldUtils.isObjectNotEmpty(points.get(j).getFileType())) {
                            RespBean respBean = fileService.selectOneByPid(String.valueOf((Integer) points.get(j).getId()), (String) points.get(j).getFileType());
                            List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>) respBean.getObj();
                            if(respBean.getStatus() == 500){
                                throw new RuntimeException("Feign调用文件服务失败");
                            }
//                            Map signPointTypeEnum = (Map) EnumMenu.findEnum(points.get(j).getSignPointType()).getObj();
                            points.get(j).setFileList(fileList);
                            points.get(j).setRouteName(routeName);
                            points.get(j).setWaterUseOfficeEnum(waterManagementOfficeEnum);
                            points.get(j).setSignPointTypeEnum(pointInspectionTypeEnum);
                            points.get(j).setRouteTypeEnum(routeTypeEnum);
                        }
                        list.add(points.get(j));
                    }
                }
            }
        }
        List<BizSignPoint> collect = list.stream().sorted(Comparator.comparing(BizSignPoint::getEnabled).reversed()).collect(Collectors.toList());
        return RespBean.ok("查询成功！",collect);
    }

    public RespBean findList(Integer routeId) {
        List<BizSignPoint> list = null;
        if(routeId != null) {
            try {
                list = signPointRepository.findSignPointListByRouteId(routeId);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("查询成功！",list);
    }

    @GlobalTransactional
    public RespBean findListByTaskId(Integer routeId,String taskId) {
        List<BizSignedPoint> list = null;
        if(taskId != null && routeId != null) {
            try {
                 list = signedPointRepository.findListByTaskId(routeId,taskId);
                if(list.size()>0){
                    for(int i =0; i<list.size(); i++){
                        //获取报建文件列表
                        if(FieldUtils.isObjectNotEmpty(list.get(i).getFileType())) {
                            RespBean respBean = fileService.selectOneByPid(String.valueOf((Integer) list.get(i).getId()), (String) list.get(i).getFileType());
                            List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>) respBean.getObj();
                            if(respBean.getStatus() == 500){
                                throw new RuntimeException("Feign调用文件服务失败");
                            }
                            list.get(i).setFileList(fileList);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("路线或任务id为空！");
        }
        return RespBean.ok("查询成功！",list);
    }

    public RespBean findSignedList(Integer routeId,String taskId) {
        List<BizSignedPoint> list = null;
        if(taskId != null) {
            try {
                list = signedPointRepository.findSignedList(routeId,taskId);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("查询成功！",list);
    }

    public RespBean taskPoint(List<String> taskIds,Integer routeId) {
        if(!taskIds.isEmpty() && routeId != null){
            List<BizSignPoint> signPointList = signPointRepository.findSignPointListByRouteId(routeId);
            for (int i = 0; i < taskIds.size(); i++) {
                for (int j = 0; j < signPointList.size(); j++) {
                    BizSignPoint bizSignPoint = signPointList.get(j);
                    HashMap<String, Object> hashMap = objectToMap(bizSignPoint);
                    hashMap.put("taskId",taskIds.get(i));
                    hashMap.put("fileType","");
                    BizSignedPoint bizSignedPoint = JSONObject.parseObject(JSONObject.toJSONString(hashMap), BizSignedPoint.class);
                    bizSignedPoint.setId(null);
                    bizSignedPoint.setSignPointStatus("不合格");
                    bizSignedPoint.setEnabled(1);
                    signedPointRepository.save(bizSignedPoint);
                }
            }
            return RespBean.ok("新建成功！");
        }
        return RespBean.error("新建异常！任务Id或路线Id不能为空！");
    }

    public RespBean findCondition(HashMap<String,Object> map) {
        SignPointQuery signPointQuery = null;
        List<HashMap<String, Object>> maps = null;
        List<String> taskids = new ArrayList<>();
        List<HashMap<String, Object>> list = new ArrayList<>();
        if(map != null){
            signPointQuery = JSONObject.parseObject(JSONObject.toJSONString(map.get("form")), SignPointQuery.class);
                RespBean respBean = inspectService.getTaskIds(signPointQuery.getTaskStart1(), signPointQuery.getTaskEnd1(), signPointQuery.getTaskStart2(), signPointQuery.getTaskEnd2(),signPointQuery.getCheckMan());
                list = (List<HashMap<String, Object>>) respBean.getObj();
                if(list.size()>0){
                    for (int i = 0; i < list.size(); i++) {
                        taskids.add((String) list.get(i).get("inspect_task_id"));
                    }
                }else {
                   return RespBean.error("未查询到数据！");
                }
                signPointQuery.setTaskidList(StringUtils.strip(taskids.toString(),"[]").replace(" ",""));
                maps = bizSignPointMapper.findConditionElse(signPointQuery);
                //根据查询结果查出巡查人
                if(maps.size()>0){
                    for (int i = 0; i < maps.size(); i++) {
                        for (int j = 0; j < list.size(); j++) {
                            if(maps.get(i).get("task_id").equals(list.get(j).get("inspect_task_id"))){
                                maps.get(i).put("inspect_person",list.get(j).get("inspect_person"));
                                maps.get(i).put("begin_time",list.get(j).get("begin_time"));
                                maps.get(i).put("dead_time",list.get(j).get("dead_time"));
                                maps.get(i).put("plan_name",list.get(j).get("plan_name"));
                            }
                        }
                    }
                }
        }else {
            return RespBean.error("查询参数对象为空！");
        }
        return RespBean.ok("查询成功",maps);
    }

    public  RespBean findEnum(String code){
        Map<String, Object> map = new HashMap<>();
        if(code != null) {
            EnumMenu[] menus = EnumMenu.values();
            for (int i = 0; i < menus.length; i++) {
                EnumMenu menu = menus[i];
                if (code.equals(menu.getCode())) {
                    map.put("mode", menu.getMode());
                    map.put("code", menu.getCode());
                    map.put("desc", menu.getDesc());
                    break;
                }
            }
        }else{
            return RespBean.error("枚举code为空！");
        }
        return RespBean.ok("查询成功！", map);
    }

    public static HashMap<String,Object> objectToMap(Object object){
        return JSONObject.parseObject(JSONObject.toJSONString(object),HashMap.class);
    }

}
