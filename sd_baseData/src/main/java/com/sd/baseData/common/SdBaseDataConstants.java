package com.sd.baseData.common;

import java.io.Serializable;

public class SdBaseDataConstants implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    
    /**
     * 默认语言
     */
    public static final String DEFAULT_LANGYAGE_TYPE = "zh_CN";
    /**
     * 系统主页
     */
    public static final String SD_WEB_INDEX = "http://disk.yiyang66.top";
    /**
     * 邮件主题类别
     */
    //默认主题
    public static final String MAIL_SUBJECT_FEDAULT = "mailSubjectDefault";
    //注册主题
    public static final String MAIL_SUBJECT_REGISTER = "mailSubjectRegister";
    //验证码主题
    public static final String MAIL_SUBJECT_CODE = "mailSubjectCode";

    /**
     * 发送邮箱验证码时，session存邮件地址
     */
    public static final String USER_EMAIL_ADDR = "userEmail";
    /**
     * 验证码存session
     */
    public static final String EMAIL_CHECK_CODE = "checkCode";
    /**
     * 用户默认角色
     */
    public static final String DEFAULT_USER_ROLE = "0";
    /**
     * 用户列表页码
     */
    public static final String DEFAULT_PAGE_NO = "1";
    /**
     * 用户列表每页条数
     */
    public static final String DEFAULT_PAGE_SIZE = "9";
    /**
     * 默认密码
     */
    public static final String DEFAULT_USER_PWD = "123456";
    
}
