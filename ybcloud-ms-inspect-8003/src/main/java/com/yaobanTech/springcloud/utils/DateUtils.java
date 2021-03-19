package com.yaobanTech.springcloud.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }
    public static int monthsBetween(Date smdate, Date bdate){
        Calendar  from  =  Calendar.getInstance();
        from.setTime(smdate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(bdate);
        //只要年月
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);

        int year = toYear  -  fromYear;
        int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth);
        return  month;
    }

    public static int yearsBetween(Date smdate, Date bdate){
        Calendar  from  =  Calendar.getInstance();
        from.setTime(smdate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(bdate);
        //只要年月
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);

        int year = toYear  -  fromYear;
        return  year;
    }
    public static Date daysAdd(Date date,int cycle) throws ParseException {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //calendar.add(calendar.YEAR, cycle);//把日期往后增加一年.整数往后推,负数往前移动
        //calendar.add(calendar.DAY_OF_MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
        calendar.add(calendar.DATE, cycle);//把日期往后增加一天.整数往后推,负数往前移动
        //calendar.add(calendar.WEEK_OF_MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
        date = calendar.getTime();
        return date;
    }
    public static Date monthsAdd(Date date,int cycle) throws ParseException {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //calendar.add(calendar.YEAR, cycle);//把日期往后增加一年.整数往后推,负数往前移动
        calendar.add(calendar.MONTH, cycle);//把日期往后增加一个月.整数往后推,负数往前移动
        //calendar.add(calendar.DATE, cycle);//把日期往后增加一天.整数往后推,负数往前移动
        //calendar.add(calendar.WEEK_OF_MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
        date = calendar.getTime();
        return date;
    }
    public static Date yearsAdd(Date date,int cycle) throws ParseException {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //calendar.add(calendar.YEAR, cycle);//把日期往后增加一年.整数往后推,负数往前移动
        calendar.add(calendar.YEAR, cycle);//把日期往后增加一个月.整数往后推,负数往前移动
        //calendar.add(calendar.DATE, cycle);//把日期往后增加一天.整数往后推,负数往前移动
        //calendar.add(calendar.WEEK_OF_MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
        date = calendar.getTime();
        return date;
    }
    public static Date dayAddAndSub(int currentDay,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(currentDay, day);
        Date startDate = calendar.getTime();
        return startDate;
    }

}
