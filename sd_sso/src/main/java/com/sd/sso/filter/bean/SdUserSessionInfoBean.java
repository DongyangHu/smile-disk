package com.sd.sso.filter.bean;

import java.io.Serializable;

import com.sd.common.time.SdTime;


public class SdUserSessionInfoBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String userId = null;
    private String userCode = null;
    private String userNikename = null;
    private String userGender = null;
    private String userEmail = null;
    private String userStatus = null;
    private String userHeadimgUrl = null;
    private SdTime crtime = null;
    private String lang = null;
    private String roleId = null;
    
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserCode() {
        return userCode;
    }
    public SdTime getCrtime() {
        return crtime;
    }
    public void setCrtime(SdTime crtime) {
        this.crtime = crtime;
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

    public String getLang() {
        return lang;
    }
    public void setLang(String lang) {
        this.lang = lang;
    }
    
}
