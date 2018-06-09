package com.sd.baseData.bean;

import java.io.Serializable;

import com.sd.common.time.SdTime;

public class SdNoticeInfoBean implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    private String noticeId = null;
    private String noticeTitle = null;
    private String noticeContent = null;
    private String cruserId = null;
    private SdTime crtime = null;
    private String status = null;
    
    
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getNoticeId() {
        return noticeId;
    }
    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }
    public String getNoticeTitle() {
        return noticeTitle;
    }
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }
    public String getNoticeContent() {
        return noticeContent;
    }
    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
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
