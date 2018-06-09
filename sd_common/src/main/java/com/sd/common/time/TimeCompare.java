package com.sd.common.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCompare {
    
    public static final String FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 判断时间在现在之前
     * @param time 时间:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static boolean isBeforeNow(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_PATTERN);
            Date date = sdf.parse(time);
            if(date.before(new Date())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
