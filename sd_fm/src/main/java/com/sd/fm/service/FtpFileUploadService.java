package com.sd.fm.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sd.common.encryp.EncrypConstants;
import com.sd.common.encryp.EncryptAES;
import com.sd.common.file.FtpUtil;
import com.sd.common.redis.RedisManager;
import com.sd.common.time.SdDateUtil;
import com.sd.common.time.SdTime;
import com.sd.fm.bean.SdFileBean;
import com.sd.fm.common.RedisSequenceConstants;
import com.sd.fm.common.SdFmConstants;
import com.sd.fm.dao.FtpFileUploadDao;
import com.sd.fm.dao.SdFileInfoDao;

public class FtpFileUploadService {
    
    private static String FTP_UPLOAD_BASE_PATH = null;
    private static String FTP_HOST = null;
    private static String FTP_PORT = null;
    private static String FTP_USER = null;
    private static String FTP_PWD = null;
    public static Log logger = LogFactory.getLog(FtpFileUploadService.class);
    
    private RedisManager redisManager = null;
    private FtpFileUploadDao ftpFileUploadDao = null;
    private SdFileInfoDao sdFileInfoDao = null;
    
    public SdFileInfoDao getSdFileInfoDao() {
        return sdFileInfoDao;
    }
    public void setSdFileInfoDao(SdFileInfoDao sdFileInfoDao) {
        this.sdFileInfoDao = sdFileInfoDao;
    }
    public FtpFileUploadDao getFtpFileUploadDao() {
        return ftpFileUploadDao;
    }
    public void setFtpFileUploadDao(FtpFileUploadDao ftpFileUploadDao) {
        this.ftpFileUploadDao = ftpFileUploadDao;
    }
    public RedisManager getRedisManager() {
        return redisManager;
    }
    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    
    static {
        Properties fileConfig = new Properties();
        InputStream inputStream = FileUploadService.class.getClassLoader().getResourceAsStream("fmConfig.properties");
        try {
            fileConfig.load(inputStream);
            FTP_UPLOAD_BASE_PATH = fileConfig.getProperty("ftp_upload_base_path");
            FTP_HOST = fileConfig.getProperty("ftp_host");
            FTP_PORT = fileConfig.getProperty("ftp_port");
            FTP_USER = EncryptAES.decryptAES(fileConfig.getProperty("ftp_user"), EncrypConstants.AES_DECRYPT_KEY);
            FTP_PWD = EncryptAES.decryptAES(fileConfig.getProperty("ftp_pwd"), EncrypConstants.AES_DECRYPT_KEY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取文件类型
     * @param fileName
     * @return
     */
    private String getFileType(String fileName) {
        String extension = upper2Low(FilenameUtils.getExtension(fileName));
        
        if(SdFmConstants.VEDIO_TYPE.contains(extension)) {
            return SdFmConstants.VEDIO_TYPE_SIGN;
        }
        if(SdFmConstants.MUSIC_TYPE.contains(extension)) {
            return SdFmConstants.MUSIC_TYPE_SIGN;
        }
        if(SdFmConstants.DOC_TYPE.contains(extension)) {
            return SdFmConstants.DOC_TYPE_SIGN;
        }
        if(SdFmConstants.IMG_TYPE.contains(extension)) {
            return SdFmConstants.IMG_TYPE_SIGN;
        }
        
        return SdFmConstants.OTHER_TYPE_SIGN;
    }
    
    /**
     * 将大写字母转换为小写
     * @param sourceStr
     * @return
     */
    private String upper2Low(String sourceStr) {
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < sourceStr.length(); i++) {
            int charNum = sourceStr.charAt(i);
            if(charNum >= 65 && charNum <= 90) {
                charNum += 32;
                buffer.append((char)charNum);
            }else {
                buffer.append((char)charNum);
            }
        }
        return buffer.toString();
    }
    
    /**
     * ftp文件上传
     * @param userCode
     * @param fileIn
     * @param fileName
     * @return
     */
    public boolean uploadFile(String userCode, String userId, InputStream fileIn, String dirId, String fileName, String saveFileName, String fileSize) {
      //保存路径，用户code开头，后面跟上年月日
        boolean flag = false;
        Calendar now = Calendar.getInstance();
        String filePath = userCode + "/" + now.get(Calendar.YEAR) + "/" + (now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.DAY_OF_MONTH);
        try {
            logger.info("ftp上传开始...");
            flag = FtpUtil.ftpUpload(FTP_HOST, FTP_PORT, FTP_USER, FTP_PWD, fileIn, FTP_UPLOAD_BASE_PATH, filePath, saveFileName);
            logger.info("ftp上传: " + flag); 
            if(flag) {
                logger.info("保存文件:" + saveFileName + " ,路径" + filePath + "/" + saveFileName);
                SdFileBean bean = new SdFileBean();
                long fileId = redisManager.getSequence(RedisSequenceConstants.SEQ_FILE_INFO);
                
                bean.setFileId(fileId + "");
                bean.setFileName(saveFileName);
                bean.setFileDirId(dirId);
                bean.setCruserId(userId);
                bean.setCrtime(new SdTime(SdDateUtil.getNowTime()));
                bean.setFilePath(filePath);
                bean.setFileLable(fileName);
                bean.setFileType(getFileType(fileName));
                bean.setStatus("1");
                bean.setFileSize(fileSize);
                ftpFileUploadDao.uploadFile(bean);
            }
        } catch (IOException e) {
            logger.error("ftpUploadFile", e);
        }
        return flag;
    }
    
    /**
     * 文件下载，获得文件输入流
     * @param fileId
     * @return
     */
    public HashMap<String, Object> downloadFile(String fileId) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        try {
            SdFileBean fileBean = ftpFileUploadDao.getFileInfoById(fileId);
            InputStream in = FtpUtil.ftpDownload(FTP_HOST, FTP_PORT, FTP_USER, FTP_PWD, FTP_UPLOAD_BASE_PATH, fileBean.getFilePath(), fileBean.getFileName());
            returnMap.put("in", in);
            returnMap.put("fileName", fileBean.getFileLable());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnMap;
    }
    
    /**
     * 根据文件id删除文件
     * @param fileId
     * @return
     */
    public boolean deleteFileByIdPhysical(String fileId) {
        boolean returnFlag = false;
        boolean deleteFlag = ftpDeleteFile(fileId);
        if(deleteFlag) {
            int deleteCount = sdFileInfoDao.deleteFileByIdPhysical(fileId);
            if(deleteCount > 0) {
                returnFlag = true;
            }
        }
        return returnFlag;
    }
    
    
    /**
     * ftp删除文件
     * @param fileId
     * @return
     */
    private boolean ftpDeleteFile(String fileId) {
        boolean deleteFlag = false;
        SdFileBean file = ftpFileUploadDao.getFileInfoById(fileId);
        try {
            String fileName = file.getFileName();
            String filePath = file.getFilePath();
            deleteFlag = FtpUtil.ftpDeleteFile(FTP_HOST, FTP_PORT, FTP_USER, FTP_PWD, FTP_UPLOAD_BASE_PATH, filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleteFlag;
    }
    
}
