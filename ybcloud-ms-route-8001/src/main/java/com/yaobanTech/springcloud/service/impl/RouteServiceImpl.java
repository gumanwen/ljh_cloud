package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.domain.BizRoute;
import com.yaobanTech.springcloud.domain.BizSignPoint;
import com.yaobanTech.springcloud.domain.FieldUtils;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.domain.enumDef.EnumMenu;
import com.yaobanTech.springcloud.repository.BizRouteRepository;
import com.yaobanTech.springcloud.repository.BizSignPointRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class RouteServiceImpl {
    @Autowired
    @Lazy
    private BizRouteRepository bizRouteRepository;

    @Autowired
    @Lazy
    private BizSignPointRepository bizSignPointRepository;

    @Autowired
    @Lazy
    private SignPointServiceImpl signPointService;

    @Autowired
    @Lazy
    private OauthService oauthService;

    @Autowired
    @Lazy
    private FileService fileService;

    public RespBean saveRoute(HashMap<String,Object> param,HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token =  StringUtils.substringAfter(header, "Bearer ");
        String user = (String) oauthService.getCurrentUser(token).getObj();
        BizRoute bizRoute = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizRoute.class);
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
        }else{
            return RespBean.error("数据为空！");
        }
        return RespBean.ok("保存成功！",bizRoute.getBizSignPoints());
    }

    public RespBean updateRoute(HashMap<String,Object> param) {
        BizRoute bizRoute = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizRoute.class);
        if(bizRoute.getId() != null && !bizRoute.getBizSignPoints().isEmpty()) {
            try {
                List<BizSignPoint> pointList = bizRoute.getBizSignPoints();
                for (int i = 0; i <pointList.size() ; i++) {
                    Integer id = pointList.get(i).getId();
                    if(id == null || "".equals(id)){
                        return RespBean.error("签到点ID为空,修改失败！");
                    }
                }
                List<BizSignPoint> list = bizSignPointRepository.saveAll(bizRoute.getBizSignPoints());
                BizRoute route = bizRouteRepository.save(bizRoute);

            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("保存失败！");
            }
        }else{
            return RespBean.error("id为空或签到点信息为空！");
        }
        return RespBean.ok("修改成功！");
    }

    public RespBean deleteRoute(Integer id) {
        Integer i = null;
        if(id != null) {
            try {
                BizRoute detail = bizRouteRepository.findDetail(id);
                List<BizSignPoint> signPoints = detail.getBizSignPoints();
                if(!signPoints.isEmpty()) {
                    i = bizRouteRepository.deleteRoute(id);
                }else{
                    return RespBean.error("删除失败！该路线包含签到点,无法进行删除操作！");
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

    public RespBean findCondition(String waterManagementOffice,String pointInspectionType,
                                  String planInspectionMileage,String createdTime,
                                  String routeName,
                                  String routeType) {
        Specification<BizRoute> spec = new Specification<BizRoute>() {
            @Override
            public Predicate toPredicate(Root<BizRoute> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                //从root取属性
                //cb构造查询条件
                //cb连接查询条件
                if(waterManagementOffice != null){
                    Predicate p1 = cb.equal(root.get("waterManagementOffice"), waterManagementOffice);
                     predicate = cb.and(p1);
                }
                if(pointInspectionType != null){
                    Predicate p2 = cb.equal(root.get("pointInspectionType"), pointInspectionType);
                    predicate = cb.and(p2);
                }
                if(planInspectionMileage != null){
                    Predicate p3 = cb.equal(root.get("planInspectionMileage"), planInspectionMileage);
                    predicate = cb.and(p3);
                }
                if(createdTime != null){
                    Predicate p4 = cb.equal(root.get("createdTime"), createdTime);
                    predicate = cb.and(p4);
                }
                if(routeName != null){
                    Predicate p5 = cb.equal(root.get("routeName"), routeName);
                    predicate = cb.and(p5);
                }
                if(routeType != null){
                    Predicate p6 = cb.equal(root.get("routeType"), routeType);
                    predicate = cb.and(p6);
                }
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC,"createdTime");
        List<BizRoute> routeList = bizRouteRepository.findAll(spec,sort);
       return RespBean.ok("查询成功！",routeList);
    }

    public RespBean findAll(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token =  StringUtils.substringAfter(header, "Bearer ");
        String user = (String) oauthService.getCurrentUser(token).getObj();
        String chineseName = (String) oauthService.getChineseName(user).getObj();

        List<BizRoute> list = bizRouteRepository.findList(user);
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                BizRoute route = list.get(i);
                List<BizSignPoint> points = route.getBizSignPoints();
                if(points.size()>0){
                    for(int j =0; j<points.size();j++){
                        //获取报建文件列表
                        if(FieldUtils.isObjectNotEmpty(points.get(j).getFileType())) {
                            List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>) fileService.selectOneByPid(String.valueOf((Integer) points.get(j).getId()), (String) points.get(j).getFileType()).getObj();
                            points.get(j).setFileList(fileList);
                        }
                    }
                }
                Map waterOfficeMenu = (Map) findEnum(route.getWaterManagementOffice()).getObj();
                Map routeTypeMenu = (Map) findEnum(route.getRouteType()).getObj();
                route.setRouteTypeMenu(routeTypeMenu);
                route.setWaterOfficeMenu(waterOfficeMenu);
                route.setRouteCreator(user);
            }
        }
        return RespBean.ok("查询成功！",list);
    }

    public RespBean findExitAll(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token =  StringUtils.substringAfter(header, "Bearer ");
        String user = (String) oauthService.getCurrentUser(token).getObj();
        String chineseName = (String) oauthService.getChineseName(user).getObj();

        List<BizRoute> list = bizRouteRepository.findExitList(user);
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                BizRoute route = list.get(i);
                List<BizSignPoint> points = route.getBizSignPoints();
                if(points.size()>0){
                    for(int j =0; j<points.size();j++){
                        //获取报建文件列表
                        if(FieldUtils.isObjectNotEmpty(points.get(j).getFileType())) {
                            List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>) fileService.selectOneByPid(String.valueOf((Integer) points.get(j).getId()), (String) points.get(j).getFileType()).getObj();
                            points.get(j).setFileList(fileList);
                        }
                    }
                }
                Map waterOfficeMenu = (Map) findEnum(route.getWaterManagementOffice()).getObj();
                Map routeTypeMenu = (Map) findEnum(route.getRouteType()).getObj();
                route.setRouteTypeMenu(routeTypeMenu);
                route.setWaterOfficeMenu(waterOfficeMenu);
                route.setRouteCreator(user);
            }
        }
        return RespBean.ok("查询成功！",list);
    }

    public RespBean findSelection(String code){
        List<HashMap<String, Object>> selection = bizRouteRepository.findSelection(code);
        return RespBean.ok("查询成功！",selection);
    }

    public RespBean findDetail(Integer id){
        BizRoute br = bizRouteRepository.findDetail(id);
        List<BizSignPoint> pointList = (List<BizSignPoint>) signPointService.findList(br.getId()).getObj();
        if(br != null) {
            List<BizSignPoint> list = br.getBizSignPoints();
            //获取报建文件列表
            if(list.size()>0){
                for(int i =0; i<list.size(); i++){
                    //获取报建文件列表
                    if(FieldUtils.isObjectNotEmpty(list.get(i).getFileType())) {
                        List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>) fileService.selectOneByPid(String.valueOf((Integer) list.get(i).getId()), (String) list.get(i).getFileType()).getObj();
                        list.get(i).setFileList(fileList);
                    }
                }
            }

            Map waterOfficeMenu = (Map) findEnum(br.getWaterManagementOffice()).getObj();
            Map routeTypeMenu = (Map) findEnum(br.getRouteType()).getObj();
            br.setRouteTypeMenu(routeTypeMenu);
            br.setWaterOfficeMenu(waterOfficeMenu);
            br.setBizSignPoints(pointList);
            return RespBean.ok("查询成功！",br);
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
}
