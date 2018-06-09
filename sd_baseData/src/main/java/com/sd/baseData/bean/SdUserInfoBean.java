package com.sd.baseData.bean;

import java.io.Serializable;

import com.sd.common.time.SdTime;

public class SdUserInfoBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String userId = null;
    private String userCode = null;
    private String userNikename = null;
    private String userGender = null;
    private String userPassword = null;
    private String userEmail = null;
    private String userStatus = null;
    private String userHeadimgUrl = null;
    private SdTime crtime = null;
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserCode() {
        return userCode;
    }
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    public String getUserNikename() {
        return userNikename;
    }
    public void setUserNikename(String userNikename) {
        this.userNikename = userNikename;
    }
    public String getUserGender() {
        return userGender;
    }
    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserStatus() {
        return userStatus;
    }
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
    public String getUserHeadimgUrl() {
        return userHeadimgUrl;
    }
    public void setUserHeadimgUrl(String userHeadimgUrl) {
        this.userHeadimgUrl = userHeadimgUrl;
    }
    public SdTime getCrtime() {
        return crtime;
    }
    public void setCrtime(SdTime crtime) {
        this.crtime = crtime;
    }
    
}
