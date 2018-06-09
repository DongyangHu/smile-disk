package com.sd.base.bean;

import java.io.Serializable;

public class TestUserBean implements Serializable {

    /**
     * 测试bean
     */
    private static final long serialVersionUID = 1L;
    
    private String userId;
    private String userCode;
    private String userName;
    private String userPassword;
    
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
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
