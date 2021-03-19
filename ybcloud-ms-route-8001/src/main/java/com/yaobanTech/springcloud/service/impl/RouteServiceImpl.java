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
                List<BizSignPoint> pointList = bizRoute.getBizSignPoints();
                for (int i = 0; i <pointList.size() ; i++) {
                    pointList.get(i).setRouteId(bizRoute.getId());
                    pointList.get(i).setRouteType(bizRoute.getRouteType());
                    pointList.get(i).setPointInspectionType(bizRoute.getPointInspectionType());
                    pointList.get(i).setEnabled(1);
                }
                List<BizSignPoint> list = bizSignPointRepository.saveAll(pointList);
                bizRoute.setCreatedTime(new Date());
                bizRoute.setRouteCreator(user);
                bizRoute.setEnabled(1);
                BizRoute route = bizRouteRepository.save(bizRoute);
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
        if(bizRoute.getId() != null && !bizRoute.getBizSignPoints().isEmpty()) {
            try {
               bizSignPointRepository.saveAll(bizRoute.getBizSignPoints());
               bizRouteRepository.save(bizRoute);

            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("修改失败！");
            }
        }else{
            return RespBean.error("id为空或签到点信息为空！");
        }
        return RespBean.ok("修改成功！",bizRoute.getBizSignPoints());
    }

    public RespBean deleteRoute(Integer id) {
        Integer i = null;
        if(id != null) {
            try {
                BizRoute detail = bizRouteRepository.findDetail(id);
                List<BizSignPoint> signPoints = detail.getBizSignPoints();
                List<Object> list = (List<Object>) planService.findByRouteId(id).getObj();
                if(signPoints.isEmpty() && list.isEmpty()) {
                    i = bizRouteRepository.deleteRoute(id);
                }else{
                    return RespBean.error("删除失败！该路线包含计划或签到点,无法进行删除操作！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("删除失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("删除成功！",i);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public RespBean findCondition(HashMap<String,Object> hashMap,HttpServletRequest request) throws UnsupportedEncodingException {
        //获取当前用户
        LoginUser u = urlUtils.getAll(request);
        String chineseName = (String)oauthService.getChineseName(u.getLoginname()).getObj();
        /*if(oauthService.getCurrentUser(token).getStatus() == 500){
            throw new RuntimeException("Feign调用权限服务失败");
        }*/
        RouteCondition routeCondition = JSONObject.parseObject(JSONObject.toJSONString(hashMap.get("form")), RouteCondition.class);
        Specification<BizRoute> spec = new Specification<BizRoute>() {
            @Override
            public Predicate toPredicate(Root<BizRoute> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                //从root取属性
                //cb构造查询条件
                //cb连接查询条件
                if(routeCondition.getWaterManagementOffice() != null){
                    Predicate p1 = cb.equal(root.get("waterManagementOffice"), routeCondition.getWaterManagementOffice());
                    predicates.add(p1);
                }
                if(routeCondition.getPointInspectionType() != null){
                    Predicate p2 = cb.equal(root.get("pointInspectionType"), routeCondition.getPointInspectionType());
                    predicates.add(p2);
                }
                if(routeCondition.getPlanInspectionMileageStart() != null){

                    Predicate p3 = cb.ge(root.get("planInspectionMileage"), routeCondition.getPlanInspectionMileageStart());
                    predicates.add(p3);
                }
                if(routeCondition.getPlanInspectionMileageEnd() != null){

                    Predicate p3 = cb.le(root.get("planInspectionMileage"), routeCondition.getPlanInspectionMileageEnd());
                    predicates.add(p3);
                }
                if(routeCondition.getCreatedTimeStart() != null){
                    Predicate p4 = cb.greaterThanOrEqualTo(root.get("createdTime"), routeCondition.getCreatedTimeStart());
                    predicates.add(p4);
                }
                if(routeCondition.getCreatedTimeEnd() != null){
                    Predicate p4 = cb.lessThanOrEqualTo(root.get("createdTime"), routeCondition.getCreatedTimeEnd());
                    predicates.add(p4);
                }
                if(routeCondition.getRouteName() != null){
                    Predicate p5 = cb.equal(root.get("routeName"), routeCondition.getRouteName());
                    predicates.add(p5);
                }
                if(routeCondition.getRouteType() != null){
                    Predicate p6 = cb.equal(root.get("routeType"), routeCondition.getRouteType());
                    predicates.add(p6);
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        //排序
        Sort sort = Sort.by(Sort.Direction.DESC,"createdTime");
        List<BizRoute> list = bizRouteRepository.findAll(spec,sort);
        //查询附件
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
//                route.setRouteCreatorCN(chineseName);
            }
        }
       return RespBean.ok("查询成功！",list);
    }


    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public RespBean findAll(HttpServletRequest request) throws UnsupportedEncodingException {
        LoginUser u = urlUtils.getAll(request);
        String user = u.getLoginname();
        String chineseName = (String)oauthService.getChineseName(u.getLoginname()).getObj();
        String role = u.getRoleLists();
        List<BizRoute> list = null;
        if(role.contains("BZZ")){
            list = bizRouteRepository.findAll();
        }else{
            list = bizRouteRepository.findList(user);
        }
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
//                route.setRouteCreatorCN(chineseName);
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
        String chineseName = (String)oauthService.getChineseName(u.getLoginname()).getObj();
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
//                route.setRouteCreatorCN(chineseName);
            }
        }
        return RespBean.ok("查询成功！",list);
    }

    public RespBean findSelection(String code){
        List<HashMap<String, Object>> selection = bizRouteRepository.findSelection(code);
        return RespBean.ok("查询成功！",selection);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public RespBean findDetail(Integer id) throws UnsupportedEncodingException {
        BizRoute br = bizRouteRepository.findDetail(id);
        if(br != null) {
            List<BizSignPoint> pointList = (List<BizSignPoint>) signPointService.findList(br.getId()).getObj();
            List<BizSignPoint> list = br.getBizSignPoints();
            String chineseName = (String)oauthService.getChineseName(br.getRouteCreator()).getObj();
            //获取报建文件列表
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

            Map waterOfficeMenu = (Map) findEnum(br.getWaterManagementOffice()).getObj();
            Map routeTypeMenu = (Map) findEnum(br.getRouteType()).getObj();
            Map pointInspectionTypeMenu = (Map) findEnum(br.getPointInspectionType()).getObj();
            br.setRouteTypeMenu(routeTypeMenu);
            br.setWaterOfficeMenu(waterOfficeMenu);
            br.setBizSignPoints(pointList);
            br.setPointInspectionTypeMenu(pointInspectionTypeMenu);
//            br.setRouteCreatorCN(chineseName);
        }
        return RespBean.ok("查询成功！",br);
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

    public RespBean findRouteIds(String waterManagementOffice,String routeName,String pointInspectionType,String planName ,String planPorid,String planType){
        if("".equals(waterManagementOffice)){
            waterManagementOffice = null;
        }
        if("".equals(routeName)){
            routeName = null;
        }
        if("".equals(pointInspectionType)){
            pointInspectionType = null;
        }
        if("".equals(planName)){
            planName = null;
        }
        if("".equals(planPorid)){
            planPorid = null;
        }
        if("".equals(planType)){
            planType = null;
        }
        List<HashMap<String, Object>> ids = bizSignPointMapper.findRouteIds(waterManagementOffice, routeName, pointInspectionType, planName, planPorid, planType);
        return RespBean.ok("查询成功！",ids);
    }

}
