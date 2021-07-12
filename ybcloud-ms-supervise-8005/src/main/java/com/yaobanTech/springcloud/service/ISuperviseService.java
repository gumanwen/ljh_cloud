package com.yaobanTech.springcloud.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yaobanTech.springcloud.entity.ComSupervise;
import com.yaobanTech.springcloud.entity.utils.RespBean;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
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
public interface ISuperviseService extends IService<ComSupervise> {

    RespBean getComSupervise(long pageNo, long pageSize, Map<String, Object> params , HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException, ParseException;

    RespBean getConSupervise(long pageNo, long pageSize, Map<String, Object> params, HttpServletRequest request);

    RespBean getFacSupervise(long pageNo, long pageSize, Map<String, Object> params, HttpServletRequest request);

    RespBean getSpeSupervise(long pageNo, long pageSize, Map<String, Object> params, HttpServletRequest request);

    RespBean saveComSupervise(Map<String, Object> params);

    RespBean saveConSupervise(Map<String, Object> params);

    RespBean saveFacSupervise(Map<String, Object> params);

    RespBean saveSpeSupervise(Map<String, Object> params);

    RespBean updateComSupervise(Map<String, Object> params);

    RespBean updateConSupervise(Map<String, Object> params);

    RespBean updateFacSupervise(Map<String, Object> params);

    RespBean updateSpeSupervise(Map<String, Object> params);
}
