package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.entity.Inspect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yaobanTech.springcloud.entity.Test;
import com.yaobanTech.springcloud.entity.utils.RespBean;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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

    RespBean getPlanInspect(long pageNo,long pageSize,Map<String, Object> params ,HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException;

    RespBean getTempInspect(String type);

    RespBean getInspectDetailById(String inspect_task_id) throws IllegalAccessException;

    RespBean updateInspectDetailById(Map<String,Object> params);

    RespBean addTempTask(Integer routeId,String  routeName ,String inspector,String beginTime,String endTime) throws ParseException;

    RespBean addPlanTask(Integer routeId,String  routeName ,Integer planId,String planName) throws ParseException;

    RespBean getCheckInPoints(Integer routeId,String inspectTaskId);

    RespBean getPointDetail(Integer id);

    RespBean updatePoint(Map<String, Object> params);

    RespBean getAllInspectByPlanId(Integer planId);

    RespBean autoCreate(Map<String, Object> params) throws ParseException;

    RespBean send(Map<String, Object> params,HttpServletRequest request) throws UnsupportedEncodingException;

    RespBean send1(HttpServletRequest request);

    Object getCurrentUser(String token);

    RespBean findSignedList(Integer routeId, String inspectTaskId);

    RespBean stop(String inspectTaskId);

    RespBean delete(String inspectTaskId);

    List<Test> digui(Integer gid);

    RespBean bijiao(Integer gid);
}
