package com.yaobanTech.springcloud.domain;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


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
    public static Object ifObjectEmpty(Object obj) {
        String str = ObjectUtils.toString(obj, "");
        if(StringUtils.isNotBlank(str)){
            return obj;
        };
        return null;
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
    public static String ifStringEmpty(String str){
        if(str == null || "".equals(str)){
            return null;
        }else{
            return str;
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

    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String,Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = null;
            if(FieldUtils.isObjectNotEmpty(field.get(obj))){
                value = field.get(obj);
            }
            map.put(fieldName, value);
        }
        return map;
    }
}
