package com.sd.sso.dao;

import java.util.HashMap;

import com.sd.sso.filter.bean.SdUserSessionInfoBean;

public class UserLoginDao extends BaseDao{
    
    /**
     * 用户登录
     * @param userCode
     * @param password
     * @return
     */
    public SdUserSessionInfoBean userLogin(String userCode, String password) {
        HashMap<String, Object> params = new HashMap<String,Object>();
        if(null != userCode && !"".equals(userCode)) {
            params.put("userCode", userCode);
        }
        if(null != password && !"".equals(password)) {
            params.put("password", password);
        }
        return this.getSqlSession().selectOne("userLogin.getUserLoginInfo", params);
    }
}
