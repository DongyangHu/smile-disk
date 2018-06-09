package com.sd.baseData.common;

import java.io.Serializable;

public class RedisSequenceConstants implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 序列命名空间
     */
    public static final String SEQ_NAMESPACE = "seq";
    /**
     * 用户序列
     */
    public static final String SEQ_USER_INFO = "seq:user_info";
    /**
     * 用户注册序列
     */
    public static final String SEQ_USER_REGISTER = "seq:user_register";
    /**
     * 好友申请序列
     */
    public static final String SEQ_FRIEND_APPLY = "seq:friend_apply";
    /**
     * 消息序列
     */
    public static final String SEQ_NOTICE_INFO = "seq:notice_info";
    

}
