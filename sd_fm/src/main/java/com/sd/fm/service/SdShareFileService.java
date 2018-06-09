package com.sd.fm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sd.common.redis.RedisManager;
import com.sd.common.time.SdDateUtil;
import com.sd.common.time.SdTime;
import com.sd.fm.bean.SdShareFileBean;
import com.sd.fm.bean.SdShareFileBean2;
import com.sd.fm.common.RedisSequenceConstants;
import com.sd.fm.dao.SdShareFileDao;

public class SdShareFileService {
    
    private SdShareFileDao sdShareFileDao = null;
    private RedisManager redisManager = null;
    
    
    public SdShareFileDao getSdShareFileDao() {
        return sdShareFileDao;
    }
    public void setSdShareFileDao(SdShareFileDao sdShareFileDao) {
        this.sdShareFileDao = sdShareFileDao;
    }
    public RedisManager getRedisManager() {
        return redisManager;
    }
    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }
    
    /**
     * 分享文件
     * @param userId
     * @param receiveUserId
     * @param fileId
     */
    public void insertShareFile(String shareUserId, ArrayList<String> receiveUserIdArray, String fileId) {
        for (String receiveUserId : receiveUserIdArray) {
            SdShareFileBean bean = new SdShareFileBean();
            String shareId = redisManager.getSequence(RedisSequenceConstants.SEQ_SHARE_ID) + "";
            bean.setShareId(shareId);
            bean.setShareUserId(shareUserId);
            bean.setShareFileId(fileId);
            bean.setCrtime(new SdTime(SdDateUtil.getNowTime()));
            bean.setReceiveUserId(receiveUserId);
            sdShareFileDao.insertShareFile(bean);
        }
    }
    
    /**
     * 取消分享
     * @param shareId
     */
    public void deleteShareFile(String shareId) {
        sdShareFileDao.deleteShareFile(shareId);
    }
    
    /**
     * 收到的分享
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public HashMap<String, Object> getRecieveShareList(String userId, int pageNo, int pageSize){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        List<SdShareFileBean2> fileList = sdShareFileDao.getRecieveShareList(userId, pageNo, pageSize);
        int totalCount = sdShareFileDao.getRecieveShareListCount(userId);
        returnMap.put("fileList", fileList);
        returnMap.put("totalCount", totalCount);
        
        return returnMap;
    }
    
    /**
     * 发出的分享
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public HashMap<String, Object> getSendShareList(String userId, int pageNo, int pageSize){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        List<SdShareFileBean2> fileList = sdShareFileDao.getSendShareList(userId, pageNo, pageSize);
        int totalCount = sdShareFileDao.getSendShareListCount(userId);
        returnMap.put("fileList", fileList);
        returnMap.put("totalCount", totalCount);
        
        return returnMap;
    }
    
}
