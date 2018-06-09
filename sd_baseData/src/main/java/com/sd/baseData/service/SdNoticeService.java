package com.sd.baseData.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sd.baseData.bean.SdNoticeInfoBean;
import com.sd.baseData.common.RedisSequenceConstants;
import com.sd.baseData.dao.SdNoticeDao;
import com.sd.common.redis.RedisManager;
import com.sd.common.time.SdDateUtil;
import com.sd.common.time.SdTime;

public class SdNoticeService {
    
    private SdNoticeDao sdNoticeDao = null;
    private RedisManager redisManager = null;
    

    public RedisManager getRedisManager() {
        return redisManager;
    }
    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }
    public SdNoticeDao getSdNoticeDao() {
        return sdNoticeDao;
    }
    public void setSdNoticeDao(SdNoticeDao sdNoticeDao) {
        this.sdNoticeDao = sdNoticeDao;
    }
    
    
    
    /**
     * 总消息列表
     * @param userId
     * @param noticeName
     * @return
     */
    public HashMap<String, Object> getAllNoticeList(String noticeName, int pageNo, int pageSize){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        List<SdNoticeInfoBean> noticeList = sdNoticeDao.getAllNoticeList(noticeName, pageNo, pageSize);
        int totalCount = sdNoticeDao.getAllNoticeListCount(noticeName);
        returnMap.put("noticeList", noticeList);
        returnMap.put("totalCount", totalCount);
        
        return returnMap;
    }
    
    /**
     * 消息列表
     * @param userId
     * @param noticeName
     * @return
     */
    public HashMap<String, Object> getNoticeList(String userId, String noticeName, int pageNo, int pageSize){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        List<SdNoticeInfoBean> noticeList = sdNoticeDao.getNoticeList(userId, noticeName, pageNo, pageSize);
        int totalCount = sdNoticeDao.getNoticeListCount(userId, noticeName);
        returnMap.put("noticeList", noticeList);
        returnMap.put("totalCount", totalCount);
        
        return returnMap;
    }
    
    /**
     * 更新消息状态
     * @param userId
     * @param noticeId
     * @return
     */
    public void updateStatus(String userId, String noticeId){
        sdNoticeDao.updateStatus(userId, noticeId);
    }
    
    /**
     * 发布消息
     * @param noticeTitle
     * @param noticeContent
     */
    public void publishNotice(String userId, String noticeTitle, String noticeContent) {
        SdNoticeInfoBean bean = new SdNoticeInfoBean();
        String noticeId = redisManager.getSequence(RedisSequenceConstants.SEQ_NOTICE_INFO) + "";
        bean.setNoticeId(noticeId);
        bean.setNoticeTitle(noticeTitle);
        bean.setNoticeContent(noticeContent);
        bean.setCruserId(userId);
        bean.setCrtime(new SdTime(SdDateUtil.getNowTime()));
        sdNoticeDao.publishNotice(bean);
    }
    
    /**
     * 发布给用户
     * @param userId
     * @param userIdArray
     */
    public void noticeUser(String noticeId, ArrayList<String> userIdArray) {
        sdNoticeDao.deleteNoticeUser(noticeId);
        for (String userId : userIdArray) {
            sdNoticeDao.noticeUser(userId,noticeId,"0");
        }
    }
    
    /**
     * 删除消息
     * @param noticeId
     */
    public void deleteNotice(String noticeId) {
        sdNoticeDao.deleteNoticeUser(noticeId);
        sdNoticeDao.deleteNotice(noticeId);
    }
    
    
}
