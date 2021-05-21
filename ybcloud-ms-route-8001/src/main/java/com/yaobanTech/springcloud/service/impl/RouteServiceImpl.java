package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.ToolUtils.UrlUtils;
import com.yaobanTech.springcloud.domain.*;
import com.yaobanTech.springcloud.domain.enumDef.EnumMenu;
import com.yaobanTech.springcloud.repository.BizRouteRepository;
import com.yaobanTech.springcloud.repository.BizSignPointMapper;
import com.yaobanTech.springcloud.repository.BizSignPointRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.RouteMatcher;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl {
    @Autowired
    @Lazy
    private BizRouteRepository bizRouteRepository;

    @Autowired
    @Lazy
    private BizSignPointRepository bizSignPointRepository;

    @Autowired
    @Lazy
    private BizSignPointMapper bizSignPointMapper;

    @Autowired
    @Lazy
    private SignPointServiceImpl signPointService;

    @Autowired
    @Lazy
    private PlanService planService;

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
    private UrlUtils urlUtils;

    @Transactional
    public RespBean saveRoute(HashMap<String,Object> param,HttpServletRequest request) throws UnsupportedEncodingException {
        HashMap<String,Object> hashMap = new HashMap<>();
        String header = request.getHeader("Authorization");
        String token =  StringUtils.substringAfter(header, "Bearer ");
        LoginUser u = urlUtils.getAll(request);
        String user =u.getLoginname();
        BizRoute bizRoute = null;
        if(param != null){
            bizRoute = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizRoute.class);
            BizRoute route = bizRouteRepository.findExit(bizRoute.getRouteName(),bizRoute.getWaterManagementOffice());
            if(route != null){
                return RespBean.error("该用水管理所下路线名称已存在,请修改路线名重试！");
            }
        }
        if(bizRoute != null && !bizRoute.getBizSignPoints().isEmpty() && bizRoute.getId() == null) {
            try {
                bizRoute.setCreatedTime(new Date());
                bizRoute.setRouteCreator(user);
                bizRoute.setEnabled(1);
                BizRoute route = bizRouteRepository.save(bizRoute);
                List<BizSignPoint> pointList = bizRoute.getBizSignPoints();
                for (int i = 0; i <pointList.size() ; i++) {
                    if(pointList.get(i).getTroubleCode() != null && !"".equals(pointList.get(i).getTroubleCode())){
                        bizSignPointRepository.copyFiles(pointList.get(i).getId().toString(),pointList.get(i).getTroubleCode());
                    }
                    pointList.get(i).setRouteId(bizRoute.getId());
                    pointList.get(i).setRouteType(bizRoute.getRouteType());
                    pointList.get(i).setPointInspectionType(bizRoute.getPointInspectionType());
                    pointList.get(i).setEnabled(1);
                    pointList.get(i).setRouteId(bizRoute.getId());
                }
                List<BizSignPoint> list = bizSignPointRepository.saveAll(pointList);

            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("保存失败！");
            }
            hashMap.put("signPointList",bizRoute.getBizSignPoints());
            hashMap.put("routeName",bizRoute.getRouteName());
            hashMap.put("routeId",bizRoute.getId());
        }else{
            return RespBean.error("数据为空！");
        }
        return RespBean.ok("保存成功！",hashMap);
    }

    @Transactional
    public RespBean updateRoute(HashMap<String,Object> param) {
        BizRoute bizRoute = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizRoute.class);
        Boolean flag = null;
        if(bizRoute.getId() != null){
            flag = (Boolean) inspectService.comfirmModify(bizRoute.getId()).getObj();
        }
        if(flag == true && !bizRoute.getBizSignPoints().isEmpty()) {
            try {
                List<BizSignPoint> list = bizRoute.getBizSignPoints();
                List<BizSignPoint> points = list.stream().map(a -> {
                    a.setEnabled(1);
                    a.setPointInspectionType(bizRoute.getPointInspectionType());
                    a.setRouteType(bizRoute.getRouteType());
                    return a;
                }).collect(Collectors.toList());
                bizSignPointRepository.saveAll(points);
               bizRouteRepository.save(bizRoute);

            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return RespBean.error("修改失败！");
            }
        }else{
            return RespBean.error("id、签到点信息为空或该路线包含任务,无法修改！");
        }
        return RespBean.ok("修改成功！",bizRoute.getBizSignPoints());
    }

    @Transactional
    public RespBean deleteRoute(Integer id) {
        Integer i = null;
        if(id != null) {
            Boolean bool = null;
            try {
                bool = inspectService.deleteRoute(id,0);
                if(bool){
                    Integer integer = bizRouteRepository.deleteRoute(id);
                }else{
                    return RespBean.error("该路线下任务状态不允许删除！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("删除成功！",i);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public RespBean findCondition(RouteCondition routeCondition,HttpServletRequest request) throws UnsupportedEncodingException {
        //获取当前用户
        LoginUser u = urlUtils.getAll(request);
        List<BizRoute> list = bizSignPointMapper.findRouteCondition(routeCondition);
        //获取用户名列表
        List<String> nameList = list.stream().map(o -> {
            return o.getRouteCreator();
        }).collect(Collectors.toList());
        RespBean r1 = oauthService.getNameList(nameList);
        List<HashMap<String, String>> names = (List<HashMap<String, String>>) r1.getObj();
        //查询附件
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                BizRoute route = list.get(i);
                RespBean r2 = signPointService.findList(route.getId());
                List<BizSignPoint> points = (List<BizSignPoint>) r2.getObj();
                route.setBizSignPoints(points);
                if(points.size()>0){
                    for(int j =0; j<points.size();j++){
                        //获取报建文件列表
                        if(FieldUtils.isObjectNotEmpty(points.get(j).getFileType())) {
                            RespBean respBean = fileService.selectOneByPid(String.valueOf((Integer) points.get(j).getId()), (String) points.get(j).getFileType());
                            List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>) respBean.getObj();
                            if(respBean.getStatus() == 500){
                                throw new RuntimeException("Feign调用文件服务失败");
                            }
                            points.get(j).setFileList(fileList);
                        }
                    }
                }
                Map waterOfficeMenu = (Map) findEnum(route.getWaterManagementOffice()).getObj();
                Map routeTypeMenu = (Map) findEnum(route.getRouteType()).getObj();
                Map pointInspectionTypeMenu = (Map) findEnum(route.getPointInspectionType()).getObj();
                route.setRouteTypeMenu(routeTypeMenu);
                route.setWaterOfficeMenu(waterOfficeMenu);
                route.setPointInspectionTypeMenu(pointInspectionTypeMenu);
                names.stream().forEach(a -> {
                    if(route.getRouteCreator().equals(a.get("username"))){
                        route.setRouteCreatorCN(a.get("name"));
                    }
                });
            }
        }
       return RespBean.ok("查询成功！",list);
    }


    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public RespBean findAll(HttpServletRequest request) throws UnsupportedEncodingException {
        LoginUser u = urlUtils.getAll(request);
        String user = u.getLoginname();
        String role = u.getRoleLists();
        List<BizRoute> list = null;
        if(!"".equals(role) && role !=null && role.contains("BZZ")){
            list = bizRouteRepository.findAll();
        }
          else{
            list = bizRouteRepository.findList(user);
        }
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                BizRoute route = list.get(i);
                String chineseName = urlUtils.getNameByUsername(route.getRouteCreator(),request);
                List<BizSignPoint> points = route.getBizSignPoints();
                if(points.size()>0){
                    for(int j =0; j<points.size();j++){
                        //获取报建文件列表
                        if(FieldUtils.isObjectNotEmpty(points.get(j).getFileType())) {
                            RespBean respBean = fileService.selectOneByPid(String.valueOf((Integer) points.get(j).getId()), (String) points.get(j).getFileType());
                            List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>) respBean.getObj();
                            if(respBean.getStatus() == 500){
                                throw new RuntimeException("Feign调用文件服务失败");
                            }
                            points.get(j).setFileList(fileList);
                        }
                    }
                }
                Map waterOfficeMenu = (Map) findEnum(route.getWaterManagementOffice()).getObj();
                Map routeTypeMenu = (Map) findEnum(route.getRouteType()).getObj();
                Map pointInspectionTypeMenu = (Map) findEnum(route.getPointInspectionType()).getObj();
                route.setRouteTypeMenu(routeTypeMenu);
                route.setWaterOfficeMenu(waterOfficeMenu);
                route.setPointInspectionTypeMenu(pointInspectionTypeMenu);
                route.setRouteCreatorCN(chineseName);
            }
        }
        List<BizRoute> routes = list.stream().
                sorted(Comparator.comparing(BizRoute::getCreatedTime).reversed().thenComparing(BizRoute::getEnabled)).
                collect(Collectors.toList());
        return RespBean.ok("查询成功！",routes);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public RespBean findExitAll(HttpServletRequest request) throws UnsupportedEncodingException {
        LoginUser u = urlUtils.getAll(request);
        String user = u.getLoginname();
        String chineseName = urlUtils.getNameByUsername(user,request);
        List<BizRoute> list = bizRouteRepository.findExitList(user);
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                BizRoute route = list.get(i);
                List<BizSignPoint> points = route.getBizSignPoints();
                if(points.size()>0){
                    for(int j =0; j<points.size();j++){
                        //获取报建文件列表
                        if(FieldUtils.isObjectNotEmpty(points.get(j).getFileType())) {
                            RespBean respBean = fileService.selectOneByPid(String.valueOf((Integer) points.get(j).getId()), (String) points.get(j).getFileType());
                            List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>) respBean.getObj();
                            if(respBean.getStatus() == 500){
                                throw new RuntimeException("Feign调用文件服务失败");
                            }
                            points.get(j).setFileList(fileList);
                        }
                    }
                }
                Map waterOfficeMenu = (Map) findEnum(route.getWaterManagementOffice()).getObj();
                Map routeTypeMenu = (Map) findEnum(route.getRouteType()).getObj();
                Map pointInspectionTypeMenu = (Map) findEnum(route.getPointInspectionType()).getObj();
                route.setRouteTypeMenu(routeTypeMenu);
                route.setWaterOfficeMenu(waterOfficeMenu);
                route.setPointInspectionTypeMenu(pointInspectionTypeMenu);
                route.setRouteCreator(user);
                route.setRouteCreatorCN(chineseName);
            }
        }
        return RespBean.ok("查询成功！",list);
    }

    public RespBean findSelection(String code){
        List<HashMap<String, Object>> selection = bizRouteRepository.findSelection(code);
        return RespBean.ok("查询成功！",selection);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public RespBean findDetail(Integer id,HttpServletRequest request) throws UnsupportedEncodingException {
        BizRoute br = bizRouteRepository.findDetail(id);
        if(br != null) {
            List<BizSignPoint> pointList = (List<BizSignPoint>) signPointService.findList(br.getId()).getObj();
            List<BizSignPoint> list = br.getBizSignPoints();
            String chineseName = (String)urlUtils.getNameByUsername(br.getRouteCreator(),request);
            //获取报建文件列表
            if(list.size()>0){
                for(int i =0; i<list.size(); i++){
                    //获取报建文件列表
                    if(FieldUtils.isObjectNotEmpty(list.get(i).getFileType())) {
                        RespBean respBean = fileService.selectOneByPid(String.valueOf((Integer) list.get(i).getId()), (String) list.get(i).getFileType());
                        Object o = respBean.getObj();
                        List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>)o ;
                        if(respBean.getStatus() == 500){
                            throw new RuntimeException("Feign调用文件服务失败");
                        }
                        list.get(i).setFileList(fileList);
                    }
                }
            }

            Map waterOfficeMenu = (Map) findEnum(br.getWaterManagementOffice()).getObj();
            Map routeTypeMenu = (Map) findEnum(br.getRouteType()).getObj();
            Map pointInspectionTypeMenu = (Map) findEnum(br.getPointInspectionType()).getObj();
            br.setRouteTypeMenu(routeTypeMenu);
            br.setWaterOfficeMenu(waterOfficeMenu);
            br.setBizSignPoints(pointList);
            br.setPointInspectionTypeMenu(pointInspectionTypeMenu);
            br.setBizSignPoints(list);
            br.setRouteCreatorCN(chineseName);
        }
        return RespBean.ok("查询成功！",br);
    }

    public RespBean findListByIds(List<Integer> routeIds){
        List<BizRoute> list = new ArrayList<>();
        if(!routeIds.isEmpty()){
            for (int i = 0; i < routeIds.size(); i++) {
                BizRoute route = bizRouteRepository.findDetail(routeIds.get(i));
                Map waterOfficeMenu = (Map) findEnum(route.getWaterManagementOffice()).getObj();
                route.setWaterOfficeMenu(waterOfficeMenu);
                list.add(route);
            }
        }
        return RespBean.ok("查询成功！",list);
    }

    public RespBean findEnumMenu(String mode){
        List<Map<String, String>> list = new ArrayList<>();
        if(mode != null) {
            EnumMenu[] menus = EnumMenu.values();
            for (int i = 0; i < menus.length; i++) {
                Map<String, String> map = new HashMap();
                EnumMenu menu = menus[i];
                if (mode.equals(menu.getMode())) {
                    map.put("mode", menu.getMode());
                    map.put("code", menu.getCode());
                    map.put("desc", menu.getDesc());
                    list.add(map);
                }
            }
        }else{
            return RespBean.error("枚举mode为空！");
        }
        return RespBean.ok("查询成功！", list);
    }

    public RespBean findEnum(String code){
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

    public RespBean findRouteIds(String waterManagementOffice,Integer routeId,String pointInspectionType,Integer planId ,String planPorid,String planType,String routeType){
        List<HashMap<String, Object>> list  = null;
        if("".equals(waterManagementOffice)){
            waterManagementOffice = null;
        }
        if("".equals(pointInspectionType)){
            pointInspectionType = null;
        }
        if("".equals(planPorid)){
            planPorid = null;
        }
        if("".equals(planType)){
            planType = null;
        }
        if("".equals(routeType)){
            routeType = null;
        }
        List<HashMap<String, Object>> ids = bizSignPointMapper.findRouteIds(waterManagementOffice, routeId, pointInspectionType, planId, planPorid, planType,routeType);
        if(!ids.isEmpty()){
          list = ids.stream().map(o -> {
                        Map waterOfficeMenu = (Map) findEnum((String) o.get("water_management_office")).getObj();
                        Map routeTypeMenu = (Map) findEnum((String) o.get("route_type")).getObj();
                        Map pointInspectionTypeMenu = (Map) findEnum((String) o.get("point_inspection_type")).getObj();
                        Map planPoridMenu = (Map) findEnum((String) o.get("plan_porid")).getObj();
                        Map planTypeMenu = (Map) findEnum((String) o.get("plan_type")).getObj();
                        o.put("water_office_menu", waterOfficeMenu);
                        o.put("route_type_menu", routeTypeMenu);
                        o.put("point_inspection_type_menu", pointInspectionTypeMenu);
                        o.put("plan_porid_menu", planPoridMenu);
                        o.put("plan_type_menu", planTypeMenu);
                        return o;
                    }
            ).collect(Collectors.toList());

        }
        return RespBean.ok("查询成功！",list);
    }


    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public RespBean openFindDetail(Integer id,String token,Integer type,HttpServletRequest request) throws UnsupportedEncodingException {
        BizRoute br = bizRouteRepository.findDetail(id);
        if(type == 1){
            request.setAttribute("Authorization",token);
        }else{
            request.setAttribute("TW-Authorization",token);
        }
        if(br != null) {
            List<BizSignPoint> pointList = (List<BizSignPoint>) signPointService.findList(br.getId()).getObj();
            List<BizSignPoint> list = br.getBizSignPoints();
            String chineseName = (String)urlUtils.getNameByUsername(br.getRouteCreator(),request);
            //获取报建文件列表
            if(list.size()>0){
                for(int i =0; i<list.size(); i++){
                    //获取报建文件列表
                    if(FieldUtils.isObjectNotEmpty(list.get(i).getFileType())) {
                        RespBean respBean = fileService.selectOneByPid(String.valueOf((Integer) list.get(i).getId()), (String) list.get(i).getFileType());
                        Object o = respBean.getObj();
                        List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>)o ;
                        if(respBean.getStatus() == 500){
                            throw new RuntimeException("Feign调用文件服务失败");
                        }
                        list.get(i).setFileList(fileList);
                    }
                }
            }

            Map waterOfficeMenu = (Map) findEnum(br.getWaterManagementOffice()).getObj();
            Map routeTypeMenu = (Map) findEnum(br.getRouteType()).getObj();
            Map pointInspectionTypeMenu = (Map) findEnum(br.getPointInspectionType()).getObj();
            br.setRouteTypeMenu(routeTypeMenu);
            br.setWaterOfficeMenu(waterOfficeMenu);
            br.setBizSignPoints(pointList);
            br.setPointInspectionTypeMenu(pointInspectionTypeMenu);
            br.setBizSignPoints(list);
            br.setRouteCreatorCN(chineseName);
        }
        return RespBean.ok("查询成功！",br);
    }
}
