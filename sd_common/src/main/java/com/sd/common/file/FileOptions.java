package com.sd.common.file;

import java.io.File;

/**
 * @see 文件操作
 * @version 0.0.1
 * @author HuDongyang
 * @blog http://blog.csdn.net/hu_dongyang
 * @date 2018.03.13
 */
public class FileOptions {
    
    /**
     * 删除文件
     * @param filePath 文件绝对路径
     * @return
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }
    
}
