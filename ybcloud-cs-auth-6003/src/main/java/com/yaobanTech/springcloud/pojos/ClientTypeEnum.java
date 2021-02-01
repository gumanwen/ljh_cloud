package com.yaobanTech.springcloud.pojos;

/**
 * @author zhoufei
 * @version 1.0
 * @description 登录客户端类型枚举
 * @date 2019/3/17 17:16
 */
public enum ClientTypeEnum {
    /**
     * 登录类型
     */
    ANDROID, IOS, WEB;

    public static ClientTypeEnum getByName(String name) {
        for(ClientTypeEnum clientTypeEnum : values()){
            if(clientTypeEnum.name().equals(name)){
                return clientTypeEnum;
            }
        }
        return WEB;
    }

    public boolean equalsObj(String clientType){
        return this.name().equals(clientType);
    }
}
