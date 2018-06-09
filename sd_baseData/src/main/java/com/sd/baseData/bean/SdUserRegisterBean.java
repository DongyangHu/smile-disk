package com.sd.baseData.bean;

import java.io.Serializable;

import com.sd.common.time.SdTime;

public class SdUserRegisterBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String registerId = null;
    private String userCode = null;
    private String userPassword = null;
    private String userEmail = null;
    private SdTime invalidTime = null;
    private String checkCode = null;
    
    public String getRegisterId() {
        return registerId;
    }
    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }
    public String getUserCode() {
        return userCode;
    }
    public void setUserCode(String userCode) {
        this.userCode = userCode;
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
    public SdTime getInvalidTime() {
        return invalidTime;
    }
    public void setInvalidTime(SdTime invalidTime) {
        this.invalidTime = invalidTime;
    }
    public String getCheckCode() {
        return checkCode;
    }
    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
    

}
