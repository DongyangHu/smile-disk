package com.sd.fm.bean;

import java.io.Serializable;

import com.sd.common.time.SdTime;

public class SdShareFileBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String shareId = null;
    private String shareUserId = null;
    private String receiveUserId = null;
    private String shareFileId = null;
    private SdTime crtime = null;
    
    
    
    public String getShareId() {
        return shareId;
    }
    public void setShareId(String shareId) {
        this.shareId = shareId;
    }
    public String getShareUserId() {
        return shareUserId;
    }
    public void setShareUserId(String shareUserId) {
        this.shareUserId = shareUserId;
    }
    public String getReceiveUserId() {
        return receiveUserId;
    }
    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }
    public String getShareFileId() {
        return shareFileId;
    }
    public void setShareFileId(String shareFileId) {
        this.shareFileId = shareFileId;
    }
    public SdTime getCrtime() {
        return crtime;
    }
    public void setCrtime(SdTime crtime) {
        this.crtime = crtime;
    }
    
    
    
}
