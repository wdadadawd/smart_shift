package com.lsu.utils;


import javax.xml.bind.Element;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author zt
 * @create 2023-07-12 15:44
 * 日期工具类
 */
public class DateUtils {
//    public static void main(String[] args) throws ParseException {
//        LocalTime localTime1 = LocalTime.parse("19:00");
//        LocalTime localTime2 = LocalTime.parse("19:04:53");
//
//        Duration duration = Duration.between(localTime1, localTime2);
//        System.out.println(duration.getSeconds());
//    }

    //获取2个时间差距多少秒
    public static long getTimeDif(Date date1,Date date2){
        String time1 = getTime(date1);
        String time2 = getTime(date2);

        LocalTime localTime1 = LocalTime.parse(time1);
        LocalTime localTime2 = LocalTime.parse(time2);

        Duration duration = Duration.between(localTime1, localTime2);

        return duration.getSeconds();
    }

    //获取指定日期为星期几
    public static String getToDay(Date date){
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return weeks[week_index];
    }

    //获取指定日期的时间
    public static String getTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    //获取指定日期的年月日
    public static String getDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    //获取指定日期的年月日
    public static String getYearAndMonth(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }

    //获取指定日期的年月日
    public static String getDateTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    //将指定Date类型加上或减去seconds分钟
    public static Date getOutSecondsTime(Date oldTime,Integer seconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldTime);
        calendar.add(Calendar.MINUTE, seconds);
        return calendar.getTime();
    }

    //将指定字符串时间加上或减去seconds分钟并返回对应的字符串形式
    public static String getOutSecondsTime(String oldTime,Integer seconds){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // 将时间字符串转换为LocalTime对象
        LocalTime time = LocalTime.parse(oldTime, formatter);
        // 增加seconds分钟
        LocalTime newTime;
        if (seconds>0)
            newTime = time.plusMinutes(seconds);
        else
            newTime = time.minusMinutes(-seconds);
        // 将新的时间转换回字符串
        return newTime.format(formatter);
    }

    //获取指定Date开始的一周日期
    public static List<Date> getWeekDay(Date date){
        //创建日期集合
        List<Date> dateList = new ArrayList<>();
        // 创建一个Calendar对象
        Calendar calendar = Calendar.getInstance();
        // 将Date对象设置到Calendar对象中
        calendar.setTime(date);
        dateList.add(calendar.getTime());
        for (int i = 1; i < 7; i++) {
            //添加天数
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date newDate = calendar.getTime();          //将日期转换回Date类型
            dateList.add(newDate);
        }
        return dateList;
    }
}
