package com.sd.baseData.bean;

import java.io.Serializable;


public class SdFriendInfoBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String friendId = null;
    private String friendCode = null;
    private String friendName = null;
    
    
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
    

}
