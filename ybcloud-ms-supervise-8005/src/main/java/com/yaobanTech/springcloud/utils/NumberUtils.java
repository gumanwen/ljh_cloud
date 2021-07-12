package com.yaobanTech.springcloud.utils;

public class NumberUtils {
    public static  Integer numberTransform(String str){
        Integer number = 0;
        switch (str){
            case "一": number = 1; break;
            case "两": number = 2; break;
            case "三": number = 3; break;
            case "四": number = 4; break;
            case "五": number = 5; break;
            case "六": number = 6; break;
            case "七": number = 7; break;
            case "八": number = 8; break;
            case "九": number = 9; break;
        }
        return  number;
    }
}
