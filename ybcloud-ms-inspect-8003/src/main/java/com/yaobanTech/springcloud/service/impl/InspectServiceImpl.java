package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netflix.ribbon.proxy.annotation.Http;
import com.yaobanTech.springcloud.entity.*;
import com.yaobanTech.springcloud.entity.utils.RedisGeneratorCode;
import com.yaobanTech.springcloud.entity.utils.RespBean;
import com.yaobanTech.springcloud.mapper.DeviceMapper;
import com.yaobanTech.springcloud.mapper.InspectMapper;
import com.yaobanTech.springcloud.mapper.TestMapper;
import com.yaobanTech.springcloud.mapper.TrackMapper;
import com.yaobanTech.springcloud.service.IInspectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaobanTech.springcloud.service.feign.*;
import com.yaobanTech.springcloud.utils.DateUtils;
import com.yaobanTech.springcloud.utils.FieldUtils;
import com.yaobanTech.springcloud.utils.NumberUtils;
import com.yaobanTech.springcloud.utils.UrlUtils;
import io.jsonwebtoken.Jwts;
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
    private DeviceMapper deviceMapper;

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

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

    private static final Logger logger = LoggerFactory.getLogger(InspectServiceImpl.class);

    // 计算两点距离
    private final double EARTH_RADIUS = 6378137.0;

    @Value("${server.ip}")
    private String ip;

    @Value("${server.port}")
    private String port;

    @Autowired
    private UrlUtils urlUtils;

    @Override
    public RespBean getPlanInspect(long pageNo,long pageSize, Map<String, Object> params,HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException, ParseException {
        Map map = (Map) params;
        RespBean feignRespBean = new RespBean();
        String type =null;
        String username = null;
        String roles = null;
        LoginUser u = urlUtils.getAll(request);
        String token = urlUtils.getToken(request);
        Integer ttype =urlUtils.getType(request);
        username = u.getLoginname();
        roles = u.getRoleLists();
        //根据当前登陆人
        String date = daydateFormat.format(new Date());
        String status =null;
        //创建对象
        QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("begin_time");
        if(FieldUtils.isObjectNotEmpty(map)){
            type = String.valueOf(map.get("status"));
            if(FieldUtils.isObjectNotEmpty(map.get("name"))){queryWrapper.eq("name",map.get("name"));}
            if(FieldUtils.isObjectNotEmpty(map.get("modifyBy"))){queryWrapper.eq("sender",map.get("modifyBy"));}
            if(FieldUtils.isObjectNotEmpty(map.get("begin_d1"))){
                String begin_d1 = (String) map.get("begin_d1");
                queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) >= convert(varchar(20),'"+begin_d1+"',20)");}
            if(FieldUtils.isObjectNotEmpty(map.get("begin_d2"))){
                String begin_d2 = (String) map.get("begin_d2");
                queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) <= convert(varchar(20),'"+begin_d2+"',20)");}
            if(FieldUtils.isObjectNotEmpty(map.get("end_d1"))){
                String end_d1 =  (String) map.get("end_d1");
                queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),dead_time,20) >= convert(varchar(20),'"+end_d1+"',20)");}
            if(FieldUtils.isObjectNotEmpty(map.get("end_d2"))){
                String end_d2 =  (String) map.get("end_d2");
                queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),dead_time,20) <= convert(varchar(20),'"+end_d2+"',20)");}
            feignRespBean = routeService.findRouteIds((String)FieldUtils.ifObjectEmptyToNullStr(map.get("waterManagementOffice")), (Integer) map.get("routeName"),(String)FieldUtils.ifObjectEmptyToNullStr(map.get("pointInspectionType")), (Integer) map.get("planName"),(String)FieldUtils.ifObjectEmptyToNullStr(map.get("planPorid")),(String)FieldUtils.ifObjectEmptyToNullStr(map.get("planType")),(String)FieldUtils.ifObjectEmptyToNullStr(map.get("routeType")));
        }
        List<HashMap<String,Object>> feignlist = (List<HashMap<String, Object>>) feignRespBean.getObj();

        if(FieldUtils.isObjectNotEmpty(map.get("tasktype"))){
            queryWrapper.eq("task_type",map.get("tasktype"));
        }
        if(FieldUtils.isObjectNotEmpty(feignlist)){
            if(feignlist.size()>0){
                queryWrapper.and(wrapper -> {
                    for(HashMap<String,Object> info: feignlist){
                        wrapper.or()
                                .eq("route_id",info.get("route_id"))
                                .eq("plan_id",info.get("plan_id"));
                    }
                });
            }else{
                queryWrapper.eq("route_id",0);
                queryWrapper.eq("plan_id",0);
            }
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
                queryWrapper.eq("inspect_person",username);
            }
        }else{
            queryWrapper.eq("inspect_person",username);
        }
        //前端选择全部之后，pagesize就是最大数值
        if(pageSize == 1){
            QueryWrapper<Inspect> query = new QueryWrapper<>();
            pageSize = inspectMapper.selectList(query).size();
        }
        IPage<Inspect> page = new Page<Inspect>(pageNo,pageSize);
        page =inspectMapper.selectPage(page,queryWrapper);
        //type  0:未处理 1：处理中 2：已完成 3：已延期 4：未派发 5：已派发 6：已终止
        if("0".equals(type)){
            //获取未处理的列表
            //queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) > convert(varchar(20),'"+date+"',20)");
            queryWrapper.eq("status","未处理");
            respBean.setMsg("未处理列表");
        }else if("1".equals(type)){
            //获取处理中的列表
            //queryWrapper.lt("complete_rate", "100").apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) <= convert(varchar(20),'"+date+"',20)");
            queryWrapper.eq("status","处理中");
            respBean.setMsg("处理中列表");
        }else if("2".equals(type)){
            //获取已完成的列表
            //queryWrapper.eq("complete_rate","100");
            queryWrapper.eq("status","已完成");
            respBean.setMsg("已完成列表");
        }else if("3".equals(type)){
            //获取已延期的列表
            queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),dead_time,20) < convert(varchar(20),'"+date+"',20)");
            queryWrapper.lt("complete_rate", "100");
            respBean.setMsg("已延期列表");
        }else if("4".equals(type)){
            queryWrapper.eq("status","未派发");
            respBean.setMsg("未派发列表");
        }else if("5".equals(type)){
            queryWrapper.and(wrapper ->wrapper.eq("status","未处理").or().eq("status","处理中").or().eq("status","已完成").or().eq("status","已延期"));
            respBean.setMsg("已派发列表");
        }else if("6".equals(type)){
            queryWrapper.eq("status","已终止");
            respBean.setMsg("已终止列表");
        }else{
            respBean.setMsg("全部列表");
        }
        list= inspectMapper.selectPage(page,queryWrapper).getRecords();
        //获取中文名
        //list= getlistName(list);
        //list= inspectMapper.selectdiyPage(page,queryWrapper).getRecords();
        if(list.size()>0){
            for(int i = 0;i<list.size();i++){
                Map<String, Object> map1 = new HashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                Map<String, Object> map3 = new HashMap<String, Object>();
                Inspect inspect = list.get(i);
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
                map1.put("planName", "");
                map1.put("planType", "");
                map1.put("planPorid", "");
                map1.put("planTypeMenu","");
                map1.put("planPoridMenu","");
                //创建对象
                /*if(FieldUtils.isObjectNotEmpty(inspect.getRouteId())){
                    map2 = (Map<String, Object>) routeService.findDetail(inspect.getRouteId()).getObj();
                }
                if(FieldUtils.isObjectNotEmpty(inspect.getPlanId())){
                    map3 = (Map<String, Object>) planService.findById(inspect.getPlanId()).getObj();
                }*/
                if(FieldUtils.isObjectNotEmpty(feignlist)){
                    if(feignlist.size()>0){
                        for (int j = 0; j < feignlist.size(); j++) {
                            if(list.get(i).getRouteId().equals(feignlist.get(j).get("route_id")) && list.get(i).getPlanId().equals(feignlist.get(j).get("plan_id"))){
                                map1 = FieldUtils.objectToMap(inspect);
                                map1.put("routeName", FieldUtils.ifObjectEmpty(feignlist.get(j).get("route_name")));
                                map1.put("routeType", FieldUtils.ifObjectEmpty(feignlist.get(j).get("route_type")));
                                map1.put("waterManagementOffice", FieldUtils.ifObjectEmpty(feignlist.get(j).get("water_management_office")));
                                map1.put("planInspectionMileage", FieldUtils.ifObjectEmpty(feignlist.get(j).get("plan_inspection_mileage")));
                                map1.put("pointInspectionType", FieldUtils.ifObjectEmpty(feignlist.get(j).get("point_inspection_type")));
                                map1.put("hiddenDangerAmount", FieldUtils.ifObjectEmpty(feignlist.get(j).get("hidden_danger_amount")));
                                map1.put("overReason", FieldUtils.ifObjectEmpty(feignlist.get(j).get("over_reason")));
                                map1.put("signIn", FieldUtils.ifObjectEmpty(feignlist.get(j).get("sign_in")));
                                map1.put("waterOfficeMenu", FieldUtils.ifObjectEmpty(feignlist.get(j).get("water_office_menu")));
                                map1.put("routeTypeMenu", FieldUtils.ifObjectEmpty(feignlist.get(j).get("route_type_menu")));
                                map1.put("pointInspectionTypeMenu", FieldUtils.ifObjectEmpty(feignlist.get(j).get("point_inspection_type_menu")));

                                map1.put("planName", FieldUtils.ifObjectEmpty(feignlist.get(j).get("plan_name")));
                                map1.put("planType", FieldUtils.ifObjectEmpty(feignlist.get(j).get("plan_type")));
                                map1.put("planPorid", FieldUtils.ifObjectEmpty(feignlist.get(j).get("plan_porid")));
                                map1.put("planTypeMenu", FieldUtils.ifObjectEmpty(feignlist.get(j).get("plan_type_menu")));
                                map1.put("planPoridMenu",FieldUtils.ifObjectEmpty(feignlist.get(j).get("plan_porid_menu")));

                                resultList.add(map1);
                            }
                        }
                    }
                }else{
                    result.put("total",0);
                    result.put("list", new ArrayList<>());
                    return RespBean.ok("数据为空").setObj(result);
                }
            }
        }
        result.put("total",page.getTotal());
        result.put("list",resultList);
        return RespBean.ok("").setObj(result);
    }

    private List<Inspect> getlistName(List<Inspect> list,HttpServletRequest request) throws UnsupportedEncodingException {
        //获取人员名称列表
        RespBean respBean = urlUtils.selectUserByRole("64",request);
        List<HashMap<String,String>> userlist = (List<HashMap<String, String>>) respBean.getObj();
        if(list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                if(userlist.size()>0){
                    for (int j = 0; j < userlist.size(); j++) {
                        if(userlist.get(j).get("username").equals(list.get(i).getInspectPerson())){
                            list.get(i).setInspectPerson(userlist.get(j).get("name"));
                        }
                    }
                }
            }
        }
        return list;
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
    public RespBean getInspectDetailById(String inspect_task_id,HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException, ParseException {
        if(FieldUtils.isStringNotEmpty(inspect_task_id)){
            Map<String, Object> map1 = new HashMap<String, Object>();
            Map<String, Object> map2 = new HashMap<String, Object>();
            Map<String, Object> map3 = new HashMap<String, Object>();
            //创建对象
            QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
            QueryWrapper<Track> query = new QueryWrapper<>();
            Inspect inspect = inspectMapper.selectOne(queryWrapper.eq("inspect_task_id",inspect_task_id));
            query.eq("inspect_person",inspect.getInspectPerson());
            String person = inspect.getInspectPerson();
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
                String date = dateFormat.format(new Date());
                Date endDate = new Date();
                //查出作业前和作业后的附件
                List<HashMap<String,Object>> qfilelist = (List<HashMap<String, Object>>) fileService.selectOneByPid(inspect_task_id,"作业前").getObj();
                List<HashMap<String,Object>> hfilelist = (List<HashMap<String, Object>>) fileService.selectOneByPid(inspect_task_id,"作业后").getObj();
                //根据任务编号查出gps轨迹,判断是否任务已开始有轨迹,没有开始就没有轨迹
                List<Track> tracklist = new ArrayList<>();
                if(FieldUtils.isObjectNotEmpty(inspect.getActBeginTime())){
                    System.out.println(inspect_task_id+"开始时间不为空,开始时间是:"+inspect.getActBeginTime());
                    //说明任务已开始
                    if(FieldUtils.isObjectNotEmpty(inspect.getActEndTime())){
                        //说明任务做完了
                        System.out.println(inspect_task_id+"结束时间不为空,结束时间是:"+inspect.getActEndTime());
                        query.apply("convert(varchar(20),gps_time,20) >= convert(varchar(20),'"+inspect.getActBeginTime()+"',20)");
                        query.apply("convert(varchar(20),gps_time,20) <= convert(varchar(20),'"+inspect.getActEndTime()+"',20)");
                        tracklist = trackMapper.selectList(query);
                    }else{
                        //和目前时间作比较
                        System.out.println(inspect_task_id+"结束时间为空");
                        query.apply("convert(varchar(20),gps_time,20) >= convert(varchar(20),'"+inspect.getActBeginTime()+"',20)");
                        query.apply("convert(varchar(20),gps_time,20) <= convert(varchar(20),'"+date+"',20)");
                        tracklist = trackMapper.selectList(query);
                    }
                }
                combineResultMap.put("tracklist",tracklist);
                combineResultMap.put("qfilelist",qfilelist);
                combineResultMap.put("hfilelist",hfilelist);
                return RespBean.ok("").setObj(combineResultMap);
            }
            return RespBean.ok("数据信息有误！").setObj("");
        }else{
            return RespBean.error("缺少任务编号");
        }
    }

    @Override
    public RespBean getFeignInspectDetailById(List<String> taskIds) {
        QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
        List<Inspect> list = new ArrayList<>();
        if(taskIds.size()>0){
            list = inspectMapper.selectList(queryWrapper.in("inspect_task_id",taskIds));
        }
        return RespBean.ok("").setObj(list);
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
    public RespBean addPlanTask(String waterManagementOffice,Integer routeId,String  routeName ,Integer planId,String planName,HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
        LoginUser u = urlUtils.getAll(request);
        String name = u.getName();
        if(FieldUtils.isObjectNotEmpty(planId) && FieldUtils.isObjectNotEmpty(routeId) ){
            RespBean re = planService.findById(planId);
            Map map = (Map) re.getObj();
            Map enumMap = new HashMap();
            Map<String, Object> tempmap =new HashMap<>();
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
                tempmap = (Map<String, Object>) routeService.findDetail(routeId).getObj();
                int all = (int) tempmap.get("signIn");
                createTask(name,start,end,cycleStr,routeId,planId,waterManagementOffice,"",all,"2","");
                return RespBean.ok("参数为空！");
            }else{
                return RespBean.error("计划开始时间，计划结束时间或计划周期不完整，创建不了任务！map="+map+",re="+re);
            }
        }else{
            return RespBean.error("参数为空！");
        }
    }

    @Override
    @Transactional
    public RespBean autoCreate(Map<String, Object> params,HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
        LoginUser u = urlUtils.getAll(request);
        String name = u.getName();
        String cycleStr =null;
        Map enumMap = new HashMap();
        Map<String, Object> map = new HashMap<>();
        if(FieldUtils.isObjectNotEmpty(params.get("waterManagementOffice")) && FieldUtils.isObjectNotEmpty(params.get("planId")) && FieldUtils.isObjectNotEmpty(params.get("routeId"))){
            if(FieldUtils.isObjectNotEmpty(params.get("period")) && FieldUtils.isObjectNotEmpty(params.get("startTime")) && FieldUtils.isObjectNotEmpty(params.get("endTime"))){
                String waterManagementOffice = (String) params.get("waterManagementOffice");
                Integer planId = (Integer) params.get("planId");
                Integer routeId = (Integer) params.get("routeId");
                String  diameter = (String) params.get("diameter");
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
                //获取路线详情
                map = (Map<String, Object>) routeService.findDetail(routeId).getObj();
                int all = (int) map.get("signIn");
                String area = (String)map.get("area");
                return createTask(name,start,end,cycleStr,routeId,planId,waterManagementOffice,diameter,all,"1",area);
            }else{
                return RespBean.error("计划开始时间，计划结束时间或计划周期不完整，无法创建计划任务！");
            }
        }else{
            return RespBean.error("参数为空！");
        }
    }

    public RespBean createTask(String name,Date start,Date end,String cycleStr,Integer routeId,Integer planId,String waterManagementOffice,String diameter,int all,String type,String area) throws ParseException {
        Integer cycle  = NumberUtils.numberTransform(cycleStr.substring(0,1));
        int days=0;//相差几个单位的时间
        int nums=0;//任务的数量
        //如果按照天去巡查
        if(cycleStr.contains("天")){
            days = DateUtils.daysBetween(start,end)+1;
            nums = (int) Math.floor(days/cycle);
        }else if(cycleStr.contains("周")){
            days = DateUtils.daysBetween(start,end)+1;
            nums = (int) Math.floor(days/7/cycle);
        }else if(cycleStr.contains("月")){
            days = DateUtils.monthsBetween(start,end)+1;
            nums = (int) Math.floor(days/cycle);
        }else if(cycleStr.contains("季")){
            days = DateUtils.monthsBetween(start,end)+1;
            nums = (int) Math.floor(days/3/cycle);
        }else if(cycleStr.contains("年")){
            days = DateUtils.yearsBetween(start,end)+1;
            nums = (int) Math.floor(days/cycle);
        }
        List<String> list = new ArrayList<>();
        /*int days = DateUtils.daysBetween(start,end)+1;*/
        /*cycle = Integer.valueOf(cycleStr.substring(0,1));*/
        if(FieldUtils.isObjectNotEmpty(routeId)){
            String date = dateFormat.format(new Date());
            if(nums>0){
                for(int i=0;i<nums;i++){
                    Inspect inspect = new Inspect();
                    String inspect_task_id = "";
                    if("3".equals(waterManagementOffice)){
                        inspect_task_id = redisGeneratorCode.createGenerateCode("城南计划任务","CN",true,4);
                    }else if("4".equals(waterManagementOffice)){
                        inspect_task_id = redisGeneratorCode.createGenerateCode("城北计划任务","CB",true,4);
                    }else{
                        inspect_task_id = redisGeneratorCode.createGenerateCode("石角计划任务","SJ",true,4);
                    }

                    logger.info("生成的任务编号："+inspect_task_id +"------------------------");
                    inspect.setInspectTaskId(inspect_task_id);
                    if(cycleStr.contains("天")){
                        inspect.setBeginTime(daydateFormat.format(DateUtils.daysAdd(start,cycle*i)));
                        inspect.setDeadTime(daydateFormat.format(DateUtils.daysAdd(start,cycle*(i+1)-1)));
                    }else if(cycleStr.contains("周")){
                        inspect.setBeginTime(daydateFormat.format(DateUtils.daysAdd(start,cycle*i*7)));
                        inspect.setDeadTime(daydateFormat.format(DateUtils.daysAdd(start,cycle*(i+1)*7-1)));
                    }else if(cycleStr.contains("月")){
                        inspect.setBeginTime(daydateFormat.format(DateUtils.monthsAdd(start,cycle*i)));
                        inspect.setDeadTime(daydateFormat.format(DateUtils.daysAdd(DateUtils.monthsAdd(start,cycle*(i+1)),-1)));
                    }else if(cycleStr.contains("季")){
                        inspect.setBeginTime(daydateFormat.format(DateUtils.monthsAdd(start,cycle*i*3)));
                        inspect.setDeadTime(daydateFormat.format(DateUtils.daysAdd(DateUtils.monthsAdd(start,cycle*(i+1)*3),-1)));
                    }else if(cycleStr.contains("年")){
                        inspect.setBeginTime(daydateFormat.format(DateUtils.yearsAdd(start,cycle*i)));
                        inspect.setDeadTime(daydateFormat.format(DateUtils.daysAdd(DateUtils.yearsAdd(start,cycle*(i+1)),-1)));
                    }
                    inspect.setPlanId(planId);
                    inspect.setRouteId(routeId);
                    inspect.setTaskType("计划任务");
                    inspect.setCompleteRate("0");
                    inspect.setStatus("未派发");
                    inspect.setWaterManagementOffice(waterManagementOffice);
                    inspect.setDiameter(diameter);
                    inspect.setCycle(cycleStr);
                    inspect.setCreateTime(date);
                    inspect.setAllpoint(all);
                    inspect.setDonepoint(0);
                    inspect.setInplacepoint(0);
                    inspect.setSender(name);
                    inspect.setModifyBy(name);
                    inspect.setArea(area);
                    list.add(inspect_task_id);
                    inspectMapper.insert(inspect);
                }
            }
            //feign接口 routeService
            System.out.println("生成任务数量="+list.size()+"---------------");
            if("1".equals(type)){
                if(FieldUtils.isObjectNotEmpty(planId)){
                    routeService.taskPoint(list,routeId,planId);
                }else{
                    return RespBean.ok("路线编号或计划编号为空！");
                }
            }else{
                routeService.taskPoint(list,routeId,planId);
            }
        }
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
                List<HashMap<String,Object>> ydwList = (List<HashMap<String, Object>>) routeService.findSignedList(routeId,inspectTaskId).getObj();
                if(allList.size()>0  && allList.size()>= ywcList.size()){
                    String complete_rate =  String.valueOf((int) Math.floor(ywcList.size()*100/allList.size()));
                    QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
                    Inspect inspect = inspectMapper.selectOne(queryWrapper.eq("inspect_task_id",inspectTaskId));
                    inspect.setInspectTaskId(inspectTaskId);
                    inspect.setCompleteRate(complete_rate);
                    //inspect.setAllpoint(allList.size());
                    inspect.setDonepoint(ywcList.size());
                    inspect.setInplacepoint(ydwList.size());
                    inspect.setArrivalRate(String.valueOf((int) Math.floor(ydwList.size()*100/allList.size())));
                    String siteConditionsDesc ="";
                    if("null".equals(map.get("siteConditionsDesc")) || !FieldUtils.isObjectNotEmpty(map.get("siteConditionsDesc"))){
                        siteConditionsDesc="";
                    }else{
                        siteConditionsDesc = (String) map.get("siteConditionsDesc");
                    }
                    String resultStr = inspect.getResult()+siteConditionsDesc+";";
                    inspect.setResult(resultStr);
                    /*if("100".equals(complete_rate)){
                        inspect.setStatus("已完成");
                    }*/
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
                String chname =  (String) map.get("name");
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
                            //activitiService.startProcess(taskMap);
                            //修改该任务的巡查人
                            Inspect inspect = new Inspect();
                            inspect.setInspectTaskId(list.get(i).getInspectTaskId());
                            inspect.setInspectPerson(inspector);
                            inspect.setSender(name);
                            inspect.setName(chname);
                            inspect.setStatus("未处理");
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

    @Override
    @Transactional
    public RespBean batchSend(Map<String, Object> params, HttpServletRequest request) throws UnsupportedEncodingException {
        LoginUser u = urlUtils.getAll(request);
        String name = u.getName();
        Map map = (Map) params;
        RespBean feignRespBean = new RespBean();
        //根据当前登陆人
        String date = daydateFormat.format(new Date());
        String inspector = null;
        String chname =  null;
        //创建对象
        QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
        if(FieldUtils.isObjectNotEmpty(map)){
            inspector = (String) map.get("inspector");
            chname =  (String) map.get("name");
            if(FieldUtils.isObjectNotEmpty(map.get("begin_d1"))){
                String begin_d1 = (String) map.get("begin_d1");
                queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) >= convert(varchar(20),'"+begin_d1+"',20)");}
            if(FieldUtils.isObjectNotEmpty(map.get("begin_d2"))){
                String begin_d2 = (String) map.get("begin_d2");
                queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),begin_time,20) <= convert(varchar(20),'"+begin_d2+"',20)");}
            feignRespBean = routeService.findRouteIds((String)FieldUtils.ifObjectEmptyToNullStr(map.get("waterManagementOffice")), (Integer) map.get("routeName"),(String)FieldUtils.ifObjectEmptyToNullStr(map.get("pointInspectionType")), (Integer) map.get("planName"),(String)FieldUtils.ifObjectEmptyToNullStr(map.get("planPorid")),(String)FieldUtils.ifObjectEmptyToNullStr(map.get("planType")),(String)FieldUtils.ifObjectEmptyToNullStr(map.get("routeType")));
        }
        List<HashMap<String,Object>> feignlist = (List<HashMap<String, Object>>) feignRespBean.getObj();

        if(FieldUtils.isObjectNotEmpty(feignlist)){
            if(feignlist.size()>0){
                queryWrapper.and(wrapper -> {
                    for(HashMap<String,Object> info: feignlist){
                        wrapper.or()
                                .eq("route_id",info.get("route_id"))
                                .eq("plan_id",info.get("plan_id"));
                    }
                });
            }else{
                queryWrapper.eq("route_id",0);
                queryWrapper.eq("plan_id",0);
            }
        }else{
            queryWrapper.eq("route_id",0);
            queryWrapper.eq("plan_id",0);
        }
        List<Inspect> list = new ArrayList<>();
        List<Inspect> result = new ArrayList<>();
        list= inspectMapper.selectList(queryWrapper);
        if(list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getStatus().equals("已终止") || list.get(i).getStatus().equals("未派发")){
                    result.add(list.get(i));
                }
            }
        }
        return RespBean.ok("确定派发给"+inspector+result.size()+"个任务吗？").setObj(result);
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
                //return activitiService.startProcess(taskMap);
        return RespBean.ok("");
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
    public RespBean getTaskListByTime(Date begin_time1, Date begin_time2, Date dead_time1, Date dead_time2,String checkMan,HttpServletRequest request) throws UnsupportedEncodingException {
        QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
        if(FieldUtils.isObjectNotEmpty(begin_time1)){
            queryWrapper.apply(FieldUtils.isStringNotEmpty(dateFormat.format(begin_time1)),"convert(varchar(20),begin_time,20) >= convert(varchar(20),'"+daydateFormat.format(begin_time1)+"',20)");
        }
        if(FieldUtils.isObjectNotEmpty(begin_time2)){
            queryWrapper.apply(FieldUtils.isStringNotEmpty(dateFormat.format(begin_time2)),"convert(varchar(20),begin_time,20) <= convert(varchar(20),'"+daydateFormat.format(begin_time2)+"',20)");
        }
        if(FieldUtils.isObjectNotEmpty(dead_time1)){
            queryWrapper.apply(FieldUtils.isStringNotEmpty(dateFormat.format(dead_time1)),"convert(varchar(20),dead_time,20) >= convert(varchar(20),'"+daydateFormat.format(dead_time1)+"',20)");
        }
        if(FieldUtils.isObjectNotEmpty(dead_time2)){
            queryWrapper.apply(FieldUtils.isStringNotEmpty(dateFormat.format(dead_time2)),"convert(varchar(20),dead_time,20) <= convert(varchar(20),'"+daydateFormat.format(dead_time2)+"',20)");
        }
        if(FieldUtils.isStringNotEmpty(checkMan)){
            queryWrapper.eq("inspect_person",checkMan);
        }
        List<Inspect> list = inspectMapper.selectList(queryWrapper);
        list = getlistName(list,request);
        List<HashMap<String,Object>> result = new ArrayList<>();
        Iterator <Inspect> t = list.iterator();
        while(t.hasNext()){
            /*Map<String, Object> map3= new HashMap<>();*/
            Inspect inspect = t.next();
            /*if(FieldUtils.isObjectNotEmpty(inspect.getPlanId())){
                map3 = (Map<String, Object>) planService.findById(inspect.getPlanId()).getObj();
            }*/
            HashMap<String,Object> map = new HashMap<>();
            map.put("inspect_task_id",inspect.getInspectTaskId());
            map.put("inspect_person",inspect.getInspectPerson());
            map.put("begin_time",inspect.getBeginTime());
            map.put("dead_time",inspect.getDeadTime());
            /*if(FieldUtils.isObjectNotEmpty(map3)){
                map.put("plan_name",map3.get("planName"));
            }else{
                map.put("plan_name","");
            }*/
            result.add(map);
        }
        return RespBean.ok("").setObj(result);
    }

    @Override
    public RespBean inspectStatistics(String waterManagementOffice,String unit,String beginTime,String deadTime) throws ParseException {
        unit = "日";
        HashMap resultMap = new HashMap();
        String searchTime = null;
        //判断时间段是否为空
        if("null".equals(waterManagementOffice)){
            waterManagementOffice = "";
        }
        List<HashMap<String,Object>> sumresult = new ArrayList<>();
        List<HashMap<String,Object>> rateresult = new ArrayList<>();
        List<HashMap<String,Object>> rdwlresult = new ArrayList<>();
        List<HashMap<String,Object>> logresult = new ArrayList<>();
        if(FieldUtils.isStringNotEmpty(unit)){
            if(FieldUtils.isStringNotEmpty(beginTime) & FieldUtils.isStringNotEmpty(deadTime) & !"null".equals(beginTime) & !"null".equals(deadTime)){
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'", Locale.US);
                //根据管理所和单位查出时间段的数据
                if("年".equals(unit)){
                    /*beginTime = yeardateFormat.format(dateFormat.parse(beginTime));
                    deadTime = yeardateFormat.format(dateFormat.parse(deadTime));*/
                    searchTime = yeardateFormat.format(DateUtils.yearsAdd(yeardateFormat.parse(deadTime),1));
                    sumresult = inspectMapper.selectTasksByYears(waterManagementOffice,beginTime,searchTime);
                }
                if("月".equals(unit)){
                    /*beginTime = monthdateFormat.format(dateFormat.parse(beginTime));
                    deadTime = monthdateFormat.format(dateFormat.parse(deadTime));*/
                    searchTime = monthdateFormat.format(DateUtils.monthsAdd(monthdateFormat.parse(deadTime),1));
                    sumresult = inspectMapper.selectTasksByMonths(waterManagementOffice,beginTime,searchTime);
                }
                if("日".equals(unit)){
                    /*beginTime = daydateFormat.format(beginTime);
                    deadTime = daydateFormat.format(deadTime);*/
                    searchTime = daydateFormat.format(DateUtils.daysAdd(daydateFormat.parse(deadTime),1));
                    sumresult = inspectMapper.selectTasksByDays(waterManagementOffice,beginTime,searchTime);
                    rateresult = inspectMapper.selectTasksRateByDays(waterManagementOffice,beginTime,searchTime);
                    logresult = inspectMapper.selectTaskslogByDays(waterManagementOffice,beginTime,searchTime);
                    rdwlresult = inspectMapper.selectTaskDwlByDays(waterManagementOffice,beginTime,searchTime);
                }
            }else{
                //根据管理所和单位查出近12单位的数据
                Date currnetDate = new Date();
                Calendar calendar = Calendar.getInstance();
                if("年".equals(unit)){
                    deadTime  = yeardateFormat.format(currnetDate);
                    searchTime =  yeardateFormat.format(DateUtils.yearsAdd(currnetDate,1));
                    beginTime = yeardateFormat.format(DateUtils.yearsAdd(yeardateFormat.parse(deadTime),-11));
                    sumresult = inspectMapper.selectTasksByYears(waterManagementOffice,beginTime,searchTime);
                }
                if("月".equals(unit)){
                    deadTime = monthdateFormat.format(currnetDate);
                    searchTime = monthdateFormat.format(DateUtils.monthsAdd(currnetDate,1));
                    beginTime = monthdateFormat.format(DateUtils.monthsAdd(monthdateFormat.parse(deadTime),-11));
                    sumresult = inspectMapper.selectTasksByMonths(waterManagementOffice,beginTime,searchTime);
                }
                if("日".equals(unit)){
                    deadTime = daydateFormat.format(currnetDate);
                    searchTime = daydateFormat.format(DateUtils.daysAdd(currnetDate,1));
                    beginTime = daydateFormat.format(DateUtils.daysAdd(daydateFormat.parse(deadTime),-11));
                    sumresult = inspectMapper.selectTasksByDays(waterManagementOffice,beginTime,searchTime);
                    rateresult = inspectMapper.selectTasksRateByDays(waterManagementOffice,beginTime,searchTime);
                    logresult = inspectMapper.selectTaskslogByDays(waterManagementOffice,beginTime,searchTime);
                    rdwlresult = inspectMapper.selectTaskDwlByDays(waterManagementOffice,beginTime,searchTime);
                }
            }
        }else{
            //默认查出近12天的数据
        }
        //补全空数据
        sumresult = supplement(sumresult,unit,beginTime,deadTime);
        rateresult = supplement(rateresult,unit,beginTime,deadTime);
        rdwlresult = supplement(rdwlresult,unit,beginTime,deadTime);
        resultMap.put("numsList",sumresult);
        resultMap.put("rateresult",rateresult);
        resultMap.put("logresult",logresult);
        resultMap.put("dwlresult",rdwlresult);
        return RespBean.ok("logresult:巡查日志表，rateresult：完成率，dwlresult：到位率，numsList：数量统计").setObj(resultMap);
    }

    @Override
    public RespBean appInspectStatistics(HttpServletRequest request) throws UnsupportedEncodingException {
        String date = dateFormat.format(new Date());
        LoginUser u = urlUtils.getAll(request);
        String username = u.getLoginname();
        QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("inspect_person",username);
        HashMap<String,Object> result = new HashMap<>();
        List<Inspect> total = inspectMapper.selectList(queryWrapper);
        List<Inspect> done = inspectMapper.selectList(queryWrapper.eq("status","已完成"));
        queryWrapper = new QueryWrapper<>();
        List<Inspect> undone = inspectMapper.selectList(queryWrapper.eq("status","未处理"));
        queryWrapper = new QueryWrapper<>();
        List<Inspect> doing = inspectMapper.selectList(queryWrapper.eq("status","处理中"));
        queryWrapper = new QueryWrapper<>();
        queryWrapper.apply(FieldUtils.isStringNotEmpty(date),"convert(varchar(20),dead_time,20) < convert(varchar(20),'"+date+"',20)");
        queryWrapper.lt("complete_rate", "100");
        List<Inspect> over = inspectMapper.selectList(queryWrapper.eq("status","已超时"));
        queryWrapper = new QueryWrapper<>();
        List<Inspect> end = inspectMapper.selectList(queryWrapper.eq("status","已终止"));
        if(total.size()>0){
            result.put("total",total.size());
            result.put("done",done.size());
            result.put("undone",undone.size());
            result.put("doing",doing.size());
            result.put("over",over.size());
            result.put("end",end.size());
        }else{
            result.put("total",0);
            result.put("done",0);
            result.put("undone",0);
            result.put("doing",0);
            result.put("over",0);
            result.put("end",0);
        }
        return RespBean.ok("").setObj(result);
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

    @Override
    public RespBean uploadDeviceGPS(Map<String, Object> params) {
        if(FieldUtils.isObjectNotEmpty(params)){
            Device device = JSONObject.parseObject(JSONObject.toJSONString(params), Device.class);
            //创建对象
            if(FieldUtils.isStringNotEmpty(device.getInspectTaskId())){
                String date = dateFormat.format(new Date());
                device.setCountTime(date);
                deviceMapper.updateById(device);
                return RespBean.ok("修改成功！").setObj(device);
            }else{
                return RespBean.error("缺少任务编号！");
            }
        }else{
            return RespBean.error("map参数为空!");
        }
    }

    @Override
    public RespBean getDeviceInfo(HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
        //创建对象
        Map<String, Object> result = new HashMap<String, Object>();
        List<Device> list = new ArrayList<>();
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status","在线");
        List<HashMap<String, Object>> personlist = (List<HashMap<String, Object>>) urlUtils.selectUserByRole("901",request).getObj();
        list = deviceMapper.selectList(queryWrapper);
        if(list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                Map<String, Object> planmap = new HashMap<String, Object>();
                String d = DateUtils.calLastedTime(dateFormat.parse(list.get(i).getCountTime()));
                list.get(i).setStoptime(d);
                QueryWrapper<Inspect> inspectqueryWrapper = new QueryWrapper<>();
                queryWrapper = new QueryWrapper<>();
                String taskid = list.get(i).getInspectTaskId();
                inspectqueryWrapper.eq("inspect_task_id",taskid);
                Inspect inspect = inspectMapper.selectOne(inspectqueryWrapper);
                if(FieldUtils.isObjectNotEmpty(inspect)){
                    map = (Map<String, Object>) routeService.findDetail(inspect.getRouteId()).getObj();
                    planmap = (Map<String, Object>) planService.findById(inspect.getPlanId()).getObj();
                    list.get(i).setRouteInfo(map);
                    list.get(i).setPlanInfo(planmap);
                }
                for (int j = 0; j < personlist.size(); j++) {
                    if(personlist.get(j).get("username").equals(list.get(i).getInspectPerson())){
                        list.get(i).setInspectPersonName((String) personlist.get(j).get("name"));
                    }
                }
            }
        }
        result.put("onlinePeople",list.size());
        result.put("devicesInfo",list);
        return RespBean.ok("").setObj(result);
    }

    @Override
    public RespBean getMapOverviewInfo(String time,String unit,HttpServletRequest request) throws UnsupportedEncodingException {
        //unit 1：年 2 ：月
        //判断是按年查询还是按月查询
        HashMap<String,Object> result = new HashMap<>();
        List<HashMap<String, Object>> personlist = (List<HashMap<String, Object>>) urlUtils.selectUserByRole("901",request).getObj();
        List<HashMap<String,Object>> officelist = (List<HashMap<String, Object>>) routeService.findEnumMenu("用水管理所").getObj();
        List<Device> devicelist = new ArrayList<>();
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status","在线");
        devicelist = deviceMapper.selectList(queryWrapper);
        if(devicelist.size()>0){
            for (int j = 0; j < personlist.size();j++){
                personlist.get(j).put("status","不在线");
                for (int i = 0; i < devicelist.size(); i++) {
                    if(devicelist.get(i).getInspectPerson().equals(personlist.get(j).get("username")) && "在线".equals(devicelist.get(i).getStatus())){
                        personlist.get(j).put("status","在线");
                    }
                }
            }
        }
        //查询出所有巡查人该时间段的完成率，到位率，巡查里程
        List<HashMap<String, Object>> wcllist = new ArrayList<>();
        List<HashMap<String, Object>> officeinfolist = new ArrayList<>();
        List<HashMap<String, Object>> tracklist = new ArrayList<>();
        if("1".equals(unit)){
            wcllist =inspectMapper.selectwclAnddwlByyear(time);
            officeinfolist = inspectMapper.selectOfficeInfoByYear(time);
            if(personlist.size()>0){
                for (int i = 0; i < personlist.size(); i++) {
                    tracklist = new ArrayList<>();
                    if(wcllist.size()>0){
                        for (int j = 0; j < wcllist.size(); j++) {
                            if(personlist.get(i).get("username").equals(wcllist.get(j).get("inspect_person"))){
                                personlist.get(i).put("wcl",wcllist.get(j).get("wcl"));
                                personlist.get(i).put("dwl",wcllist.get(j).get("dwl"));
                            }else{
                                personlist.get(i).put("wcl",0);
                                personlist.get(i).put("dwl",0);
                            }
                        }
                    }else{
                        personlist.get(i).put("wcl",0);
                        personlist.get(i).put("dwl",0);
                    }
                    tracklist = inspectMapper.selectInspectMileByyear((String) personlist.get(i).get("username"),time);
                    personlist.get(i).put("miles",inspectMile(tracklist));
                }
            }
        }else{
            wcllist =inspectMapper.selectwclAnddwlBymonth(time);
            officeinfolist = inspectMapper.selectOfficeInfoByMonth(time);
            if(personlist.size()>0){
                for (int i = 0; i < personlist.size(); i++) {
                    personlist.get(i).put("wcl",0);
                    personlist.get(i).put("dwl",0);
                    tracklist = new ArrayList<>();
                    if(wcllist.size()>0){
                        for (int j = 0; j < wcllist.size(); j++) {
                            if(personlist.get(i).get("username").equals(wcllist.get(j).get("inspect_person"))){
                                personlist.get(i).put("wcl",wcllist.get(j).get("wcl"));
                                personlist.get(i).put("dwl",wcllist.get(j).get("dwl"));
                            }
                        }
                    }
                    tracklist = inspectMapper.selectInspectMileBymonth((String) personlist.get(i).get("username"),time);
                    personlist.get(i).put("miles",inspectMile(tracklist));
                }
            }
        }
        result.put("personlist",personlist);
        //获取各个管理所的完成率，到位率，覆盖率
        if(officelist.size()>0){
            for (int i = 0; i <officelist.size(); i++) {
                officelist.get(i).put("wcl",0);
                officelist.get(i).put("dwl",0);
                for (int j = 0; j < officeinfolist.size(); j++) {
                    if(officelist.get(i).get("code").equals(officeinfolist.get(j).get("water_management_office"))){
                        officelist.get(i).put("wcl",officeinfolist.get(j).get("wcl"));
                        officelist.get(i).put("dwl",officeinfolist.get(j).get("dwl"));
                    }
                }
            }
        }
        result.put("officelist",officelist);
        return RespBean.ok("").setObj(result);
    }

    @Override
    public RespBean selectUserByRole(String rol,HttpServletRequest request) throws UnsupportedEncodingException {
        return urlUtils.selectUserByRole(rol,request);
    }

    @Override
    public RespBean
    selectUserByRoleAndDept(String rol,String dept,HttpServletRequest request) throws UnsupportedEncodingException {
        return urlUtils.selectUserByRoleAndDept(rol,dept,request);
    }

    @Override
    public Boolean isDelete(Integer routeId, Integer planId) {
        List<Inspect> list = new ArrayList<>();
        QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
        if(FieldUtils.isObjectNotEmpty(routeId)){
            queryWrapper.eq("route_id",routeId);
            list = inspectMapper.selectList(queryWrapper);
        }
        if(FieldUtils.isObjectNotEmpty(planId)){
            queryWrapper.eq("plan_id",routeId);
            list = inspectMapper.selectList(queryWrapper);
        }
        if(list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                if(!("未派发".equals(list.get(i).getStatus()) || "已终止".equals(list.get(i).getStatus()))){
                    return false;
                }
            }
        }
        return true;
    }

    //计算每个人的巡查里程
    public double inspectMile(List<HashMap<String, Object>>  tracklist){
        double n = 0;
        if(tracklist.size()>0){
            for (int i = 0; i < tracklist.size()-1; i++) {
                n+= gps2m(Double.valueOf(tracklist.get(i).get("latitude").toString()),Double.valueOf(tracklist.get(i).get("longitude").toString()),Double.valueOf(tracklist.get(i+1).get("latitude").toString()),Double.valueOf(tracklist.get(i+1).get("longitude").toString()));
            }
        }
        return n/1000;
    }


    public double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    @Override
    public RespBean isModifiable(Integer routeId) {
        List<Inspect> alllist = new ArrayList<>();
        QueryWrapper<Inspect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("route_id",routeId);
        alllist = inspectMapper.selectList(queryWrapper);
        if(alllist.size()>0){
            return RespBean.ok("").setObj(false);
        }else{
            return RespBean.ok("").setObj(true);
        }
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

    //关阀分析
    public RespBean getcloseValues(String gid){
        //0：根据gid查询出联通的节点
        //1：根据gid获取s，t
        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
        Test test = testMapper.selectOne(queryWrapper.eq("gid",gid));
        //2：拿到s，t做深度遍历找到节点
        //3：判断每个节点是不是阀门，找出所有阀门
        //4：跟水厂做联通分析
        //5：关闭上游阀门
        return findAllValves(test);
    }

    public RespBean findAllValves(Test test){
        //阀门列表
        HashMap<String,Object> map = new HashMap<>();
        List<Integer> svids = new ArrayList<>();
        List<Integer> xvids = new ArrayList<>();
        List<Test> alllist = new ArrayList<>();
        HashSet<Test> oalllist = new HashSet<>();
        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
        alllist = testMapper.selectList(queryWrapper);
        //将自身置位无效
        for (int i = 0; i < alllist.size(); i++) {
            if(alllist.get(i).getGid().equals(test.getGid())){
                alllist.get(i).setStatus(0);
                alllist.get(i).setPid(0);
                oalllist.add(alllist.get(i));
            }else{
                oalllist.add(alllist.get(i));
            }
        }
        int s = test.getSource();
        int t = test.getTarget();
        test.setPid(test.getPid());
        //判断s、t是不是阀门
        if(FieldUtils.isObjectNotEmpty(test.getSvid())){
            //第一个阀门就是该 svid
            map.put("上游阀门",test.getSvid());
        }else{
            map.put("上游阀门",findValves(test.getGid(),test,oalllist,"0",0,svids));
        }
        if(FieldUtils.isObjectNotEmpty(test.getTvid())){
            //第一个阀门就是该 tvid
            map.put("下游阀门",test.getTvid());
        }else{
            map.put("下游阀门",findtValves(test.getGid(),test,oalllist,"0",0,xvids));
        }
        //分别拿s/t去深度遍历
        return RespBean.ok("").setObj(map);
    }
    //深度遍历
    public List<Integer> findValves(int ogid,Test ct,HashSet<Test> list,String flag,int n,List<Integer> vids){
        n+=1;
        Test pt = new Test();
        Test nt = new Test();
        if(String.valueOf(ct.getGid())!="null"){
            System.out.println("n:"+n);
            System.out.println("当前gid:"+ct.getGid());
            List<Test> clist =new ArrayList<>();
                int gid = ct.getGid();
                int node = ct.getTarget();
                int pid = ct.getPid();
                //塞入子节点
                if(list.size()>0){
                    Iterator <Test> s = list.iterator();
                    Test test = new Test();
                    while(s.hasNext()){
                        test = s.next();
                        if(test.getGid().equals(pid)){
                            pt = test;
                        }
                        if("1".equals(flag)){
                            if(test.getGid().equals(gid)){
                                clist = test.getList();
                            }
                        }else{
                            if(!test.getGid().equals(ogid) && !test.getGid().equals(gid) ){
                                if(test.getSource().equals(node)){
                                    clist.add(test);
                                }
                                if(test.getTarget().equals(node)){
                                    Integer s1 = test.getSource();
                                    Integer s2 = test.getTarget();
                                    Integer s3 = test.getSvid();
                                    Integer s4 = test.getTvid();
                                    test.setSource(s2);
                                    test.setTarget(s1);
                                    test.setSvid(s4);
                                    test.setTvid(s3);
                                    clist.add(test);
                                }
                            }
                        }
                    }
                    //flag = 0
                    if("0".equals(flag)){
                        Iterator <Test> s1 = list.iterator();
                        while (s1.hasNext()){
                            test = s1.next();
                            if(test.getGid().equals(gid)){
                                test.setList(clist);
                            }
                        }
                    }
                }
                int open =0;
                if(clist.size()>0){
                    for (int i = 0; i < clist.size(); i++) {
                        if(clist.get(i).getStatus().equals(1)){
                            open +=1;
                        }
                    }
                    for (int i = 0; i < clist.size(); i++) {
                        if(clist.get(i).getStatus().equals(1)){
                            clist.get(i).setStatus(0);
                            nt = clist.get(i);
                            break;
                        }else{
                            //说明是没有子节点可以走了，返回父节点递归
                            if(n==1){
                                System.out.println("没有子节点可以走了，返回父节点递归：" + ct.getGid());
                                findValves(ogid,ct, list, "1", n, vids);
                            }else{
                                System.out.println("没有子节点可以走了，返回父节点递归：" + pt.getGid());
                                findValves(ogid,pt, list, "1", n, vids);
                            }
                        }
                    }
                }else{
                    //说明是末梢点，返回父节点递归
                    System.out.println("是末梢点，返回父节点递归："+pt.getGid());
                    findValves(ogid,pt,list,"1",n,vids);
                }
                nt.setPid(gid);
                System.out.println(nt.getGid()+","+nt.getStatus()+","+nt.getSvid()+","+nt.getTvid());
                if(FieldUtils.isObjectNotEmpty(nt.getTvid())){
                    //该下一个节点是阀门，先判断自身还有没有路径，如果没有，则返回父节点递归
                    vids.add(nt.getTvid());
                    if(open>1){
                        findValves(ogid,ct,list,"1",n,vids);
                    }else {
                        findValves(ogid,pt,list,"1",n,vids);
                    }
                    /*if(n==1){
                        System.out.println("下一个节点是阀门:"+nt.getTvid()+"||"+nt.getSvid()+",返回父节点递归："+ct.getGid());
                        findValves(ogid,ct,list,"1",n,vids);
                    }else{
                        System.out.println("下一个节点是阀门:"+nt.getTvid()+"||"+nt.getSvid()+",返回父节点递归："+pt.getGid());
                        findValves(ogid,pt,list,"1",n,vids);
                    }*/
                }else{
                    //下一个节点不是阀门，到下一个节点继续递归
                    System.out.println("下一个节点不是阀门，到下一个节点："+nt.getGid());
                    findValves(ogid,nt,list,"0",n,vids);
                }
            }
        return vids;
    }

    //深度遍历
    public List<Integer> findtValves(int ogid,Test ct,HashSet<Test> list,String flag,int n,List<Integer> vids){
        n+=1;
        Test pt = new Test();
        Test nt = new Test();
        if(String.valueOf(ct.getGid())!="null"){
            System.out.println("n:"+n);
            System.out.println("当前gid:"+ct.getGid());
            List<Test> clist =new ArrayList<>();
            int gid = ct.getGid();
            int node = ct.getSource();
            int pid = ct.getPid();
            //塞入子节点
            if(list.size()>0){
                Iterator <Test> s = list.iterator();
                Test test = new Test();
                while(s.hasNext()){
                    test = s.next();
                    if(test.getGid().equals(pid)){
                        pt = test;
                    }
                    if("1".equals(flag)){
                        if(test.getGid().equals(gid)){
                            clist = test.getList();
                        }
                    }else{
                        if(!test.getGid().equals(ogid) && !test.getGid().equals(gid) ){
                            if(test.getTarget().equals(node)){
                                clist.add(test);
                            }
                            if(test.getSource().equals(node)){
                                Integer s1 = test.getSource();
                                Integer s2 = test.getTarget();
                                Integer s3 = test.getSvid();
                                Integer s4 = test.getTvid();
                                test.setSource(s2);
                                test.setTarget(s1);
                                test.setSvid(s4);
                                test.setTvid(s3);
                                clist.add(test);
                            }
                        }
                    }
                }
                //flag = 0
                if("0".equals(flag)){
                    Iterator <Test> s1 = list.iterator();
                    while (s1.hasNext()){
                        test = s1.next();
                        if(test.getGid().equals(gid)){
                            test.setList(clist);
                        }
                    }
                }
            }
            int open =0;
            if(clist.size()>0){
                for (int i = 0; i < clist.size(); i++) {
                    if(clist.get(i).getStatus().equals(1)){
                        open +=1;
                    }
                }
                for (int i = 0; i < clist.size(); i++) {
                    if(clist.get(i).getStatus().equals(1)){
                        clist.get(i).setStatus(0);
                        nt = clist.get(i);
                        break;
                    }else{
                        //说明是没有子节点可以走了，返回父节点递归
                        if(n==1){
                            System.out.println("没有子节点可以走了，返回父节点递归：" + ct.getGid());
                            findtValves(ogid,ct, list, "1", n, vids);
                        }else{
                            System.out.println("没有子节点可以走了，返回父节点递归：" + pt.getGid());
                            findtValves(ogid,pt, list, "1", n, vids);
                        }
                    }
                }
            }else{
                //说明是末梢点，返回父节点递归
                System.out.println("是末梢点，返回父节点递归：" + pt.getGid());
                findtValves(ogid,pt,list,"1",n,vids);
            }
            nt.setPid(gid);
            System.out.println("当前水管:"+ct.getGid()+","+ct.getStatus()+","+ct.getSvid()+","+ct.getTvid());
            System.out.println("下一个水管:"+nt.getGid()+","+nt.getStatus()+","+nt.getSvid()+","+nt.getTvid());
            if(FieldUtils.isObjectNotEmpty(nt.getSvid())){
                //该下一个节点是阀门，先判断自身还有没有路径，如果没有，则返回父节点递归
                vids.add(nt.getSvid());
                if(open>1){
                    System.out.println("节点是阀门，自身还有节点：" + ct.getGid());
                    findtValves(ogid,ct,list,"1",n,vids);
                }else {
                    System.out.println("节点是阀门，自身没有节点，返回父节点：" + ct.getGid());
                    findtValves(ogid,pt,list,"1",n,vids);
                }
                    /*if(n==1){
                        System.out.println("下一个节点是阀门:"+nt.getSvid()+"||"+nt.getTvid()+",返回父节点递归："+ct.getGid());
                        findtValves(ogid,ct,list,"1",n,vids);
                    }else{
                        System.out.println("下一个节点是阀门:"+nt.getSvid()+"||"+nt.getTvid()+",返回父节点递归："+pt.getGid());
                        findtValves(ogid,pt,list,"1",n,vids);
                    }*/
            }else{
                //下一个节点不是阀门，到下一个节点继续递归
                System.out.println("下一个节点不是阀门，到下一个节点："+nt.getGid());
                findtValves(ogid,nt,list,"0",n,vids);
            }
        }
        return vids;
    }
    public Boolean isvalve(){
        return true;
    }

    @Override
    public RespBean getTrackByTime(String inspect_person, String s1, String s2) {
        QueryWrapper<Track> query = new QueryWrapper<>();
        List<Track> result = new ArrayList<>();
        if(FieldUtils.isStringNotEmpty(inspect_person)){
            query.orderByAsc("gps_time");
            query.eq("inspect_person",inspect_person);
            query.apply("convert(varchar(20),gps_time,20) >= convert(varchar(20),'"+s1+"',20)");
            query.apply("convert(varchar(20),gps_time,20) <= convert(varchar(20),'"+s2+"',20)");
            result = trackMapper.selectList(query);
        }else{
            return RespBean.error("巡查人为空！");
        }
        return RespBean.ok("").setObj(result);
    }
}
