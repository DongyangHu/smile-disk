package com.sd.baseData.dao;

import java.util.HashMap;
import java.util.List;

import com.sd.baseData.bean.SdFriendApplyInfoBean;
import com.sd.baseData.bean.SdFriendApplyReturnBean;
import com.sd.baseData.bean.SdFriendInfoBean;
import com.sd.common.time.SdDateUtil;
import com.sd.common.time.SdTime;

public class SdFriendDao extends BaseDao {
    
    
    /**
     * 所有的好友
     * @param userId
     * @return
     */
    public List<SdFriendInfoBean> getAllFriendList(String userId){
        return this.getSqlSession().selectList("friend.getAllFriendList", userId);
    }
    
    /**
     * 好友列表
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<SdFriendInfoBean> getFriendList(String userId, int pageNo, int pageSize){
        HashMap<String, Object> pageQueryParams = getPageQueryParams(pageNo, pageSize);
        pageQueryParams.put("userId", userId);
        return this.getSqlSession().selectList("friend.getFriendList", pageQueryParams);
    }
    
    /**
     * 好友列表总数
     * @param userId
     * @return
     */
    public int getFriendListCount(String userId){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return this.getSqlSession().selectOne("friend.getFriendListCount", params);
    }
    
    /**
     * 好友申请列表
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<SdFriendApplyReturnBean> getFriendApplyList(String userId, int pageNo, int pageSize){
        HashMap<String, Object> pageQueryParams = getPageQueryParams(pageNo, pageSize);
        pageQueryParams.put("userId", userId);
        return this.getSqlSession().selectList("friend.getFriendApplyList", pageQueryParams);
    }
    
    /**
     * 好友申请列表总数
     * @param userId
     * @return
     */
    public int getFriendApplyListCount(String userId){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return this.getSqlSession().selectOne("friend.getFriendApplyListCount", params);
    }
    
    /**
     * 删除好友
     * @param userId
     * @param friendId
     * @return
     */
    public int deleteFriendById(String userId, String friendId) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("friendId", friendId);
        return this.getSqlSession().delete("friend.deleteFriendById", param);
    }
    
    /**
     * 添加申请记录
     * @param userId
     * @param friendId
     * @return
     */
    public int sendFriendApply(SdFriendApplyInfoBean bean) {
        return this.getSqlSession().insert("friend.sendFriendApply", bean);
    }
    
    /**
     * 处理好友申请
     * @param applyId
     * @param result
     */
    public int updateFriendApply(String applyId, String result) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("applyId", applyId);
        param.put("result", result);
        param.put("handleTime", new SdTime(SdDateUtil.getNowTime()));
        return this.getSqlSession().delete("friend.updateFriendApply", param);
    }
    
    /**
     * 添加好友记录
     * @param userId
     * @param friendId
     * @return
     */
    public int insertFriendRelationship(String userId, String friendId) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("friendId", friendId);
        param.put("crtime", new SdTime(SdDateUtil.getNowTime()));
        return this.getSqlSession().delete("friend.insertFriendRelationship", param);
    }
    
}
