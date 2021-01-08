package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.entity.Inspect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yaobanTech.springcloud.entity.utils.RespBean;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
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

    RespBean getPlanInspect(String type,HttpServletRequest request) throws IllegalAccessException;

    RespBean getTempInspect(String type);

    RespBean getInspectDetailById(String inspect_task_id) throws IllegalAccessException;

    RespBean updateInspectDetailById(Map<String,Object> params);

    RespBean addTempTask(Integer routeId,String  routeName ,String inspector,String beginTime,String endTime);

    RespBean addPlanTask(Integer routeId,String  routeName ,Integer planId,String planName) throws ParseException;

    RespBean getCheckInPoints(Integer routeId,String inspectTaskId);

    RespBean getPointDetail(Integer id);

    RespBean updatePoint(Map<String, Object> params);

    RespBean getAllInspectByPlanId(Integer planId);

    RespBean autoCreate(Map<String, Object> params) throws ParseException;

    RespBean send(Map<String, Object> params,HttpServletRequest request);

    Object getCurrentUser(String token);

    RespBean findSignedList(Integer routeId, String inspectTaskId);
}
