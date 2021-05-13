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
    /**
     * 当前时间之前的时间与当前时间相差多少秒
     * @param startDate 当前时间之前的时间
     * @return
     */
    public static String calLastedTime(Date startDate) {
        long nowDate = new Date().getTime();
        long startDateTime = startDate.getTime();
        int ds = (int) ((nowDate - startDateTime) / 1000);
        String DateTimes = null;
        long days = ds / ( 60 * 60 * 24);
        long hours = (ds % ( 60 * 60 * 24)) / (60 * 60);
        long minutes = (ds % ( 60 * 60)) /60;
        long seconds = ds % 60;
        if(days>0){
        DateTimes= days + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒";
        }else if(hours>0){
        DateTimes=hours + "小时" + minutes + "分钟" + seconds + "秒";
        }else if(minutes>0){
        DateTimes=minutes + "分钟"
        + seconds + "秒";
        }else{
        DateTimes=seconds + "秒";
        }
        return DateTimes;
    }

}
