package com.sd.common.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class SdI18n {
    /**
     * 默认资源文件名称
     */
    private final static String DEFAULT_RES_NAME = "message";
    
    /**
     * 获取单个key的 value
     * @param key
     * @param lang
     * @return
     */
    public static String getMessage(String key, String lang) {
        String[] split = lang.split("_");
        String language = split[0];
        String contry = split[1];
        Locale locale = new Locale(language,contry);
        ResourceBundle rb = ResourceBundle.getBundle(DEFAULT_RES_NAME, locale);        
        return rb.getString(key);
    }
}
