package com.sd.baseData.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sd.baseData.bean.SdFriendApplyInfoBean;
import com.sd.baseData.bean.SdFriendApplyReturnBean;
import com.sd.baseData.bean.SdFriendInfoBean;
import com.sd.baseData.common.RedisSequenceConstants;
import com.sd.baseData.dao.SdFriendDao;
import com.sd.common.redis.RedisManager;
import com.sd.common.time.SdDateUtil;
import com.sd.common.time.SdTime;

public class SdFriendService {
    
    private SdFriendDao sdFriendDao = null;
    private RedisManager redisManager = null;
    
    
    
    public RedisManager getRedisManager() {
        return redisManager;
    }
    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }
    public SdFriendDao getSdFriendDao() {
        return sdFriendDao;
    }
    public void setSdFriendDao(SdFriendDao sdFriendDao) {
        this.sdFriendDao = sdFriendDao;
    }


    /**
     * 好友列表
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public HashMap<String, Object> getFriendList(String userId, int pageNo, int pageSize){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        List<SdFriendInfoBean> friendList = sdFriendDao.getFriendList(userId, pageNo, pageSize);
        int totalCount = sdFriendDao.getFriendListCount(userId);
        returnMap.put("friendList", friendList);
        returnMap.put("totalCount", totalCount);
        
        return returnMap;
    }
    
    /**
     * 好友申请列表
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public HashMap<String, Object> getFriendApplyList(String userId, int pageNo, int pageSize){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        List<SdFriendApplyReturnBean> friendApplyList = sdFriendDao.getFriendApplyList(userId, pageNo, pageSize);
        int totalCount = sdFriendDao.getFriendApplyListCount(userId);
        returnMap.put("friendApplyList", friendApplyList);
        returnMap.put("totalCount", totalCount);
        
        return returnMap;
    }
    
    /**
     * 根据好友用户id删除好友
     * @param userId
     * @param friendId
     */
    public void deleteFriendById(String userId, String friendId) {
        sdFriendDao.deleteFriendById(userId, friendId);
    }
    
    /**
     * 发起好友申请
     * @param userId
     * @param friendId
     * @return 0：已经是好友 1：成功发起
     */
    public int sendFriendApply(String userId, String friendId) {
        
        List<SdFriendInfoBean> allFriendList = sdFriendDao.getAllFriendList(userId);
        //判断是否已经是好友
        boolean flag = true;
        for (int i = 0; i < allFriendList.size(); i++) {
            SdFriendInfoBean bean = allFriendList.get(i);
            if(bean.getFriendId().equals(friendId)) {
                flag = false;
                break;
            }
        }
        if(flag) {
            SdFriendApplyInfoBean bean = new SdFriendApplyInfoBean();
            String applyId = redisManager.getSequence(RedisSequenceConstants.SEQ_FRIEND_APPLY) + "";
            bean.setApplyId(applyId);
            bean.setSendUserId(userId);
            bean.setReceiveUserId(friendId);
            bean.setCrtime(new SdTime(SdDateUtil.getNowTime()));
            sdFriendDao.sendFriendApply(bean);
            return 1;
        }else {
            return 0;
        }
        
    }
    
    /**
     * 处理好友申请
     * @param applyId
     * @param result
     */
    @Transactional
    public void updateFriendApply(String applyId, String result, String userId, String friendId) {
        //好友申请表处理状态更新
        sdFriendDao.updateFriendApply(applyId, result);
        //添加好友对应关系
        if("1".equals(result)) {
            sdFriendDao.insertFriendRelationship(userId, friendId);
            sdFriendDao.insertFriendRelationship(friendId, userId);
        }
    }
}
