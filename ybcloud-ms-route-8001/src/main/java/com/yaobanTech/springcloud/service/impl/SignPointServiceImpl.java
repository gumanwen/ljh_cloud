package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.domain.BizSignPoint;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.repository.BizSignPointMapper;
import com.yaobanTech.springcloud.repository.BizSignPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class SignPointServiceImpl {
    @Autowired
    @Lazy
    private BizSignPointRepository signPointRepository;

    @Autowired
    @Lazy
    private BizSignPointMapper bizSignPointMapper;

    public RespBean saveSignPoint(HashMap<String,Object> param) {
        BizSignPoint bizSignPoint = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizSignPoint.class);
        if(bizSignPoint != null) {
            try {
                bizSignPoint.setSignPointStatus("未签到");
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

    public RespBean updateSignPoint(HashMap<String,Object> param) {
        if(!param.isEmpty() && param.get("form") != null) {
            BizSignPoint bizSignPoint = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizSignPoint.class);
            if(bizSignPoint.getId() != null){
            try {
                bizSignPoint.setModifyTime(new Date());
                bizSignPointMapper.update(bizSignPoint);
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

    public RespBean findSignedList(Integer routeId) {
        List<BizSignPoint> list = null;
        if(routeId != null) {
            try {
                list = signPointRepository.findSignedList(routeId);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("查询成功！",list);
    }

//    public RespBean findCondition(String waterManagementOffice,String fixedPointInspectionType,
//                                  String planInspectionMileage,String createdTime,
//                                  String SignPointName,String SignPointCreator,
//                                  String SignPointType) {
//        Specification<BizSignPoint> spec = new Specification<BizSignPoint>() {
//            @Override
//            public Predicate toPredicate(Root<BizSignPoint> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//               //从root取属性
//                Path<Object> path1 = root.get("waterManagementOffice");
//                Path<Object> path2 = root.get("fixedPointInspectionType");
//                Path<Object> path3 = root.get("planInspectionMileage");
//                Path<Object> path4 = root.get("createdTime");
//                Path<Object> path5 = root.get("SignPointName");
//                Path<Object> path6 = root.get("SignPointCreator");
//                Path<Object> path7 = root.get("SignPointType");
//                //cb构造查询条件
//                Predicate p1 = cb.equal(path1, waterManagementOffice);
//                Predicate p2 = cb.equal(path2, waterManagementOffice);
//                Predicate p3 = cb.equal(path3, waterManagementOffice);
//                Predicate p4 = cb.equal(path4, waterManagementOffice);
//                Predicate p5 = cb.equal(path5, waterManagementOffice);
//                Predicate p6 = cb.equal(path6, waterManagementOffice);
//                Predicate p7 = cb.equal(path7, waterManagementOffice);
//                //cb连接查询条件
//                Predicate predicate = cb.and(p1, p2, p3, p4, p5, p5, p7);
//                return predicate;
//            }
//        };
//        Sort sort = Sort.by(Sort.Direction.DESC,"createdTime");
//        List<BizSignPoint> SignPointList = signPointRepository.findAll(spec,sort);
//       return RespBean.ok("查询成功！",SignPointList);
//    }
}
