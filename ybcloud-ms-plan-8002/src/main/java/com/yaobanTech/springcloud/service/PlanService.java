package com.yaobanTech.springcloud.service;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.ToolUtils.UrlUtils;
import com.yaobanTech.springcloud.domain.BizPlan;
import com.yaobanTech.springcloud.domain.LoginUser;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.domain.enumDef.EnumMenu;
import com.yaobanTech.springcloud.repository.BizPlanMapper;
import com.yaobanTech.springcloud.repository.BizPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PlanService {

    @Autowired
    @Lazy
    private BizPlanRepository bizPlanRepository;

    @Autowired
    @Lazy
    private BizPlanMapper bizPlanMapper;

    @Autowired
    @Lazy
    private RouteService routeService;

    @Autowired
    @Lazy
    private InspectService inspectService;

    @Autowired
    @Lazy
    private OauthService oauthService;

    @Autowired
    @Lazy
    private UrlUtils urlUtils;
    @Autowired
    @Lazy
    private PlanMapper planMapper;

    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public RespBean savePlan(HashMap<String,Object> param, HttpServletRequest request) {
        BizPlan bizPlan = null;
        Boolean flag = false;
        if(param != null){
            bizPlan = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizPlan.class);
            List<String> list = bizPlanRepository.findPlanName(bizPlan.getRouteId());
            if(!list.isEmpty()){
                for (int i = 0; i < list.size(); i++) {
                    String planName = list.get(i);
                    if(planName.equals(bizPlan.getPlanName())){
                        flag = true;
                    }
                }
            }
            if(flag == false){
                if(bizPlan != null) {
                    try {
                        bizPlan.setEnabled(1);
                        bizPlan.setPlanStatus("11");
                        bizPlan.setPlanCreatedTime(new Date());
                        LoginUser u = urlUtils.getAll(request);
                        String user = u.getLoginname();
                        bizPlan.setPlanCreatedBy(user);
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
            }else{
                return RespBean.error("该路线下已有重名计划,请修改计划名重试!");
            }
        }
        return RespBean.ok("保存成功！");
    }

    public RespBean updatePlan(HashMap<String,Object> param) {
        BizPlan bizPlan = null;
        Boolean flag = false;
        if(param != null){
            bizPlan = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizPlan.class);
            List<String> list = bizPlanRepository.findPlanName(bizPlan.getRouteId());
            if(!list.isEmpty()){
                for (int i = 0; i < list.size(); i++) {
                    String planName = list.get(i);
                    if(planName.equals(bizPlan.getPlanName())){
                        flag = true;
                    }
                }
            }
        }
        if(flag == false){
            if(bizPlan.getId() != null) {
                try {
                    bizPlanMapper.update(bizPlan);
                } catch (Exception e) {
                    e.printStackTrace();
                    return RespBean.error("修改失败！");
                }
            }else{
                return RespBean.error("id为空！");
            }
        }
        return RespBean.ok("修改成功！");
    }

    public RespBean deletePlan(Integer id) {
        if(id != null) {
            try {
                BizPlan detail = bizPlanRepository.findDetail(id);
                Date date = new Date();
                Date startTime = detail.getStartTime();
               Boolean flag = startTime.before(date);
                if(!flag) {
                    bizPlanRepository.deletePlan(id);
                }else{
                    return RespBean.error("删除失败！该计划已开始,无法删除！");
                }
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

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespBean findAll(HttpServletRequest request) throws UnsupportedEncodingException {
        LoginUser u = urlUtils.getAll(request);
        String user = u.getLoginname();
        List<BizPlan> list = planMapper.findAll(user);
        String chineseName = (String)oauthService.getChineseName(u.getLoginname()).getObj();
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                BizPlan plan = list.get(i);
                Map map = (Map) findEnum(plan.getPlanType()).getObj();
                Map ps = (Map) findEnum(plan.getPlanStatus()).getObj();
                Map pp = (Map) findEnum(plan.getPlanPorid()).getObj();
                plan.setPlanTypeMenu(map);
                plan.setPlanStatusMenu(ps);
                plan.setPlanPoridMenu(pp);
                plan.setPlanCreatedByCN(chineseName);
                RespBean respBean = routeService.findDetail(plan.getRouteId());
                Object o = respBean.getObj();
                if(respBean.getStatus() == 500){
                    throw new RuntimeException("Feign调用路线服务失败！");
                }
                plan.setRouteObj(o);
            }
        }
        return RespBean.ok("查询成功！",list);
    }

    public RespBean findSelection(Integer routeId){
        List<HashMap<String,Object>> selection = bizPlanRepository.findSelection(routeId);
        return RespBean.ok("查询成功！",selection);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespBean findById(Integer id) throws UnsupportedEncodingException {
        BizPlan bp = bizPlanRepository.findDetail(id);
        if(bp != null) {
            String user = bp.getPlanCreatedBy();
            String chineseName = (String)oauthService.getChineseName(user).getObj();
            Map map = (Map) findEnum(bp.getPlanType()).getObj();
            Map ps = (Map) findEnum(bp.getPlanStatus()).getObj();
            Map pp = (Map) findEnum(bp.getPlanPorid()).getObj();
            bp.setPlanTypeMenu(map);
            bp.setPlanStatusMenu(ps);
            bp.setPlanPoridMenu(pp);
            bp.setPlanCreatedByCN(chineseName);
            RespBean respBean = routeService.findDetail(bp.getRouteId());
            Object o = respBean.getObj();
            if(respBean.getStatus() == 500){
                throw new RuntimeException("Feign调用路线服务失败！");
            }
            bp.setRouteObj(o);
            return RespBean.ok("查询成功！", bp);
        }
        return RespBean.ok("查询成功！", bp);
    }

    @Transactional
    public RespBean findByRouteId(Integer routeId){
        List<BizPlan> list = bizPlanRepository.findByRouteId(routeId);
        return RespBean.ok("查询成功！", list);
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

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespBean findCondition(String routeName, String waterManagementOffice, String planPorid, String planType,
                                  String startTimeOfPCT, String endTimeOfPCT,
                                  String startTimeOfPST, String endTimeOfPST,
                                  String startTimeOfPET, String endTimeOfPET, HttpServletRequest request) throws UnsupportedEncodingException {
        //获取当前用户
        LoginUser u = urlUtils.getAll(request);
        String user = u.getLoginname();
        String chineseName = (String)oauthService.getChineseName(user).getObj();
        if("null".equals(routeName)){
            routeName = null;
        }
        if("null".equals(waterManagementOffice)){
            waterManagementOffice = null;
        }
        if("null".equals(planPorid)){
            planPorid = null;
        }
        if("null".equals(planType)){
            planType = null;
        }
        if("null".equals(startTimeOfPCT)){
            startTimeOfPCT = null;
        }
        if("null".equals(endTimeOfPCT)){
            endTimeOfPCT = null;
        }
        if("null".equals(startTimeOfPST)){
            startTimeOfPST = null;
        }
        if("null".equals(endTimeOfPST)){
            endTimeOfPST = null;
        }
        if("null".equals(startTimeOfPET)){
            startTimeOfPET = null;
        }
        if("null".equals(endTimeOfPET)){
            endTimeOfPET = null;
        }

        //查询计划列表
        List<BizPlan> list = bizPlanRepository.findCondition(routeName,waterManagementOffice,planPorid,planType,startTimeOfPCT,endTimeOfPCT,startTimeOfPST,endTimeOfPST,startTimeOfPET,endTimeOfPET);
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                BizPlan plan = list.get(i);
                Map map = (Map) findEnum(plan.getPlanType()).getObj();
                Map ps = (Map) findEnum(plan.getPlanStatus()).getObj();
                Map pp = (Map) findEnum(plan.getPlanPorid()).getObj();
                plan.setPlanTypeMenu(map);
                plan.setPlanStatusMenu(ps);
                plan.setPlanPoridMenu(pp);
                plan.setPlanCreatedByCN(chineseName);
                RespBean respBean = routeService.findDetail(plan.getRouteId());
                Object o = respBean.getObj();
                if(respBean.getStatus() == 500){
                    throw new RuntimeException("Feign调用路线服务失败！");
                }
                plan.setRouteObj(o);
            }
        }
        return RespBean.ok("查询成功！", list);
    }

}
