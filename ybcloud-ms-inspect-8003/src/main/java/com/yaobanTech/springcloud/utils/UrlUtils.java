package com.yaobanTech.springcloud.utils;

import com.alibaba.fastjson.JSON;
import com.yaobanTech.springcloud.entity.LoginUser;
import com.yaobanTech.springcloud.entity.utils.QyRespBean;
import com.yaobanTech.springcloud.entity.utils.RespBean;
import com.yaobanTech.springcloud.service.feign.AuthService;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

@Service
public class UrlUtils {

    @Value("${qyserver.ipAdd}")
    private String qyIP;

    @Autowired
    private AuthService authService;

    public LoginUser getAll(HttpServletRequest request) throws UnsupportedEncodingException {
        Integer ttype = null;
        //String tokenT = request.getHeader("TW-AUTH-HEADER");
        String tokenT = request.getHeader("TW-Authorization");
        String token = null;
        LoginUser loginUser = null;
        if(FieldUtils.isStringNotEmpty(tokenT)){
            token = URLDecoder.decode(tokenT,"UTF-8");
            ttype = 2;
        }else{
            ttype = 1;
            String header = request.getHeader("Authorization");
            token =  StringUtils.substringAfter(header, "Bearer ");
        }
        RespBean feignRespBean = authService.getAll(token,ttype);
        loginUser = JSON.parseObject(JSON.toJSONString(feignRespBean.getObj()),LoginUser.class);
        return  loginUser;
    }

    public String getToken(HttpServletRequest request) throws UnsupportedEncodingException {
        Integer ttype = null;
        //String tokenT = request.getHeader("TW-AUTH-HEADER");
        String tokenT = request.getHeader("TW-Authorization");
        String token = null;
        LoginUser loginUser = null;
        if(FieldUtils.isStringNotEmpty(tokenT)){
            token = URLDecoder.decode(tokenT,"UTF-8");
            ttype = 2;
        }else{
            ttype = 1;
            String header = request.getHeader("Authorization");
            token =  StringUtils.substringAfter(header, "Bearer ");
        }
        return  token;
    }

    public Integer getType(HttpServletRequest request) throws UnsupportedEncodingException {
        Integer ttype = null;
        //String tokenT = request.getHeader("TW-AUTH-HEADER");
        String tokenT = request.getHeader("TW-Authorization");
        String token = null;
        LoginUser loginUser = null;
        if(FieldUtils.isStringNotEmpty(tokenT)){
            token = URLDecoder.decode(tokenT,"UTF-8");
            ttype = 2;
        }else{
            ttype = 1;
            String header = request.getHeader("Authorization");
            token =  StringUtils.substringAfter(header, "Bearer ");
        }
        return  ttype;
    }

    public RespBean selectUserByRole(String role,HttpServletRequest request) throws UnsupportedEncodingException {
        Integer ttype = null;
        //String tokenT = request.getHeader("TW-AUTH-HEADER");
        String tokenT = request.getHeader("TW-Authorization");
        String token = null;
        LoginUser loginUser = null;
        if(FieldUtils.isStringNotEmpty(tokenT)){
            token = URLDecoder.decode(tokenT,"UTF-8");
            ttype = 2;
            //默认传多少就是多少，如果传bzy转化成64
            if("ROLE_BZY".equals(role)){
                role ="64";
            }
        }else{
            ttype = 1;
            String header = request.getHeader("Authorization");
            token =  StringUtils.substringAfter(header, "Bearer ");
            role = "ROLE_BZY";
        }
        RespBean feignRespBean = authService.selectUserByRole(role,ttype,token);
        List<HashMap<String, Object>> result = (List<HashMap<String, Object>>) feignRespBean.getObj();
        return  RespBean.ok("").setObj(result);
    }

    public RespBean selectUserByRoleAndDept(String role,String dept,HttpServletRequest request) throws UnsupportedEncodingException {
        Integer ttype = null;
        //String tokenT = request.getHeader("TW-AUTH-HEADER");
        String tokenT = request.getHeader("TW-Authorization");
        String token = null;
        LoginUser loginUser = null;
        if(FieldUtils.isStringNotEmpty(tokenT)){
            token = URLDecoder.decode(tokenT,"UTF-8");
            ttype = 2;
        }else{
            ttype = 1;
            String header = request.getHeader("Authorization");
            token =  StringUtils.substringAfter(header, "Bearer ");
        }
        RespBean feignRespBean = authService.selectUserByRoleAndDept(role,dept,ttype,token);
        List<HashMap<String, Object>> result = (List<HashMap<String, Object>>) feignRespBean.getObj();
        return  RespBean.ok("").setObj(result);
    }
}
