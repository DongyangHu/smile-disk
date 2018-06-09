package com.sd.baseData.bean;

import java.io.Serializable;

import com.sd.common.time.SdTime;

public class SdFriendApplyInfoBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String applyId = null;
    private String sendUserId = null;
    private String receiveUserId = null;
    private SdTime crtime = null;
    private SdTime handleTime = null;
    private String result = null;
     
     
    public String getApplyId() {
        return applyId;
    }
    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
    public String getSendUserId() {
        return sendUserId;
    }
    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }
    public String getReceiveUserId() {
        return receiveUserId;
    }
    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }
    public SdTime getCrtime() {
        return crtime;
    }
    public void setCrtime(SdTime crtime) {
        this.crtime = crtime;
    }
    public SdTime getHandleTime() {
        return handleTime;
    }
    public void setHandleTime(SdTime handleTime) {
        this.handleTime = handleTime;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }  
    

}
