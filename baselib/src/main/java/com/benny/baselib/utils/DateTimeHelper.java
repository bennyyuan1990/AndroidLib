package com.benny.baselib.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by yuanbb on 2017/10/9.
 */

public class DateTimeHelper {


    public static String convert2yyyyMMdd(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static String convert2yyyyMMddHHmm(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.format(date);
    }

    public static String convert2yyyyMMddHHmmss(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }


    /**
     * 转换之日常时间表达方式(1天前，2天前等)
     * @param time
     * @return
     */
    public static String convert2HumanTime(Date time) {
        String ftime = "";
        Calendar cal = Calendar.getInstance();        // 判断是否是同一天
        DateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = dateFormater2.format(cal.getTime());
        String paramDate = dateFormater2.format(time);

        if (curDate.equals(paramDate)) {

            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);

            if (hour == 0) {
                ftime = Math.max(
                    (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                    + "分钟前";
            } else {
                ftime = hour + "小时前";
            }

            return ftime;
        }
        long lt = time.getTime() / 86400000;

        long ct = cal.getTimeInMillis() / 86400000;

        int days = (int) (ct - lt);

        if (days == 0) {

            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);

            if (hour == 0) {
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                    + "分钟前";
            } else {
                ftime = hour + "小时前";
            }
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天 ";
        } else if (days > 2 && days < 31) {
            ftime = days + "天前";
        } else if (days >= 31 && days <= 2 * 31) {
            ftime = "1个月前";
        } else if (days > 2 * 31 && days <= 3 * 31) {
            ftime = "2个月前";
        } else if (days > 3 * 31 && days <= 4 * 31) {
            ftime = "3个月前";
        } else {
            ftime = dateFormater2.format(time);
        }
        return ftime;
    }


}
