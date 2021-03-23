package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaobanTech.springcloud.entity.Inspect;
import com.yaobanTech.springcloud.entity.LoginUser;
import com.yaobanTech.springcloud.entity.Test;
import com.yaobanTech.springcloud.entity.Track;
import com.yaobanTech.springcloud.entity.utils.RedisGeneratorCode;
import com.yaobanTech.springcloud.entity.utils.RespBean;
import com.yaobanTech.springcloud.mapper.InspectMapper;
import com.yaobanTech.springcloud.mapper.TestMapper;
import com.yaobanTech.springcloud.mapper.TrackMapper;
import com.yaobanTech.springcloud.service.IInspectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaobanTech.springcloud.service.feign.*;
import com.yaobanTech.springcloud.utils.DateUtils;
import com.yaobanTech.springcloud.utils.FieldUtils;
import com.yaobanTech.springcloud.utils.UrlUtils;
import io.jsonwebtoken.Jwts;
import io.micrometer.core.instrument.util.JsonUtils;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.io.TeeInputStream;
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
    private TrackMapper trackMapper;

    @Autowired
    private TestMapper testMapper;

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

    SimpleDateFormat monthdateFormat= new SimpleDateFormat("yyyy-MM");

    SimpleDateFormat yeardateFormat= new SimpleDateFormat("yyyy");


    private static final Logger logger = LoggerFactory.getLogger(InspectServiceImpl.class);

    @Value("${server.ip}")
    private String ip;

    @Value("${server.port}")
    private String port;

    @Autowired
    private UrlUtils urlUtils;

    @Override
    public RespBean getPlanInspect(long pageNo,long pageSize, Map<String, Object> params,HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException {
        Map map = (Map) params;
        RespBean feignRespBean = new RespBean();
        String type =null;
        String username = null;
        String roles = null;
        LoginUser u = urlUtils.getAll(request);
        username = u.getLoginname();
        roles = u.getRoleLists();
        //根据当前登陆人:班组员 bzy
        String date = dateFormat.format(new Date());
        String status =null;
        //创建对象
        QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
        if(FieldUtils.isObjectNotEmpty(map)){
            type = String.valueOf(map.get("status"));
            if(FieldUtils.isObjectNotEmpty(map.get("modifyBy"))){queryWrapper.eq("modify_by",map.get("modifyBy"));}
            if(FieldUtils.isObjectNotEmpty(map.get("begin_d1"))){
                Date begin_d1 =  new Date(Long.parseLong((String) map.get("begin_d1")));
                queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) >= convert(varchar(20),'"+begin_d1+"',20)");}
            if(FieldUtils.isObjectNotEmpty(map.get("begin_d2"))){
                Date begin_d2 =  new Date(Long.parseLong((String) map.get("begin_d2")));
                queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) <= convert(varchar(20),'"+begin_d2+"',20)");}
            if(FieldUtils.isObjectNotEmpty(map.get("end_d1"))){
                Date end_d1 =  new Date(Long.parseLong((String) map.get("end_d1")));
                queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),end_time,20) >= convert(varchar(20),'"+end_d1+"',20)");}
            if(FieldUtils.isObjectNotEmpty(map.get("end_d2"))){
                Date end_d2 =  new Date(Long.parseLong((String) map.get("end_d2")));
                queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),end_time,20) <= convert(varchar(20),'"+end_d2+"',20)");}
            feignRespBean = routeService.findRouteIds((String)FieldUtils.ifObjectEmptyToNullStr(map.get("waterManagementOffice")), (Integer) map.get("routeName"),(String)FieldUtils.ifObjectEmptyToNullStr(map.get("pointInspectionType")), (Integer) map.get("planName"),(String)FieldUtils.ifObjectEmptyToNullStr(map.get("planPorid")),(String)FieldUtils.ifObjectEmptyToNullStr(map.get("planType")));
        }
        List<HashMap<String,Object>> feignMap = (List<HashMap<String, Object>>) feignRespBean.getObj();
        /*if (FieldUtils.isObjectNotEmpty(feignMap)){
            if(feignMap.size()>0){
                *//*for (int i = 0; i < feignMap.size(); i++) {
                    *//**//*if (i == 0) {
                        queryWrapper.eq("route_id", feignMap.get(0).get("routeIds"));
                        queryWrapper.eq("plan_id", feignMap.get(0).get("planIds"));
                    } else {*//**//*
                    Integer routeIds = Integer.valueOf((Integer) feignMap.get(i).get("routeIds"));
                    Integer planIds = Integer.valueOf((Integer) feignMap.get(i).get("planIds"));
                    queryWrapper.and(inspectQueryWrapper -> inspectQueryWrapper.or(wrapper -> wrapper.eq("plan_id", routeIds).eq("plan_id", planIds)));

                }*//*
            }else{
                queryWrapper.eq("route_id","");
                queryWrapper.eq("plan_id","");
            }
        }*/

        //queryWrapper.eq("task_type","计划任务");
        if(feignMap.size()>0){
            queryWrapper.and(wrapper -> {
                for(HashMap<String,Object> info: feignMap){
                    wrapper.or()
                            .eq("route_id",info.get("routeIds"))
                            .eq("plan_id",info.get("planIds"));
                }
            });
        }else{
            queryWrapper.eq("route_id",0);
            queryWrapper.eq("plan_id",0);
        }
        RespBean respBean = new RespBean();
        List<Inspect> list = new ArrayList<>();
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> result = new HashMap<>();
        logger.info("该用户的角色="+roles);
        if(FieldUtils.isStringNotEmpty(roles)){
            if(roles.indexOf("BZZ")== -1){//班组长
                //queryWrapper.eq("inspect_person",username);
            }
        }else{
            //queryWrapper.eq("inspect_person",username);
        }
        IPage<Inspect> page = new Page<Inspect>(pageNo,pageSize);
        page =inspectMapper.selectPage(page,queryWrapper);
        //type  0:未处理 1：处理中 2：已完成 3：已延期 4：未派发 5：已派发 6：已终止
        if("0".equals(type)){
            //获取未处理的列表
            queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) > convert(varchar(20),'"+date+"',20)");
            list= inspectMapper.selectPage(page,queryWrapper).getRecords();
            status = "未处理";
            respBean.setMsg("未处理列表");
        }else if("1".equals(type)){
            //获取处理中的列表
            queryWrapper.lt("complete_rate", "100").apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) <= convert(varchar(20),'"+date+"',20)");
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
            QueryWrapper<Track> query = new QueryWrapper<>();
            Inspect inspect = inspectMapper.selectOne(queryWrapper.eq("inspect_task_id",inspect_task_id));
            query.eq("inspect_task_id",inspect_task_id);
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
                //根据任务编号查出gps轨迹
                List<Track> tracklist = trackMapper.selectList(query);
                combineResultMap.put("tracklist",tracklist);
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
    public RespBean addTempTask(String waterManagementOffice,Integer routeId,String routeName ,String inspector,String beginTime,String endTime) throws ParseException {
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
            inspect.setWaterManagementOffice(waterManagementOffice);
            int id = inspectMapper.insert(inspect);
            return RespBean.ok("保存成功！"+ id );
        }else{
            return RespBean.error("未读取到对象数据！");
        }
    }

    @Override
    @Transactional
    public RespBean addPlanTask(String waterManagementOffice,Integer routeId,String  routeName ,Integer planId,String planName) throws ParseException {
        if(FieldUtils.isObjectNotEmpty(planId) && FieldUtils.isObjectNotEmpty(routeId) ){
            RespBean re = planService.findById(planId);
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
                return createTask(start,end,cycleStr,routeId,planId,waterManagementOffice);
            }else{
                return RespBean.error("计划开始时间，计划结束时间或计划周期不完整，创建不了任务！map="+map+",re="+re);
            }
        }else{
            return RespBean.error("参数为空！");
        }
    }

    @Override
    @Transactional
    public RespBean autoCreate(Map<String, Object> params) throws ParseException {
        String cycleStr =null;
        Map enumMap = new HashMap();
        if(FieldUtils.isObjectNotEmpty(params.get("waterManagementOffice")) && FieldUtils.isObjectNotEmpty(params.get("planId")) && FieldUtils.isObjectNotEmpty(params.get("routeId"))){
            if(FieldUtils.isObjectNotEmpty(params.get("period")) && FieldUtils.isObjectNotEmpty(params.get("startTime")) && FieldUtils.isObjectNotEmpty(params.get("endTime"))){
                String waterManagementOffice = (String) params.get("waterManagementOffice");
                Integer planId = (Integer) params.get("planId");
                Integer routeId = (Integer) params.get("routeId");
                int period = Integer.valueOf((String) params.get("period"));
                Date start = daydateFormat.parse((String) params.get("startTime"));
                Date end = daydateFormat.parse((String) params.get("endTime"));
                RespBean respBean = routeService.findEnum(String.valueOf(period));
                if(FieldUtils.isObjectNotEmpty(respBean)){
                    enumMap = (Map)respBean.getObj();
                    if(FieldUtils.isObjectNotEmpty(enumMap.get("desc"))){
                        cycleStr = (String) enumMap.get("desc");
                    }
                }
                return createTask(start,end,cycleStr,routeId,planId,waterManagementOffice);
            }else{
                return RespBean.error("计划开始时间，计划结束时间或计划周期不完整，无法创建计划任务！");
            }
        }else{
            return RespBean.error("参数为空！");
        }
    }

    public RespBean createTask(Date start,Date end,String cycleStr,Integer routeId,Integer planId,String waterManagementOffice) throws ParseException {
        List<String> list = new ArrayList<>();
        int days = DateUtils.daysBetween(start,end)+1;
        Integer cycle = Integer.valueOf(cycleStr.substring(0,1));
        int nums = (int) Math.floor(days/cycle);
        if(nums>0){
            for(int i=0;i<nums;i++){
                Inspect inspect = new Inspect();
                String inspect_task_id = "";
                inspect_task_id = redisGeneratorCode.createGenerateCode("计划任务","JH",true,4);
                logger.info("生成的任务编号："+inspect_task_id +"           ------------------------");
                inspect.setInspectTaskId(inspect_task_id);
                inspect.setBeginTime(daydateFormat.format(DateUtils.daysAdd(start,cycle*i)));
                inspect.setDeadTime(daydateFormat.format(DateUtils.daysAdd(start,cycle*(i+1)-1)));
                inspect.setPlanId(planId);
                inspect.setRouteId(routeId);
                inspect.setTaskType("计划任务");
                inspect.setCompleteRate("0");
                inspect.setStatus("未处理");
                inspect.setWaterManagementOffice(waterManagementOffice);
                list.add(inspect_task_id);
                inspectMapper.insert(inspect);
            }
        }
        //feign接口 routeService
        routeService.taskPoint(list,routeId);
        return RespBean.ok("生成任务完成！");
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
    public RespBean send(Map<String, Object> params,HttpServletRequest request) throws UnsupportedEncodingException {

        LoginUser u = urlUtils.getAll(request);
        String name = u.getName();
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

    @Override
    public RespBean getTaskListByTime(Date begin_time1, Date begin_time2, Date dead_time1, Date dead_time2,String checkMan) {
        QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
        if(FieldUtils.isObjectNotEmpty(begin_time1)){
            queryWrapper.apply(FieldUtils.isStringNotEmpty(dateFormat.format(begin_time1)),"convert(varchar(20),begin_time,20) >= convert(varchar(20),'"+begin_time1+"',20)");
        }
        if(FieldUtils.isObjectNotEmpty(begin_time2)){
            queryWrapper.apply(FieldUtils.isStringNotEmpty(dateFormat.format(begin_time2)),"convert(varchar(20),begin_time,20) <= convert(varchar(20),'"+begin_time2+"',20)");
        }
        if(FieldUtils.isObjectNotEmpty(dead_time1)){
            queryWrapper.apply(FieldUtils.isStringNotEmpty(dateFormat.format(dead_time1)),"convert(varchar(20),dead_time,20) >= convert(varchar(20),'"+dead_time1+"',20)");
        }
        if(FieldUtils.isObjectNotEmpty(dead_time2)){
            queryWrapper.apply(FieldUtils.isStringNotEmpty(dateFormat.format(dead_time2)),"convert(varchar(20),dead_time,20) <= convert(varchar(20),'"+dead_time2+"',20)");
        }
         queryWrapper.eq("inspect_person",checkMan);
        List<Inspect> list = inspectMapper.selectList(queryWrapper);
        List<HashMap<String,Object>> result = new ArrayList<>();
        Iterator <Inspect> t = list.iterator();
        while(t.hasNext()){
            Inspect inspect = t.next();
            HashMap<String,Object> map = new HashMap<>();
            map.put("inspect_task_id",inspect.getInspectTaskId());
            map.put("inspect_person",inspect.getInspectPerson());
            result.add(map);
        }
        return RespBean.ok("").setObj(result);
    }

    @Override
    public RespBean inspectStatistics(String waterManagementOffice,String unit,String beginTime,String deadTime) throws ParseException {
        HashMap resultMap = new HashMap();
        String searchTime = null;
        //判断时间段是否为空
        if("null".equals(waterManagementOffice)){
            waterManagementOffice = "";
        }
        List<HashMap<String,Object>> result = new ArrayList<>();
        if(FieldUtils.isStringNotEmpty(unit)){
            if(FieldUtils.isStringNotEmpty(beginTime) & FieldUtils.isStringNotEmpty(deadTime) & !"null".equals(beginTime) & !"null".equals(deadTime)){
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'", Locale.US);
                //根据管理所和单位查出时间段的数据
                if("年".equals(unit)){
                    beginTime = yeardateFormat.format(dateFormat.parse(beginTime));
                    deadTime = yeardateFormat.format(dateFormat.parse(deadTime));
                    searchTime = yeardateFormat.format(DateUtils.yearsAdd(dateFormat.parse(deadTime),1));
                    result = inspectMapper.selectTasksByYears(waterManagementOffice,beginTime,searchTime);
                }
                if("月".equals(unit)){
                    beginTime = monthdateFormat.format(dateFormat.parse(beginTime));
                    deadTime = monthdateFormat.format(dateFormat.parse(deadTime));
                    searchTime = monthdateFormat.format(DateUtils.monthsAdd(dateFormat.parse(deadTime),1));
                    result = inspectMapper.selectTasksByMonths(waterManagementOffice,beginTime,searchTime);
                }
                if("日".equals(unit)){
                    beginTime = daydateFormat.format(dateFormat.parse(beginTime));
                    deadTime = daydateFormat.format(dateFormat.parse(deadTime));
                    searchTime = daydateFormat.format(DateUtils.daysAdd(dateFormat.parse(deadTime),1));
                    result = inspectMapper.selectTasksByDays(waterManagementOffice,beginTime,searchTime);
                }
            }else{
                //根据管理所和单位查出近12单位的数据
                Date currnetDate = new Date();
                Calendar calendar = Calendar.getInstance();
                if("年".equals(unit)){
                    deadTime  = yeardateFormat.format(currnetDate);
                    searchTime =  yeardateFormat.format(DateUtils.yearsAdd(currnetDate,1));
                    beginTime = yeardateFormat.format(DateUtils.yearsAdd(yeardateFormat.parse(deadTime),-11));
                    result = inspectMapper.selectTasksByYears(waterManagementOffice,beginTime,searchTime);
                }
                if("月".equals(unit)){
                    deadTime = monthdateFormat.format(currnetDate);
                    searchTime = monthdateFormat.format(DateUtils.monthsAdd(currnetDate,1));
                    beginTime = monthdateFormat.format(DateUtils.monthsAdd(monthdateFormat.parse(deadTime),-11));
                    result = inspectMapper.selectTasksByMonths(waterManagementOffice,beginTime,searchTime);
                }
                if("日".equals(unit)){
                    deadTime = daydateFormat.format(currnetDate);
                    searchTime = daydateFormat.format(DateUtils.daysAdd(currnetDate,1));
                    beginTime = daydateFormat.format(DateUtils.daysAdd(daydateFormat.parse(deadTime),-11));
                    result = inspectMapper.selectTasksByDays(waterManagementOffice,beginTime,searchTime);
                }
            }
        }else{
            //默认查出近12天的数据
        }
        //补全空数据
        result = supplement(result,unit,beginTime,deadTime);
        resultMap.put("numsList",result);
        return RespBean.ok("").setObj(resultMap);
    }

    public List<HashMap<String,Object>> supplement(List<HashMap<String,Object>> result,String unit,String beginTime,String deadTIme) throws ParseException {
        List<HashMap<String,Object>> re = new ArrayList<>();
        if("日".equals(unit)){
            Date begin =  daydateFormat.parse(beginTime);
            Date dead  =  daydateFormat.parse(deadTIme);
            int days = DateUtils.daysBetween(begin,dead);
            for (int i = 0; i < days+1; i++) {
                String tempDate = daydateFormat.format(DateUtils.daysAdd(begin,i));
                if(result.size()>0){
                    Boolean flag =false;
                    for (int j = 0; j < result.size(); j++) {
                        if(result.get(j).get("time").equals(tempDate)){
                            re.add(result.get(j));
                            flag = true;
                        }
                    }
                    if(!flag){
                        HashMap map = new HashMap();
                        map.put("time",tempDate);
                        map.put("sum",0);
                        re.add(map);
                    }
                }else{
                    HashMap map = new HashMap();
                    map.put("time",tempDate);
                    map.put("sum",0);
                    re.add(map);
                }
            }
        }else if("月".equals(unit)){
            Date begin =  monthdateFormat.parse(beginTime);
            Date dead  =  monthdateFormat.parse(deadTIme);
            int months = DateUtils.monthsBetween(begin,dead);
            for (int i = 0; i < months+1; i++) {
                String tempDate = monthdateFormat.format(DateUtils.monthsAdd(begin,i));
                if(result.size()>0){
                    Boolean flag =false;
                    for (int j = 0; j < result.size(); j++) {
                        if(result.get(j).get("time").equals(tempDate)){
                            re.add(result.get(j));
                            flag = true;
                        }
                    }
                    if(!flag){
                        HashMap map = new HashMap();
                        map.put("time",tempDate);
                        map.put("sum",0);
                        re.add(map);
                    }
                }else{
                    HashMap map = new HashMap();
                    map.put("time",tempDate);
                    map.put("sum",0);
                    re.add(map);
                }
            }
        }else if("年".equals(unit)){
            Date begin =  yeardateFormat.parse(beginTime);
            Date dead  =  yeardateFormat.parse(deadTIme);
            int years = DateUtils.yearsBetween(begin,dead);
            for (int i = 0; i < years+1; i++) {
                String tempDate = yeardateFormat.format(DateUtils.yearsAdd(begin,i));
                if(result.size()>0){
                    Boolean flag =false;
                    for (int j = 0; j < result.size(); j++) {
                        if(result.get(j).get("time").equals(tempDate)){
                            re.add(result.get(j));
                            flag = true;
                        }
                    }
                    if(!flag){
                        HashMap map = new HashMap();
                        map.put("time",tempDate);
                        map.put("sum",0);
                        re.add(map);
                    }
                }else{
                    HashMap map = new HashMap();
                    map.put("time",tempDate);
                    map.put("sum",0);
                    re.add(map);
                }
            }
        }
        return re;
    }

    @Override
    public RespBean uploadGPS(Map<String, Object> params) {
        if(FieldUtils.isObjectNotEmpty(params)){

            Track track = JSONObject.parseObject(JSONObject.toJSONString(params), Track.class);
            //创建对象
            if(FieldUtils.isStringNotEmpty(track.getInspectTaskId())){
                String date = dateFormat.format(new Date());
                track.setGpsTime(date);
                trackMapper.insert(track);
                return RespBean.ok("修改成功！").setObj(track);
            }else{
                return RespBean.error("缺少任务编号！");
            }
        }else{
            return RespBean.error("map参数为空!");
        }
    }

    //传入alllist,然后递归算出一共生成几段网络
    public Integer createGeo(HashSet<Test> alllist,int n){
        HashSet<Test> oalllist = new HashSet<>();
        List<Test> newlist = new ArrayList<>();
        boolean flag = false;
        if(!alllist.isEmpty()){
            n+=1;
            HashSet<Test> allnewlist = new HashSet<>();
            HashSet<Test> list = new HashSet<>();
            HashSet<Test> result = new HashSet<>();
            Iterator <Test> t = alllist.iterator();
            Test test = (Test) alllist.toArray()[0];
            while(t.hasNext()){
                Test tests = t.next();
                if(!test.getGid().equals(tests.getGid())){
                    allnewlist.add(tests);
                }
                oalllist.add(tests);
            }
            allnewlist.remove(test);
            list.add(test);
            result = func(allnewlist,list,list);
            //更新数据库 update
            Iterator <Test> te = result.iterator();
            while(te.hasNext()){
                Test tt = te.next();
                tt.setSid(n);
                newlist.add(tt);
            }
            if(newlist.size()<=1000){
                testMapper.updateAll(newlist,n);
            }else{
                int m= (int) Math.floor(newlist.size()/1000);
                for (int j = 0; j < m+1; j++) {
                    List<Test>  temp = new ArrayList<>();
                    for(int i =j*1000;i<(j+1)*1000 && i<newlist.size();i++ ){
                        temp.add(newlist.get(i));
                    }
                    testMapper.updateAll(temp,n);
                }
            }
            oalllist.removeAll(result);
            flag =true;
        }
        if(flag){
            createGeo(oalllist,n);
        }
        return 1;
    }

    //数组递归方法
    public HashSet<Test> func(HashSet<Test> alllist,HashSet<Test> list,HashSet<Test> olist) {
        HashSet<Test> newlist = new HashSet<>();
        boolean flag = false;
        Iterator <Test> s = list.iterator();
        Test tests = new Test();
        Test test = new Test();
        while(s.hasNext()){
            tests = s.next();
            Iterator <Test> t = alllist.iterator();
            while(t.hasNext()){
                test = t.next();
                if(tests.getSource().equals(test.getTarget()) || tests.getTarget().equals(test.getSource())
                        ||tests.getTarget().equals(test.getTarget()) || tests.getSource().equals(test.getSource())){
                    flag = true;
                    olist.add(test);
                    newlist.add(test);
                }
            }
            alllist.removeAll(newlist);
        }
        if(flag){
            func(alllist,newlist,olist);
        }
        return olist;
    }

    //数据库递归方法
    public List<Test> func1(List<Test> alllist,List<Test> list,List<Test> olist) {
        List<Test> newlist = new ArrayList<>();
        boolean flag = false;
        for (int i = 0; i < list.size(); i++) {
            //第一次 ： list s:154 t:367
            //
            flag = true;
            alllist.removeAll(newlist);
        }
        newlist.removeAll(olist);
        if(flag){
            //func(alllist,newlist,olist);
        }
        return olist;
    }


    @Override
    public RespBean bijiao(Integer gid) {
        List<Test> result1 = new ArrayList<>();
        List<Test> result2 = new ArrayList<>();
        List<Test> result3 = new ArrayList<>();
        return RespBean.ok("").setObj(result3);
    }

    @Override
    public RespBean digui(Integer gid) {
        //1:查询出该gid对应的s/t
       /* HashMap<String,Object> map = new HashMap<>();
        List<Test> alllist = new ArrayList<>();
        HashSet<Test> oalllist = new HashSet<>();
        HashSet<Test> allnewlist = new HashSet<>();
        HashSet<Test> list = new HashSet<>();
        HashSet<Test> result = new HashSet<>();
        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
        alllist = testMapper.selectList(queryWrapper);
        Test test = testMapper.selectOne(queryWrapper.eq("gid",gid));
        for (int i = 0; i < alllist.size(); i++) {
            if(!alllist.get(i).getGid().equals(test.getGid())){
                allnewlist.add(alllist.get(i));
            }
            oalllist.add(alllist.get(i));
        }
        allnewlist.remove(test);
        list.add(test);
        result = func(allnewlist,list,list);
        oalllist.removeAll(result);
        map.put("length",oalllist.size());
        map.put("list",oalllist);
        System.out.println(oalllist.size());*/
        List<Test> alllist = new ArrayList<>();
        //HashMap<String,Object> map = new HashMap<>();
        alllist = testMapper.selectAllList(gid);
        /*map.put("total",alllist.size());
        map.put("list",alllist);*/
        return RespBean.ok("").setObj(alllist);
    }
    //生成地图网络块
    public RespBean create(){
        List<Test> alllist = new ArrayList<>();
        //排序查出表中的第一条数据
        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
        HashSet<Test> oalllist = new HashSet<>();
        alllist = testMapper.selectList(queryWrapper);
        for (int i = 0; i < alllist.size(); i++) {
            oalllist.add(alllist.get(i));
        }
        return RespBean.ok("分析成功，一共生成"+createGeo(oalllist,0)+"条");
    }
    //关阀分析
    public RespBean getcloseValues(String gid){
        //0：根据gid查询出联通的节点
        //1：根据gid获取s，t
        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
        Test test = testMapper.selectOne(queryWrapper.eq("gid",gid));
        //2：拿到s，t做深度遍历找到节点
        findAllValves(test);
        //3：判断每个节点是不是阀门，找出所有阀门
        //4：跟水厂做联通分析
        //5：关闭上游阀门
        return RespBean.ok("");
    }
    public RespBean findAllValves(Test test){
        List<Test> alllist = new ArrayList<>();
        HashSet<Test> oalllist = new HashSet<>();
        HashSet<Test> allnewlist = new HashSet<>();
        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
        alllist = testMapper.selectList(queryWrapper);
        for (int i = 0; i < alllist.size(); i++) {
            if(alllist.get(i).getGid().equals(test.getGid())){
                alllist.get(i).setStatus(0);
                oalllist.add(alllist.get(i));
            }else{
                oalllist.add(alllist.get(i));
            }
        }
        //深度遍历
        int s = test.getSource();
        int t = test.getTarget();
        findValves(s,oalllist);
        //分别拿s/t去遍历
        return RespBean.ok("");
    }
    public RespBean findValves(int node,HashSet<Test> list){
        if(list.size()>0){
            Iterator <Test> s = list.iterator();
            Test test = new Test();
            while(s.hasNext()){
                test = s.next();
            }
        }
        return RespBean.ok("");
    }
    public Boolean isvalve(){
        return true;
    }

}
