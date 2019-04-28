package com.hc.calling.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String GetNowDate(String format) {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        temp_str = sdf.format(dt);

        return temp_str;
    }

    public static long getNextEveningDuration(Boolean morning) {
        int hour; //打卡时间
        Date date = new Date(); //当前时间
        Calendar calendar = Calendar.getInstance();
        System.out.println("now:" + calendar.get(Calendar.HOUR));


        // if now is morning then next start time is afternoon
        if (morning) {
            hour = 17;
        } else {
            hour = 8;
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hour, 5);

        calendar.set(Calendar.DATE, hour == 8 ? calendar.get(Calendar.DATE) + 1 : calendar.get(Calendar.DATE));
        System.out.println("updated time:" + calendar.getTime());
        System.out.println("duration:" + (calendar.getTimeInMillis() - date.getTime()));

        return calendar.getTimeInMillis() - date.getTime();
    }

}
