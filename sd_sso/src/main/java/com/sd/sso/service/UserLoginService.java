package com.sd.sso.service;

import com.sd.sso.dao.UserLoginDao;
import com.sd.sso.filter.bean.SdUserSessionInfoBean;

public class UserLoginService {
    
    private UserLoginDao userLoginDao = null;

    public UserLoginDao getUserLoginDao() {
        return userLoginDao;
    }

    public void setUserLoginDao(UserLoginDao userLoginDao) {
        this.userLoginDao = userLoginDao;
    }
    
    /**
     * 用户登录
     * @param userCode
     * @param password
     * @return
     */
    public SdUserSessionInfoBean userLogin(String userCode, String password) {
        return userLoginDao.userLogin(userCode, password);
    }
    
    
}
