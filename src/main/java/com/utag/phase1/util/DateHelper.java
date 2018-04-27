package com.utag.phase1.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    public static String getDate(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = f.format(date);
        return s;
    }

    public static int getMonth(String date, int beg, int end){
        String monthStr = date.substring(beg, end);
        return Integer.parseInt(monthStr);
    }
}
