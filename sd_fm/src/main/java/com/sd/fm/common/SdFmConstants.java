package com.sd.fm.common;

import java.io.Serializable;

public class SdFmConstants implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 默认语言
     */
    public static final String DEFAULT_LANGYAGE_TYPE = "zh_CN";
    
    /**
     * 文件后缀列表
     */
    public static final String VEDIO_TYPE = "mp4,avi,flv,3gp,rmvb,wmv";
    public static final String MUSIC_TYPE = "mp3,wma,aac";
    public static final String DOC_TYPE = "doc,docx,ppt,pptx,xls,xlsx,pdf,txt";
    public static final String IMG_TYPE = "jpg,jpge,bmp,png,gif";
    
    /**
     * 文件类别标志 1：视频，2：音乐，3：文档，4：图片，0：其他
     */
    public static final String VEDIO_TYPE_SIGN = "1";
    public static final String MUSIC_TYPE_SIGN = "2";
    public static final String DOC_TYPE_SIGN = "3";
    public static final String IMG_TYPE_SIGN = "4";
    public static final String OTHER_TYPE_SIGN = "0";
    
    /**
     * 默认分页参数
     */
    public static final String DEFAULT_PAGE_NO = "1";
    public static final String DEFAULT_PAGE_SIZE = "10";
}
