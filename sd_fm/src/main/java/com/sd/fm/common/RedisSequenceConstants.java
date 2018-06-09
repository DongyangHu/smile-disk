package com.sd.fm.common;

import java.io.Serializable;

public class RedisSequenceConstants implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 文件序列
     */
    public static final String SEQ_FILE_INFO = "seq:file_info";
    /**
     * 文件夹序列
     */
    public static final String SEQ_FILE_DIR_INFO = "seq:file_dir_info";
    /**
     * 分享序列
     */
    public static final String SEQ_SHARE_ID = "seq:share_id";

}
