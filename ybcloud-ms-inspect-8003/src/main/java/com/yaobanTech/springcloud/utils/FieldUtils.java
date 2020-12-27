package com.yaobanTech.springcloud.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;


public class FieldUtils {

    /**
     * 判断Object对象为空或空字符串
     * @param obj
     * @return
     */
    public static Boolean isObjectNotEmpty(Object obj) {
        String str = ObjectUtils.toString(obj, "");
        return StringUtils.isNotBlank(str);
    }
    /**
     * 判断字符串是不是为空
     *
     */
    public static Boolean isStringNotEmpty(String str){
        if(str == null || "".equals(str)){
            return false;
        }else{
            return true;
        }
    }
    /**
     *对象转字符串
     *注意 :当obj为null时，String.valueOf(Object)的值是字符串对象"null",而不是null！
     */
    public static String ObjectToString(Object obj){
        String str = String.valueOf(obj);
        return str;
    }
}
