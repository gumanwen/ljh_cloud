package com.yaobanTech.springcloud.ToolUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {
    /**
     * date转日期字符串
     * @param date
     * @return String
     * */
    public static String DateToStr(Date date) {
        if (null == date) {
            date = new Date();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.format(date);
    }

    public static Date StrToDate(String str) throws ParseException {
        Date date = null;
        if (str != null) {
            //首先定义格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //按格式进行转换
            date = sdf.parse(str);
        }

        return date;

    }

}

