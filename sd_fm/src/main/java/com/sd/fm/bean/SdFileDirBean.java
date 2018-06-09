package com.sd.fm.bean;

import java.io.Serializable;

import com.sd.common.time.SdTime;

/**
 * 文件夹bean
 * @author HuDongyang
 *
 */
public class SdFileDirBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String dirId = null;
    private String dirName = null;
    private String parentDirId = null;
    private String dirPathId = null;
    private String dirPathName = null;
    private String status = null;
    private String cruserId = null;
    private SdTime crtime = null;
    
    
    public String getDirId() {
        return dirId;
    }
    public void setDirId(String dirId) {
        this.dirId = dirId;
    }
    public String getDirName() {
        return dirName;
    }
    public void setDirName(String dirName) {
        this.dirName = dirName;
    }
    public String getParentDirId() {
        return parentDirId;
    }
    public void setParentDirId(String parentDirId) {
        this.parentDirId = parentDirId;
    }
    public String getDirPathId() {
        return dirPathId;
    }
    public void setDirPathId(String dirPathId) {
        this.dirPathId = dirPathId;
    }
    public String getDirPathName() {
        return dirPathName;
    }
    public void setDirPathName(String dirPathName) {
        this.dirPathName = dirPathName;
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
