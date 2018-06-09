package com.sd.baseData.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimedTaskService {
    
    
    public void showTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间:" + df.format(new Date()));
    }
}
