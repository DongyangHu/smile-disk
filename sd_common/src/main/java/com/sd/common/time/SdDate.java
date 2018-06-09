package com.sd.common.time;


import java.io.Serializable;

public class SdDate implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String time = null;

    public static final String formatPattern = "yyyy-MM-dd";
    
    public SdDate() {
    }
    
    public SdDate(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
