package com.yaobanTech.springcloud.service;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.domain.BizPlan;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.domain.enumDef.EnumMenu;
import com.yaobanTech.springcloud.repository.BizPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class PlanService {
    @Autowired
    @Lazy
    private BizPlanRepository bizPlanRepository;

    @Autowired
    @Lazy
    private InspectService inspectService;

    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public RespBean savePlan(HashMap<String,Object> param) {
        BizPlan bizPlan = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizPlan.class);
        if(bizPlan != null) {
            try {
                bizPlan.setEnabled(1);
                BizPlan plan = bizPlanRepository.save(bizPlan);
                String code = bizPlan.getPlanPorid();
                EnumMenu key = EnumMenu.getEnumByKey(code);
                String desc = key.getDesc();
                String period = desc.substring(0, 1);
                HashMap<String,Object> map = new HashMap<>();
                map.put("routeId",bizPlan.getRouteId());
                map.put("planId",bizPlan.getId());
                map.put("startTime",dateFormat.format(bizPlan.getStartTime()));
                map.put("endTime",dateFormat.format(bizPlan.getEndTime()));
                map.put("period",period);
                inspectService.sendInspectInfo(map);
            } catch (Exception
                    e) {
                e.printStackTrace();
                return RespBean.error("保存失败！");
            }
        }else{
            return RespBean.error("数据为空！");
        }
        return RespBean.ok("保存成功！");
    }

    public RespBean updatePlan(HashMap<String,Object> param) {
        BizPlan bizPlan = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizPlan.class);
        if(bizPlan.getId() != null) {
            try {
                BizPlan plan = bizPlanRepository.save(bizPlan);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("修改失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("修改成功！");
    }

    public RespBean deletePlan(Integer id) {
        if(id != null) {
            try {
                bizPlanRepository.deletePlan(id);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("删除失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("删除成功！");
    }

    public RespBean findPlanId(Integer id) {
        Integer routeId = null;
        if(id != null) {
            try {
                 routeId = bizPlanRepository.findRouteId(id);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("查询成功！",routeId);
    }

    public RespBean findAll(){
        List<BizPlan> list = bizPlanRepository.findList();
        return RespBean.ok("查询成功！",list);
    }

    public RespBean findSelection(){
        List<HashMap<String,Object>> selection = bizPlanRepository.findSelection();
        return RespBean.ok("查询成功！",selection);
    }

    public RespBean findById(Integer id){
        BizPlan bp = bizPlanRepository.findDetail(id);
        return RespBean.ok("查询成功！",bp);
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
