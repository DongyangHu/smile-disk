package com.sd.common.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SdDateUtil {
    public static  String getNowTime() {
        Date d= new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(d);
        return str;
    }
    
    public static  String getNowDate() {
        Date d= new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(d);
        return str;
    }
    
    
    public static String getNow(String format) {
        Date d= new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(d);
        return str;
    }
}
