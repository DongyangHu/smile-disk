package com.sd.baseData.dao;

import java.util.HashMap;
import java.util.List;

import com.sd.baseData.bean.SdUserInfoBean;
import com.sd.baseData.bean.SdUserRegisterBean;

public class UserInfoDao extends BaseDao{
    
    
    /**
     * 根据userCode获取该code对应的用户数
     * @param userCode
     * @return
     */
    public int checkUserCodeUnique (String userCode) {
        int userCount = this.getSqlSession().selectOne("userInfo.checkUserCodeUnique", userCode);
        int registerCount = this.getSqlSession().selectOne("userInfo.checkUserCodeUniqueRegister", userCode);
        return userCount + registerCount;
    }
    
    /**
     * 所有用户信息
     * @return
     */
    public List<SdUserInfoBean> getUserInfoList(int pageNo, int pageSize){
        HashMap<String, Object> pageQueryParams = getPageQueryParams(pageNo, pageSize);
        return getSqlSession().selectList("userInfo.getUserInfoList", pageQueryParams);
    }
    public List<SdUserInfoBean> getUserInfoList(){
        return getSqlSession().selectList("userInfo.getAllUserInfoList");
    }
    /**
     * 所有用户条数
     * @return
     */
    public int getUserInfoListCount(){
        return getSqlSession().selectOne("userInfo.getUserInfoListCount");
    }
    
    /**
     * 用户注册
     * @param bean
     */
    public int saveUserRegisterInfo(SdUserRegisterBean bean) {
        return this.getSqlSession().insert("userInfo.saveUserRegisterInfo", bean);
    }
    
    /**
     * 根据参数查询注册信息
     * @param registerId
     * @param userCode
     * @param userEmail
     * @param checkCode
     * @return
     */
    public SdUserRegisterBean getUserRegisterInfoByParam(String registerId, String userCode, String userEmail, String checkCode) {
        HashMap<String, String> params = new HashMap<String, String>();
        if(null != registerId && !"".equals(registerId)) {
            params.put("registerId", registerId);
        }
        if(null != userCode && !"".equals(userCode)) {
            params.put("userCode", userCode);
        }
        if(null != userEmail && !"".equals(userEmail)) {
            params.put("userEmail", userEmail);
        }
        if(null != checkCode && !"".equals(checkCode)) {
            params.put("checkCode", checkCode);
        }
        
        return this.getSqlSession().selectOne("userInfo.getUserRegisterInfoByParam",params);
    }
    
    /**
     * 根据注册信息Id删除注册信息
     */
    public void deleteUserRegisterInfoById(String registerId) {
        this.getSqlSession().delete("userInfo.deleteUserRegisterInfoById", registerId);
    }
    
    /**
     * 保存用户信息
     * @param bean
     */
    public void saveUserInfo(SdUserInfoBean bean) {
        this.getSqlSession().insert("userInfo.saveUserInfo", bean);
    }
    
    /**
     * 根据userCode或者userId获取单个用户信息
     * @param userCode
     * @param userId
     * @return
     */
    public SdUserInfoBean getUserInfoByParams(String userCode, String userId) {
        HashMap<String, String> params = new HashMap<String, String>();
        
        if(null != userCode && !"".equals(userCode)) {
            params.put("userCode", userCode);
        }
        if(null != userId && !"".equals(userId)) {
            params.put("userId", userId);
        }
        
        return this.getSqlSession().selectOne("userInfo.getUserInfoByParams", params);
    }
    
    /**
     * 修改用户信息
     * @param bean
     */
    public void updateUserInfo(String userCode, String userPwd) {
        HashMap<String, String> params = new HashMap<String, String>();
        
        if(null != userCode && !"".equals(userCode)) {
            params.put("userCode", userCode);
        }
        if(null != userPwd && !"".equals(userPwd)) {
            params.put("userPwd", userPwd);
        }
        
        this.getSqlSession().update("userInfo.updateUserInfo", params);
    }

    /**
     * 更改用户状态
     * @param userId
     * @param status
     */
    public void changeUserStatus(String userId, String status) {
        HashMap<String, String> params = new HashMap<String, String>();
        
        if(null != userId && !"".equals(userId)) {
            params.put("userId", userId);
        }
        if(null != status && !"".equals(status)) {
            params.put("status", status);
        }
        
        this.getSqlSession().update("userInfo.changeUserStatus", params);
    }
    
}
