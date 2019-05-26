package com.linln.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static int differentDaysByMillisecond(Date date1, Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    public static String getToday(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(getToday());
    }
}
