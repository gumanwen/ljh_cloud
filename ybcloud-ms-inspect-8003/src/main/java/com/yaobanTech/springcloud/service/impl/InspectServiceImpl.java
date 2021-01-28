package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.client.utils.JSONUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaobanTech.springcloud.entity.Inspect;
import com.yaobanTech.springcloud.entity.LoginUser;
import com.yaobanTech.springcloud.entity.utils.RedisGeneratorCode;
import com.yaobanTech.springcloud.entity.utils.RespBean;
import com.yaobanTech.springcloud.mapper.InspectMapper;
import com.yaobanTech.springcloud.service.IInspectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaobanTech.springcloud.service.feign.*;
import com.yaobanTech.springcloud.utils.DateUtils;
import com.yaobanTech.springcloud.utils.FieldUtils;
import io.jsonwebtoken.Jwts;
import io.micrometer.core.instrument.util.JsonUtils;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
    private AuthService authService;

    @Resource
    private ActivitiService activitiService;

    @Resource
    private FileService fileService;

    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    SimpleDateFormat daydateFormat= new SimpleDateFormat("yyyy-MM-dd");

    private static final Logger logger = LoggerFactory.getLogger(InspectServiceImpl.class);

    @Value("${server.ip}")
    private String ip;

    @Value("${server.port}")
    private String port;
    @Override
    public RespBean getPlanInspect(String type,long pageNo,long pageSize,HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException {

        logger.info("清远用户token="+request.getHeader("TW-AUTH-HEADER"));
        String tokenT = request.getHeader("TW-AUTH-HEADER");
        LoginUser loginUser = null;
        if(FieldUtils.isStringNotEmpty(tokenT)){
            tokenT = URLDecoder.decode(tokenT,"UTF-8");
            loginUser = JSON.parseObject(tokenT,LoginUser.class);
            logger.info("清远用户信息="+loginUser.toString());
        }
        String header = request.getHeader("Authorization");
        String token =  StringUtils.substringAfter(header, "Bearer ");
        RespBean feignRespBean = authService.getCurrentUserAndRole(token);
        HashMap<Object,Object> feignMap = (HashMap<Object, Object>) feignRespBean.getObj();
        String username = (String) feignMap.get("username");
        String roles = (String) feignMap.get("roles");
        //根据当前登陆人:班组员 bzy
        String date = dateFormat.format(new Date());
        String status =null;
        //创建对象
        QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
        RespBean respBean = new RespBean();
        queryWrapper.eq("task_type","计划任务");
        List<Inspect> list = new ArrayList<>();
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> result = new HashMap<>();
        if(roles.indexOf("BZZ")== -1){//班组长
            queryWrapper.eq("inspect_person",username);
        }
        IPage<Inspect> page = new Page<Inspect>(pageNo,pageSize);
        page =inspectMapper.selectPage(page,queryWrapper);
        if("0".equals(type)){
            //获取未处理的列表
            queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) > convert(varchar(20),'"+date+"',20)");
            list= inspectMapper.selectPage(page,queryWrapper).getRecords();
            status = "未处理";
            respBean.setMsg("未处理列表");
        }else if("1".equals(type)){
            //获取处理中的列表
            queryWrapper.lt("complete_rate","100").apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) <= convert(varchar(20),'"+date+"',20)");
            list= inspectMapper.selectPage(page,queryWrapper).getRecords();
            status = "处理中";
            respBean.setMsg("处理中列表");
        }else if("2".equals(type)){
            //获取已处理的列表
            queryWrapper.eq("complete_rate","100");
            list= inspectMapper.selectPage(page,queryWrapper).getRecords();
            status = "已完成";
            respBean.setMsg("已完成列表");
        }else if("3".equals(type)){
            //获取已延期的列表
            queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),dead_time,20) < convert(varchar(20),'"+date+"',20)");
            list= inspectMapper.selectPage(page,queryWrapper).getRecords();
            status = "已延期";
            respBean.setMsg("已延期列表");
        }else{
            list= inspectMapper.selectPage(page,queryWrapper).getRecords();
            respBean.setMsg("全部列表");
        }
        if(list.size()>0){
            for(int i = 0;i<list.size();i++){
                Map<String, Object> map1 = new HashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                Map<String, Object> map3 = new HashMap<String, Object>();
                Inspect inspect = list.get(i);
                inspect.setStatus(status);
                //创建对象
                if(FieldUtils.isObjectNotEmpty(inspect.getRouteId())){
                    map2 = (Map<String, Object>) routeService.findDetail(inspect.getRouteId()).getObj();
                }
                if(FieldUtils.isObjectNotEmpty(inspect.getPlanId())){
                    map3 = (Map<String, Object>) planService.findById(inspect.getPlanId()).getObj();
                }
                if(FieldUtils.isObjectNotEmpty(map2)) {
                    // 合并
                    Map<String, Object> combineResultMap = new HashMap<String, Object>();
                    map1 = FieldUtils.objectToMap(inspect);
                    map1.put("routeName", FieldUtils.ifObjectEmpty(map2.get("routeName")));
                    map1.put("routeType", FieldUtils.ifObjectEmpty(map2.get("routeType")));
                    map1.put("waterManagementOffice", FieldUtils.ifObjectEmpty(map2.get("waterManagementOffice")));
                    map1.put("planInspectionMileage", FieldUtils.ifObjectEmpty(map2.get("planInspectionMileage")));
                    map1.put("pointInspectionType", FieldUtils.ifObjectEmpty(map2.get("pointInspectionType")));
                    map1.put("hiddenDangerAmount", FieldUtils.ifObjectEmpty(map2.get("hiddenDangerAmount")));
                    map1.put("overReason", FieldUtils.ifObjectEmpty(map2.get("overReason")));
                    map1.put("signIn", FieldUtils.ifObjectEmpty(map2.get("signIn")));
                    map1.put("waterOfficeMenu", FieldUtils.ifObjectEmpty(map2.get("waterOfficeMenu")));
                    map1.put("routeTypeMenu", FieldUtils.ifObjectEmpty(map2.get("routeTypeMenu")));
                    map1.put("pointInspectionTypeMenu", FieldUtils.ifObjectEmpty(map2.get("pointInspectionTypeMenu")));
                }else{
                    // 合并
                    Map<String, Object> combineResultMap = new HashMap<String, Object>();
                    map1 = FieldUtils.objectToMap(inspect);
                    map1.put("routeName","");
                    map1.put("routeType","");
                    map1.put("waterManagementOffice", "");
                    map1.put("planInspectionMileage", "");
                    map1.put("pointInspectionType", "");
                    map1.put("hiddenDangerAmount", "");
                    map1.put("overReason", "");
                    map1.put("signIn", "");
                    map1.put("waterOfficeMenu", "");
                    map1.put("routeTypeMenu", "");
                    map1.put("pointInspectionTypeMenu", "");
                }
                if(FieldUtils.isObjectNotEmpty(map3)){
                    Map<String, Object> combineResultMap = new HashMap<String, Object>();
                    map1.put("planName", FieldUtils.ifObjectEmpty(map3.get("planName")));
                    map1.put("planType", FieldUtils.ifObjectEmpty(map3.get("planType")));
                    map1.put("planPorid", FieldUtils.ifObjectEmpty(map3.get("planPorid")));
                    map1.put("planTypeMenu", FieldUtils.ifObjectEmpty(map3.get("planTypeMenu")));
                    map1.put("planPoridMenu",FieldUtils.ifObjectEmpty(map3.get("planPoridMenu")));
                }else{
                    map1.put("planName", "");
                    map1.put("planType", "");
                    map1.put("planPorid", "");
                    map1.put("planTypeMenu","");
                    map1.put("planPoridMenu","");
                }
                resultList.add(map1);
            }
        }
        result.put("total",page.getTotal());
        result.put("list",resultList);
        return RespBean.ok("").setObj(result);
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
            return RespBean.ok("").setObj(result);
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
            if(FieldUtils.isObjectNotEmpty(inspect)){
                if(FieldUtils.isObjectNotEmpty(inspect.getRouteId())){
                    map2 = (Map<String, Object>) routeService.findDetail(inspect.getRouteId()).getObj();
                }
                if(FieldUtils.isObjectNotEmpty(inspect.getPlanId())){
                    map3 = (Map<String, Object>) planService.findById(inspect.getPlanId()).getObj();
                }
                // 合并
                Map<String, Object> combineResultMap = new HashMap<String, Object>();
                map1 = FieldUtils.objectToMap(inspect);
                if(FieldUtils.isObjectNotEmpty(map2)){
                    combineResultMap.putAll(map2);
                }
                if(FieldUtils.isObjectNotEmpty(map3)){
                    combineResultMap.putAll(map3);
                }
                if(FieldUtils.isObjectNotEmpty(map1)){
                    combineResultMap.putAll(map1);
                }
                return RespBean.ok("").setObj(combineResultMap);
            }
            return RespBean.ok("数据信息有误！").setObj("");
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
    public RespBean addTempTask(Integer routeId,String routeName ,String inspector,String beginTime,String endTime) throws ParseException {
        Inspect inspect = new Inspect();
        if(FieldUtils.isObjectNotEmpty(routeId)&&FieldUtils.isStringNotEmpty(beginTime)&&FieldUtils.isStringNotEmpty(endTime)){
            Date start =  new Date(Long.parseLong(beginTime));
            Date end =  new Date(Long.parseLong(endTime));
            inspect.setRouteId(routeId);
            String inspect_task_id = "";
            inspect_task_id = redisGeneratorCode.createGenerateCode("临时任务","LS",true,4);
            inspect.setInspectTaskId(inspect_task_id);
            inspect.setTaskType("临时任务");
            inspect.setBeginTime(daydateFormat.format(start));
            inspect.setDeadTime(daydateFormat.format(end));
            inspect.setCompleteRate("0");
            inspect.setStatus("处理中");
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
            List<String> list = new ArrayList<>();
            Map map = (Map) re.getObj();
            Map enumMap = new HashMap();
            String cycleStr = null;
            if(FieldUtils.isObjectNotEmpty(map.get("planPorid")) && FieldUtils.isObjectNotEmpty(map.get("startTime")) && FieldUtils.isObjectNotEmpty(map.get("endTime"))){
                Date start =  daydateFormat.parse((String) map.get("startTime"));
                Date end =  daydateFormat.parse((String) map.get("endTime"));
                RespBean respBean = routeService.findEnum((String) map.get("planPorid"));
                if(FieldUtils.isObjectNotEmpty(respBean)){
                    enumMap = (Map)respBean.getObj();
                    if(FieldUtils.isObjectNotEmpty(enumMap.get("desc"))){
                        cycleStr = (String) enumMap.get("desc");
                    }
                }
                int days = DateUtils.daysBetween(start,end);
                Integer cycle = Integer.valueOf(cycleStr.substring(0,1));
                int nums = (int) Math.floor(days/cycle);
                if(nums>0){
                    for(int i=0;i<nums;i++){
                        Inspect inspect = new Inspect();
                        String inspect_task_id = "";
                        inspect_task_id = redisGeneratorCode.createGenerateCode("计划任务","JH",true,4);
                        inspect.setInspectTaskId(inspect_task_id);
                        inspect.setBeginTime(daydateFormat.format(DateUtils.daysAdd(start,cycle*i)));
                        inspect.setDeadTime(daydateFormat.format(DateUtils.daysAdd(start,cycle*(i+1))));
                        inspect.setPlanId(planId);
                        inspect.setRouteId(routeId);
                        inspect.setTaskType("计划任务");
                        inspect.setCompleteRate("0");
                        inspect.setStatus("未处理");
                        list.add(inspect_task_id);
                        inspectMapper.insert(inspect);
                    }
                }
                //feign接口 routeService
                routeService.taskPoint(list,routeId);
                return RespBean.ok("生成任务完成！");
            }else{
                return RespBean.error("计划开始时间，计划结束时间或计划周期不完整，创建不了任务！map="+map+",re="+re);
            }

        }else{
            return RespBean.error("参数为空！");
        }
    }

    @Override
    public RespBean getCheckInPoints(Integer routeId,String inspectTaskId) {
        if(FieldUtils.isObjectNotEmpty(routeId) && FieldUtils.isStringNotEmpty(inspectTaskId)){
            //路线id取签到表列表
            return routeService.findListByTaskId(routeId,inspectTaskId);
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
            Map map = (Map) params.get("form");
            Map result = new HashMap();
            if(FieldUtils.isObjectNotEmpty(map.get("inspectTaskId"))){
                map.put("taskId",map.get("inspectTaskId"));
            }
            params.put("form",map);
            //修改完成率
            if(FieldUtils.isObjectNotEmpty(map.get("routeId")) && FieldUtils.isObjectNotEmpty(map.get("inspectTaskId"))){
                Integer pointId = Integer.valueOf((Integer) routeService.updatePoint(params).getObj());
                result.put("id",pointId);
                Integer routeId = Integer.valueOf((String) map.get("routeId"));
                String inspectTaskId = (String) map.get("inspectTaskId");
                List<HashMap<String,Object>> allList = (List<HashMap<String, Object>>) routeService.findListByTaskId(routeId,inspectTaskId).getObj();
                List<HashMap<String,Object>> ywcList = (List<HashMap<String, Object>>) routeService.findSignedList(routeId,inspectTaskId).getObj();
                if(allList.size()>0  && allList.size()>= ywcList.size()){
                    String complete_rate =  String.valueOf((int) Math.floor(ywcList.size()*100/allList.size()));
                    Inspect inspect = new Inspect();
                    inspect.setInspectTaskId(inspectTaskId);
                    inspect.setCompleteRate(complete_rate);
                    inspectMapper.updateById(inspect);
                    return RespBean.ok("修改成功！").setObj(result);
                }
                return RespBean.ok("签到点数量有问题！").setObj(result);
            }
           return RespBean.error("路线编号或任务编号为空！");
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
        return RespBean.ok("");
        /*if(FieldUtils.isObjectNotEmpty(params.get("planId")) && FieldUtils.isObjectNotEmpty(params.get("routeId"))){
            Integer planId = (Integer) params.get("planId");
            Integer routeId = (Integer) params.get("routeId");
            if(FieldUtils.isObjectNotEmpty(params.get("period")) && FieldUtils.isObjectNotEmpty(params.get("startTime")) && FieldUtils.isObjectNotEmpty(params.get("endTime"))){
                int period = Integer.valueOf((String) params.get("period"));
                Date start = daydateFormat.parse((String) params.get("startTime"));
                Date end = daydateFormat.parse((String) params.get("endTime"));
                int days = DateUtils.daysBetween(start,end);
                int nums = Integer.valueOf((int) Math.floor(days/period));
                if(nums>1){
                    for(int i=0;i<nums;i++){
                        Inspect inspect = new Inspect();
                        String inspect_task_id = "";
                        inspect_task_id = redisGeneratorCode.createGenerateCode("计划任务","JH",true,4);
                        inspect.setInspectTaskId(inspect_task_id);
                        inspect.setBeginTime(daydateFormat.format(DateUtils.daysAdd(start,period*i)));
                        inspect.setDeadTime(daydateFormat.format(DateUtils.daysAdd(start,period*(i+1))));
                        inspect.setPlanId(planId);
                        inspect.setRouteId(routeId);
                        inspect.setTaskType("计划任务");
                        inspect.setCompleteRate("0");
                        inspect.setStatus("未处理");
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
                return RespBean.ok("计划已制定好，！");
            }else{
                 return RespBean.error("计划开始时间，计划结束时间或计划周期不完整，无法创建计划任务！");
            }

        }else{
            return RespBean.error("参数为空！");
        }*/
    }

    @Override
    @Transactional
    public RespBean send(Map<String, Object> params,HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token =  StringUtils.substringAfter(header, "Bearer ");
        String name = (String) authService.getNameByUsername((String) authService.getCurrentUser(token).getObj()).getObj();
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
                            //修改该任务的巡查人
                            Inspect inspect = new Inspect();
                            inspect.setInspectTaskId(list.get(i).getInspectTaskId());
                            inspect.setInspectPerson(inspector);
                            inspect.setSender(name);
                            inspect.setSendTime(dateFormat.format(new Date()));
                            inspectMapper.updateById(inspect);
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

    @Transactional
    public RespBean send1(HttpServletRequest request) {
                Map<String,Object> taskMap = new HashMap<>();
                Map<String, Object> variable = new HashMap<String, Object>();
                variable.put("ROLE_BZY","inspector");
                variable.put("ROLE_BZZ","bzz" );
                taskMap.put("key","inspect");
                taskMap.put("businessKey","123456");
                taskMap.put("variable",variable);
                return activitiService.startProcess(taskMap);
    }

    @Override
    public Object getCurrentUser(String token) {
        Map map= Jwts.parser()
                .setSigningKey("ybcloud".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
        return  RespBean.ok("").setObj(map.get("user_name"));
    }

    @Override
    public RespBean findSignedList(Integer routeId, String inspectTaskId) {
        RespBean respBean = routeService.findSignedList(routeId,inspectTaskId);
        /*List<HashMap<String,Object>> list = (List<HashMap<String, Object>>) respBean.getObj();
        if(list.size()>0){
            for(int i =0; i<list.size(); i++){
                //获取报建文件列表
                if(FieldUtils.isObjectNotEmpty(list.get(i).get("fileType"))) {
                    List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>) fileService.selectOneByPid(String.valueOf((Integer) list.get(i).get("id")), (String) list.get(i).get("fileType")).getObj();

                    list.get(i).put("fileList", fileList);
                }
            }
        }
        return RespBean.ok("").setObj(list);*/
        return respBean;
    }

    @Override
    public RespBean stop(String inspectTaskId) {
        return null;
    }

    @Override
    public RespBean delete(String inspectTaskId) {
        return null;
    }
}
