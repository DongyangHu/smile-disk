package com.sd.fm.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;


import com.sd.common.file.FileUploadUtil;

public class FileUploadService {
    
    private static String FILE_UPLOAD_BASE_PATH = null;
    
    static {
        Properties fileConfig = new Properties();
        InputStream inputStream = FileUploadService.class.getClassLoader().getResourceAsStream("fmConfig.properties");
        try {
            fileConfig.load(inputStream);
            FILE_UPLOAD_BASE_PATH = fileConfig.getProperty("file_upload_base_path");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 输入流保存文件
     * @param in
     * @param fileName
     * @return
     */
    private long saveFileByInputStream(InputStream in, String fileName, String filePath) {
        long saveFileSize = 0L;
        try {
            saveFileSize = FileUploadUtil.saveFile(in, fileName, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveFileSize;
    }

    /**
     * 保存文件
     * @param in
     * @param userCode
     * @param fileName
     * @return
     */
    public boolean saveFile(InputStream in, long fileSize, String userCode, String fileName) {
        //保存路径，用户code开头，后面跟上年月日
        Calendar now = Calendar.getInstance();
        String fileSavePath = FILE_UPLOAD_BASE_PATH + File.separator + userCode + File.separator + now.get(Calendar.YEAR) + File.separator + (now.get(Calendar.MONTH) + 1) + File.separator + now.get(Calendar.DAY_OF_MONTH);
        long saveSize = saveFileByInputStream(in, fileName, fileSavePath);
        if(saveSize == fileSize) {
            return true;
        }
        return false;
    }
    

    
}
