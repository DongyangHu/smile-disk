package com.sd.baseData.bean;

import java.io.Serializable;

public class SdFriendApplyReturnBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    private String friendId = null;
    private String friendCode = null;
    private String friendName = null;
    private String result = null;
    private String applyId = null;
    
    
    
    public String getApplyId() {
        return applyId;
    }
    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
    public String getFriendId() {
        return friendId;
    }
    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
    public String getFriendCode() {
        return friendCode;
    }
    public void setFriendCode(String friendCode) {
        this.friendCode = friendCode;
    }
    public String getFriendName() {
        return friendName;
    }
    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    
    

}
