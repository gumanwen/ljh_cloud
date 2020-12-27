package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.domain.BizRoute;
import com.yaobanTech.springcloud.domain.BizSignPoint;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.domain.enumDef.EnumMenu;
import com.yaobanTech.springcloud.repository.BizRouteRepository;
import com.yaobanTech.springcloud.repository.BizSignPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class RouteServiceImpl {
    @Autowired
    @Lazy
    private BizRouteRepository bizRouteRepository;

    @Autowired
    @Lazy
    private BizSignPointRepository bizSignPointRepository;


    public RespBean saveRoute(HashMap<String,Object> param) {
        BizRoute bizRoute = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizRoute.class);
        if(bizRoute != null && !bizRoute.getBizSignPoints().isEmpty()) {
            try {
                List<BizSignPoint> pointList = bizRoute.getBizSignPoints();
                for (int i = 0; i <pointList.size() ; i++) {
                    pointList.get(i).setRouteId(bizRoute.getId());
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

    public RespBean updateRoute(Integer id,BizRoute bizRoute) {
        if(id != null) {
            try {
                BizRoute route = bizRouteRepository.save(bizRoute);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("修改失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("修改成功！");
    }

    public RespBean deleteRoute(Integer id) {
        if(id != null) {
            try {
                BizRoute route = bizRouteRepository.deleteRoute(id);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("删除失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("删除成功！");
    }

    public RespBean findCondition(String waterManagementOffice,String pointInspectionType,
                                  String planInspectionMileage,String createdTime,
                                  String routeName,String routeCreator,
                                  String routeType) {
        Specification<BizRoute> spec = new Specification<BizRoute>() {
            @Override
            public Predicate toPredicate(Root<BizRoute> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
               //从root取属性
                Path<Object> path1 = root.get("waterManagementOffice");
                Path<Object> path2 = root.get("pointInspectionType");
                Path<Object> path3 = root.get("planInspectionMileage");
                Path<Object> path4 = root.get("createdTime");
                Path<Object> path5 = root.get("routeName");
                Path<Object> path6 = root.get("routeCreator");
                Path<Object> path7 = root.get("routeType");
                //cb构造查询条件
                Predicate p1 = cb.equal(path1, waterManagementOffice);
                Predicate p2 = cb.equal(path2, pointInspectionType);
                Predicate p3 = cb.equal(path3, planInspectionMileage);
                Predicate p4 = cb.equal(path4, createdTime);
                Predicate p5 = cb.equal(path5, routeName);
                Predicate p6 = cb.equal(path6, routeCreator);
                Predicate p7 = cb.equal(path7, routeType);
                //cb连接查询条件
                Predicate predicate = cb.and(p1, p2, p3, p4, p5, p5, p7);
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC,"createdTime");
        List<BizRoute> routeList = bizRouteRepository.findAll(spec,sort);
       return RespBean.ok("查询成功！",routeList);
    }

    public RespBean findAll(){
        List<BizRoute> list = bizRouteRepository.findAll();
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
}
