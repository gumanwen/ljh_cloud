package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.entity.Inspect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yaobanTech.springcloud.entity.utils.RespBean;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lijh
 * @since 2020-12-18
 */
public interface IInspectService extends IService<Inspect> {

    RespBean getPlanInspect(long pageNo,long pageSize,Map<String, Object> params ,HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException;

    RespBean getTempInspect(String type);

    RespBean getInspectDetailById(String inspect_task_id) throws IllegalAccessException;

    RespBean updateInspectDetailById(Map<String,Object> params);

    RespBean addTempTask(String waterManagementOffice,Integer routeId,String  routeName ,String inspector,String beginTime,String endTime) throws ParseException;

    RespBean addPlanTask(String waterManagementOffice,Integer routeId,String  routeName ,Integer planId,String planName) throws ParseException;

    RespBean getCheckInPoints(Integer routeId,String inspectTaskId);

    RespBean getPointDetail(Integer id);

    RespBean updatePoint(Map<String, Object> params);

    RespBean getAllInspectByPlanId(Integer planId);

    RespBean autoCreate(Map<String, Object> params) throws ParseException;

    RespBean send(Map<String, Object> params,HttpServletRequest request) throws UnsupportedEncodingException;

    RespBean send1(HttpServletRequest request);

    Object getCurrentUser(String token);

    RespBean findSignedList(Integer routeId, String inspectTaskId);

    RespBean getTaskListByTime(Date begin_time1, Date begin_time2, Date dead_time1, Date dead_time2,String checkMan);

    RespBean stop(String inspectTaskId);

    RespBean delete(String inspectTaskId);

    RespBean inspectStatistics(String waterManagementOffice,String unit,String beginTime,String deadTime) throws ParseException;

    RespBean appInspectStatistics(HttpServletRequest request) throws UnsupportedEncodingException;

    RespBean uploadGPS(Map<String, Object> params);

    RespBean digui(Integer gid);

    RespBean bijiao(Integer gid);

    RespBean create();

    RespBean getcloseValues(String gid);

    RespBean isModifiable(Integer routeId);


}
