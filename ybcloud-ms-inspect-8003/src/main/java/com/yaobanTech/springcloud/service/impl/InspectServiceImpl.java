package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaobanTech.springcloud.entity.Inspect;
import com.yaobanTech.springcloud.entity.utils.RedisGeneratorCode;
import com.yaobanTech.springcloud.entity.utils.RespBean;
import com.yaobanTech.springcloud.mapper.InspectMapper;
import com.yaobanTech.springcloud.service.IInspectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaobanTech.springcloud.service.feign.ActivitiService;
import com.yaobanTech.springcloud.service.feign.PlanService;
import com.yaobanTech.springcloud.service.feign.RouteService;
import com.yaobanTech.springcloud.utils.DateUtils;
import com.yaobanTech.springcloud.utils.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lijh
 * @since 2020-12-18
 */
@Service
public class InspectServiceImpl extends ServiceImpl<InspectMapper, Inspect> implements IInspectService {

    @Autowired
    private InspectMapper inspectMapper;

    @Autowired
    @Lazy
    private RedisGeneratorCode redisGeneratorCode;

    @Resource
    private RouteService routeService;

    @Resource
    private PlanService planService;

    @Resource
    private ActivitiService activitiService;

    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    SimpleDateFormat daydateFormat= new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public RespBean getPlanInspect(String type){
        //根据当前登陆人:班组员 bzy
        HashMap result = new HashMap();
        String date = dateFormat.format(new Date());
        //创建对象
        QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_type","计划任务");
        if("0".equals(type)){
            //获取未处理的列表
            List<Inspect>  wcllist= inspectMapper.selectList(queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) > convert(varchar(20),'"+date+"',20)"));
            return RespBean.ok("未处理列表").setObj(wcllist);
        }else if("1".equals(type)){
            //获取处理中的列表
            List<Inspect>  clzlist= inspectMapper.selectList(queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) <= convert(varchar(20),'"+date+"',20)"));
            return RespBean.ok("处理中列表").setObj(clzlist);
        }else if("2".equals(type)){
            //获取已处理的列表
            List<Inspect>  ycllist= inspectMapper.selectList(queryWrapper.eq("complete_rate","100"));
            return RespBean.ok("已处理列表").setObj(ycllist);
        }else if("3".equals(type)){
            //获取已延期的列表
            List<Inspect>  yyqlist= inspectMapper.selectList(queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),dead_time,20) < convert(varchar(20),'"+date+"',20)"));
            return RespBean.ok("已延期列表").setObj(yyqlist);
        }else{
            //获取未处理的列表
            List<Inspect>  wcllist= inspectMapper.selectList(queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) > convert(varchar(20),'"+date+"',20)"));
            //获取处理中的列表
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("task_type","计划任务");
            List<Inspect>  clzlist= inspectMapper.selectList(queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) <= convert(varchar(20),'"+date+"',20)"));
            //获取已处理的列表
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("task_type","计划任务");
            List<Inspect>  ycllist= inspectMapper.selectList(queryWrapper.eq("complete_rate","100"));
            //获取已延期的列表
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("task_type","计划任务");
            List<Inspect>  yyqlist= inspectMapper.selectList(queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),dead_time,20) < convert(varchar(20),'"+date+"',20)"));
            result.put("wcllist",wcllist);
            result.put("clzlist",clzlist);
            result.put("ycllist",ycllist);
            result.put("yyqlist",yyqlist);
            return RespBean.ok("wcllist：未处理列表，clzlist：处理中列表，ycllist:已处理列表，yyqlist:已延期列表").setObj(result);
        }
    }

    @Override
    public RespBean getTempInspect(String type) {
        //根据当前登陆人:班组员 bzy
        HashMap result = new HashMap();
        String date = dateFormat.format(new Date());
        //创建对象
        QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_type","临时任务");
        if("1".equals(type)){
            //获取处理中的列表
            List<Inspect>  clzlist= inspectMapper.selectList(queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) <= convert(varchar(20),'"+date+"',20)"));
            return RespBean.ok("处理中列表").setObj(clzlist);
        }else if("2".equals(type)){
            //获取已处理的列表
            List<Inspect>  ycllist= inspectMapper.selectList(queryWrapper.eq("complete_rate","100"));
            return RespBean.ok("已处理列表").setObj(ycllist);
        }else if("3".equals(type)){
            //获取已延期的列表
            List<Inspect>  yyqlist= inspectMapper.selectList(queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),dead_time,20) < convert(varchar(20),'"+date+"',20)"));
            return RespBean.ok("已延期列表").setObj(yyqlist);
        }else{
            //获取处理中的列表
            List<Inspect>  clzlist= inspectMapper.selectList(queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) <= convert(varchar(20),'"+date+"',20)"));
            //获取已处理的列表
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("task_type","临时任务");
            List<Inspect>  ycllist= inspectMapper.selectList(queryWrapper.eq("complete_rate","100"));
            //获取已延期的列表
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("task_type","临时任务");
            List<Inspect>  yyqlist= inspectMapper.selectList(queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),dead_time,20) < convert(varchar(20),'"+date+"',20)"));
            result.put("clzlist",clzlist);
            result.put("ycllist",ycllist);
            result.put("yyqlist",yyqlist);
            return RespBean.ok("clzlist：处理中列表，ycllist:已处理列表，yyqlist:已延期列表").setObj(result);
        }
    }

    @Override
    public RespBean getInspectDetailById(String inspect_task_id) throws IllegalAccessException {
        if(FieldUtils.isStringNotEmpty(inspect_task_id)){
            Map<String, Object> map1 = new HashMap<String, Object>();
            Map<String, Object> map2 = new HashMap<String, Object>();
            Map<String, Object> map3 = new HashMap<String, Object>();
            //创建对象
            QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
            Inspect inspect = inspectMapper.selectOne(queryWrapper.eq("inspect_task_id",inspect_task_id));
            if(FieldUtils.isObjectNotEmpty(inspect.getRouteId())){
                map2 = (Map<String, Object>) routeService.findDetail(inspect.getRouteId()).getObj();
            }
            if(FieldUtils.isObjectNotEmpty(inspect.getPlanId())){
                map3 = (Map<String, Object>) planService.findById(inspect.getPlanId()).getObj();
            }
            // 合并
            Map<String, Object> combineResultMap = new HashMap<String, Object>();
            map1 = FieldUtils.objectToMap(inspect);
            if(FieldUtils.isObjectNotEmpty(map1)){
                combineResultMap.putAll(map1);
            }
            if(FieldUtils.isObjectNotEmpty(map2)){
                combineResultMap.putAll(map2);
            }
            if(FieldUtils.isObjectNotEmpty(map3)){
                combineResultMap.putAll(map3);
            }
            return RespBean.ok("").setObj(combineResultMap);
        }else{
            return RespBean.error("缺少任务编号");
        }
    }

    @Override
    @Transactional
    public RespBean updateInspectDetailById(Map<String, Object> params) {
        if(FieldUtils.isObjectNotEmpty(params)){
            Inspect inspect = JSONObject.parseObject(JSONObject.toJSONString(params.get("form")), Inspect.class);
            //创建对象
            if(FieldUtils.isStringNotEmpty(inspect.getInspectTaskId())){
                inspectMapper.updateById(inspect);
                return RespBean.ok("修改成功！").setObj(inspect);
            }else{
                return RespBean.error("缺少任务编号！");
            }
        }else{
            return RespBean.error("map参数为空!");
        }
    }

    @Override
    @Transactional
    public RespBean addTempTask(Integer routeId,String  routeName ,String inspector,String beginTime,String endTime) {
        Inspect inspect = new Inspect();
        if(FieldUtils.isObjectNotEmpty(routeId)){
            inspect.setRouteId(routeId);
            String inspect_task_id = "";
            inspect_task_id = redisGeneratorCode.createGenerateCode("临时任务","LS",true,4);
            inspect.setInspectTaskId(inspect_task_id);
            inspect.setTaskType("临时任务");
            inspect.setBeginTime(beginTime);
            inspect.setDeadTime(endTime);
            int id = inspectMapper.insert(inspect);
            return RespBean.ok("保存成功！"+ id );
        }else{
            return RespBean.error("未读取到对象数据！");
        }
    }

    @Override
    @Transactional
    public RespBean addPlanTask(Integer routeId,String  routeName ,Integer planId,String planName) throws ParseException {
        if(FieldUtils.isObjectNotEmpty(planId) && FieldUtils.isObjectNotEmpty(routeId) ){
            RespBean re = planService.findById(planId);

            Map map = (Map) re.getObj();
            Date start = (Date) map.get("begin_time");
            Date end = (Date) map.get("end_time");
            String cycleStr = (String) map.get("cycle");;
            int days = DateUtils.daysBetween(start,end);
            if(FieldUtils.isStringNotEmpty(cycleStr) && FieldUtils.isObjectNotEmpty(start) && FieldUtils.isObjectNotEmpty(end)){
                Integer cycle = Integer.valueOf(cycleStr.substring(0,1));
                int nums = (int) Math.floor(days/cycle);
                if(nums>1){
                    for(int i=0;i<nums;i++){
                        Inspect inspect = new Inspect();
                        String inspect_task_id = "";
                        inspect_task_id = redisGeneratorCode.createGenerateCode("临时任务","LS",true,4);
                        inspect.setInspectTaskId(inspect_task_id);
                        inspect.setBeginTime(dateFormat.format(DateUtils.daysAdd(start,3*i)));
                        inspect.setDeadTime(dateFormat.format(DateUtils.daysAdd(end,3*(i+1))));
                        inspect.setPlanId(planId);
                        inspect.setRouteId(routeId);
                        inspectMapper.insert(inspect);
                    }
                }
                return RespBean.ok("生成任务完成！");
            }else{
                return RespBean.error("计划开始时间，计划结束时间或计划周期不完整，创建不了任务！");
            }

        }else{
            return RespBean.error("参数为空！");
        }
    }

    @Override
    public RespBean getCheckInPoints(Integer routeId) {
        if(FieldUtils.isObjectNotEmpty(routeId)){
            /*RespBean respBean = planService.findrouteId(id);
            Integer routeId = null;
            if(FieldUtils.isObjectNotEmpty(respBean.getObj())){
                routeId = (Integer) respBean.getObj();
            }*/
            //路线id取签到表列表
            return routeService.findpoints(routeId);
        }else{
            return RespBean.error("缺少路线编号routeId");
        }
    }

    @Override
    @Transactional
    public RespBean getPointDetail(Integer id) {
        if(FieldUtils.isObjectNotEmpty(id)){
            return routeService.findsignPoint(id);
        }else{
            return RespBean.error("缺少签到点编号Id");
        }
    }

    @Override
    public RespBean updatePoint(Map<String, Object> params) {
        if(FieldUtils.isObjectNotEmpty(params)){
            return routeService.updatePoint(params);
        }else{
            return RespBean.error("参数为空！");
        }
    }

    @Override
    public RespBean getAllInspectByPlanId(Integer planId) {
        if(FieldUtils.isObjectNotEmpty(planId)){
            QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("task_type", "计划任务");
            queryWrapper.eq("plan_id", planId);
            //获取未处理的列表
            List<Inspect> result = inspectMapper.selectList(queryWrapper);
            return RespBean.ok("根据计划编号查出的计划任务").setObj(result);
        }else{
            return RespBean.error("缺少计划编号："+planId);
        }
    }

    @Override
    @Transactional
    public RespBean autoCreate(Map<String, Object> params) throws ParseException {
        if(FieldUtils.isObjectNotEmpty(params)){
            Integer planId = (Integer) params.get("planId");
            Integer routeId = (Integer) params.get("routeId");
            int period = Integer.valueOf((String) params.get("period"));
            Date start = daydateFormat.parse((String) params.get("startTime"));
            Date end = daydateFormat.parse((String) params.get("endTime"));
            int days = DateUtils.daysBetween(start,end);
            int nums = Integer.valueOf((int) Math.floor(days/period));
            if(nums>1){
                for(int i=0;i<nums;i++){
                    Inspect inspect = new Inspect();
                    String inspect_task_id = "";
                    inspect_task_id = redisGeneratorCode.createGenerateCode("临时任务","LS",true,4);
                    inspect.setInspectTaskId(inspect_task_id);
                    inspect.setBeginTime(daydateFormat.format(DateUtils.daysAdd(start,period*i)));
                    inspect.setDeadTime(daydateFormat.format(DateUtils.daysAdd(start,period*(i+1))));
                    inspect.setPlanId(planId);
                    inspect.setRouteId(routeId);
                    inspectMapper.insert(inspect);
                    //启动流程
                    Map<String,Object> map = new HashMap<>();
                    Map<String, Object> variable = new HashMap<String, Object>();
                    variable.put("ROLE_BZY","bzy");
                    variable.put("ROLE_BZZ","bzz" );
                    map.put("key","inspect");
                    map.put("businessKey",inspect_task_id);
                    map.put("variable",variable);
                    activitiService.startProcess(map);
                }
            }
            return RespBean.ok("生成任务完成！");
        }else{
            return RespBean.error("参数为空！");
        }
    }

    @Override
    @Transactional
    public RespBean send(Map<String, Object> params) {
        if(FieldUtils.isObjectNotEmpty(params) && FieldUtils.isObjectNotEmpty(params.get("form"))) {
            Map map = new HashMap();
            map = (Map) params.get("form");
            String inspector = null;
            if (FieldUtils.isObjectNotEmpty(map.get("inspector"))) {
                inspector = (String) map.get("inspector");
                if (FieldUtils.isObjectNotEmpty(map.get("inspect_task_id"))) {
                    List<Inspect> list = new ArrayList<>();
                    List<HashMap<String,Object>> tasklist= (ArrayList) map.get("inspect_task_id");
                    if(tasklist.size()>0){
                        for(int i=0;i<tasklist.size();i++){
                            list.add(JSON.parseObject(JSON.toJSONString(tasklist.get(i)), new TypeReference<Inspect>() { }));
                        }
                    }
                    if(list.size()>0){
                        for(int i =0;i<list.size();i++){
                            Map<String,Object> taskMap = new HashMap<>();
                            Map<String, Object> variable = new HashMap<String, Object>();
                            variable.put("ROLE_BZY",inspector);
                            variable.put("ROLE_BZZ","bzz" );
                            taskMap.put("key","inspect");
                            taskMap.put("businessKey",list.get(i).getInspectTaskId());
                            taskMap.put("variable",variable);
                            activitiService.startProcess(taskMap);
                        }
                    }
                    return RespBean.ok("派发给"+inspector+list.size()+"个任务！，派发成功！").setObj("");
                }else{
                    return RespBean.error("缺少参数！");
                }
            } else {
                return RespBean.error("缺少参数！");
            }
        }else{
            return RespBean.error("缺少参数！");
        }
    }
}
