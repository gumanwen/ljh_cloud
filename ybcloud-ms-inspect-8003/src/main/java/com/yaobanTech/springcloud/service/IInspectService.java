package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.entity.Inspect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yaobanTech.springcloud.entity.utils.RespBean;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
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

    RespBean getPlanInspect(long pageNo,long pageSize,Map<String, Object> params ,HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException, ParseException;

    RespBean getTempInspect(String type);

    RespBean getInspectDetailById(String inspect_task_id,HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException, ParseException;

    RespBean getFeignInspectDetailById(List<String> taskIds);

    RespBean updateInspectDetailById(Map<String,Object> params);

    RespBean addTempTask(String waterManagementOffice,Integer routeId,String  routeName ,String inspector,String beginTime,String endTime) throws ParseException;

    RespBean addPlanTask(String waterManagementOffice,Integer routeId,String  routeName ,Integer planId,String planName,HttpServletRequest request) throws ParseException, UnsupportedEncodingException;

    RespBean getCheckInPoints(Integer routeId,String inspectTaskId);

    RespBean getPointDetail(Integer id);

    RespBean updatePoint(Map<String, Object> params);

    RespBean getAllInspectByPlanId(Integer planId);

    RespBean autoCreate(Map<String, Object> params,HttpServletRequest request) throws ParseException, UnsupportedEncodingException;

    RespBean send(Map<String, Object> params,HttpServletRequest request) throws UnsupportedEncodingException;

    RespBean send1(HttpServletRequest request);

    Object getCurrentUser(String token);

    RespBean findSignedList(Integer routeId, String inspectTaskId);

    RespBean getTaskListByTime(Date begin_time1, Date begin_time2, Date dead_time1, Date dead_time2,String checkMan,HttpServletRequest request) throws UnsupportedEncodingException;

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

    RespBean uploadDeviceGPS(Map<String, Object> params);

    RespBean getDeviceInfo(HttpServletRequest request) throws ParseException, UnsupportedEncodingException;

    RespBean getMapOverviewInfo(String t,String unit,HttpServletRequest request) throws UnsupportedEncodingException;

    RespBean selectUserByRole(String role,HttpServletRequest request) throws UnsupportedEncodingException;

    RespBean selectUserByRoleAndDept(String role, String waterManagementOffice, HttpServletRequest request) throws UnsupportedEncodingException;

    Boolean isDelete(Integer routeId, Integer planId);

    RespBean batchSend(Map<String, Object> params, HttpServletRequest request) throws UnsupportedEncodingException;

    RespBean getTrackByTime(String inspect_person, String s1, String s2);
}
