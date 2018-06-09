package com.sd.fm.bean;

import java.io.Serializable;

import com.sd.common.time.SdTime;

/**
 * 文件bean
 * @author HuDongyang
 *
 */
public class SdFileBean implements Serializable{
     /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String fileId = null;
    private String fileName = null;
    private String filePath = null;
    private String fileDirId = null;
    private String status = null;
    private String cruserId = null;
    private SdTime crtime = null;
    private String fileType = null;
    private String fileLable = null;
    private String fileSize = null;
    
    public String getFileSize() {
        return fileSize;
    }
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public String getFileLable() {
        return fileLable;
    }
    public void setFileLable(String fileLable) {
        this.fileLable = fileLable;
    }
    public String getFileId() {
        return fileId;
    }
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getFileDirId() {
        return fileDirId;
    }
    public void setFileDirId(String fileDirId) {
        this.fileDirId = fileDirId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCruserId() {
        return cruserId;
    }
    public void setCruserId(String cruserId) {
        this.cruserId = cruserId;
    }
    public SdTime getCrtime() {
        return crtime;
    }
    public void setCrtime(SdTime crtime) {
        this.crtime = crtime;
    }
    
}
