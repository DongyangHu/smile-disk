package com.sd.baseData.dao;

import java.util.HashMap;
import java.util.List;

import com.sd.baseData.bean.SdNoticeInfoBean;

public class SdNoticeDao extends BaseDao{
    

    /**
     * 消息总列表
     * @param userId
     * @param noticeName
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<SdNoticeInfoBean> getAllNoticeList(String noticeName, int pageNo, int pageSize){
        HashMap<String, Object> params = getPageQueryParams(pageNo, pageSize);
        
        if(null != noticeName && !"".equals(noticeName)) {
            params.put("noticeName", "%" + noticeName + "%");
        }
        return this.getSqlSession().selectList("notice.getAllNoticeList", params);
    }
    
    
    
    /**
     * 消息总列表总条数
     * @param userId
     * @param noticeName
     * @param pageNo
     * @param pageSize
     * @return
     */
    public int getAllNoticeListCount(String noticeName){
        HashMap<String, Object> params = new HashMap<String, Object>();

        if(null != noticeName && !"".equals(noticeName)) {
            params.put("noticeName", "%" + noticeName + "%");
        }
        
        return this.getSqlSession().selectOne("notice.getAllNoticeListCount", params);
    }
    /**
     * 消息列表
     * @param userId
     * @param noticeName
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<SdNoticeInfoBean> getNoticeList(String userId, String noticeName, int pageNo, int pageSize){
        HashMap<String, Object> params = getPageQueryParams(pageNo, pageSize);
        
        if(null != userId && !"".equals(userId)) {
            params.put("userId", userId);
        }
        if(null != noticeName && !"".equals(noticeName)) {
            params.put("noticeName", "%" + noticeName + "%");
        }
        
        return this.getSqlSession().selectList("notice.getNoticeList", params);
    }
    
    
    
    /**
     * 消息列表总条数
     * @param userId
     * @param noticeName
     * @param pageNo
     * @param pageSize
     * @return
     */
    public int getNoticeListCount(String userId, String noticeName){
        HashMap<String, Object> params = new HashMap<String, Object>();
        
        if(null != userId && !"".equals(userId)) {
            params.put("userId", userId);
        }
        if(null != noticeName && !"".equals(noticeName)) {
            params.put("noticeName", "%" + noticeName + "%");
        }
        
        return this.getSqlSession().selectOne("notice.getNoticeListCount", params);
    }
    
    /**
     * 更新消息阅读状态
     * @param userId
     * @param noticeId
     */
    public void updateStatus(String userId, String noticeId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("noticeId", noticeId);
        
        this.getSqlSession().update("notice.updateStatus", params);
    }
    
    /**
     * 发布消息
     * @param bean
     */
    public void publishNotice(SdNoticeInfoBean bean) {
        this.getSqlSession().insert("notice.publishNotice", bean);
    }
    
    /**
     * 删除消息关系
     * @param bean
     */
    public void deleteNoticeUser(String noticeId) {
        this.getSqlSession().insert("notice.deleteNoticeUser", noticeId);
    }


    /**
     * 发布给用户
     * @param userId
     * @param noticeId
     * @param string
     */
    public void noticeUser(String userId, String noticeId, String status) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("noticeId", noticeId);
        params.put("status", status);
        this.getSqlSession().insert("notice.noticeUser", params);
    }

    /**
     * 删除消息
     * @param noticeId
     */
    public void deleteNotice(String noticeId) {
        this.getSqlSession().insert("notice.deleteNotice", noticeId);
    }
    
    

    
    
}
