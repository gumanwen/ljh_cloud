package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.entity.Inspect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yaobanTech.springcloud.entity.utils.RespBean;

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

    RespBean getPlanInspect(String type);

    RespBean getTempInspect(String type);

    RespBean getInspectDetailById(String inspect_task_id);

    RespBean updateInspectDetailById(String inspect_task_id);

    RespBean addTempTask(Map<String, Object> params);

    RespBean getCheckInPoints(Integer plan_id);

    RespBean getPointDetail(Integer id);

    RespBean updatePoint(Map<String, Object> params);

}
