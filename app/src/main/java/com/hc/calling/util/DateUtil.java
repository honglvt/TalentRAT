package com.hc.calling.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String GetNowDate(String format) {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        temp_str = sdf.format(dt);

        return temp_str;
    }

}
