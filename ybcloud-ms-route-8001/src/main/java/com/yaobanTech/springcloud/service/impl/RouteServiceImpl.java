package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.domain.BizRoute;
import com.yaobanTech.springcloud.domain.BizSignPoint;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.domain.Selection;
import com.yaobanTech.springcloud.domain.enumDef.EnumMenu;
import com.yaobanTech.springcloud.repository.BizRouteRepository;
import com.yaobanTech.springcloud.repository.BizSignPointRepository;
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
    private OauthService oauthService;


    public RespBean saveRoute(HashMap<String,Object> param) {
        BizRoute bizRoute = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizRoute.class);
        if(bizRoute != null && !bizRoute.getBizSignPoints().isEmpty()) {
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
                bizRoute.setEnabled(1);
                BizRoute route = bizRouteRepository.save(bizRoute);

            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("保存失败！");
            }
        }else{
            return RespBean.error("数据为空！");
        }
        return RespBean.ok("保存成功！",bizRoute);
    }

    public RespBean updateRoute(HashMap<String,Object> param) {
        BizRoute bizRoute = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizRoute.class);
        if(bizRoute.getId() != null && !bizRoute.getBizSignPoints().isEmpty()) {
            try {
//                List<BizSignPoint> pointList = bizRoute.getBizSignPoints();
//                for (int i = 0; i <pointList.size() ; i++) {
//                    Integer id = pointList.get(i).getId();
//                    if(id == null || "".equals(id)){
//                        return RespBean.error("签到点ID为空,修改失败！");
//                    }
//                }
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
                i = bizRouteRepository.deleteRoute(id);
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

    public RespBean findAll(){
    //    String user = (String) oauthService.getCurrentUser(request).getObj();
        List<BizRoute> list = bizRouteRepository.findList();
        return RespBean.ok("查询成功！",list);
    }

    public RespBean findSelection(){
        List<Selection> selection = bizRouteRepository.findSelection();
        return RespBean.ok("查询成功！",selection);
    }

    public RespBean findDetail(Integer id){
        BizRoute br = bizRouteRepository.findDetail(id);
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
}
