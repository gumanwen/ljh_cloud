package com.yaobanTech.springcloud.entity.utils;

public class QyRespBean {
    private Integer code;
    private String message;
    private Object data;

    public static QyRespBean build() {
        return new QyRespBean();
    }

    public static QyRespBean ok(String message) {
        return new QyRespBean(200, message, null);
    }

    public static QyRespBean ok(String message, Object data) {
        return new QyRespBean(200, message, data);
    }

    public static QyRespBean error(String msg) {
        return new QyRespBean(500, msg, null);
    }

    public static QyRespBean error(String message, Object data) {
        return new QyRespBean(500, message, data);
    }

    public QyRespBean() {
    }

    private QyRespBean(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
