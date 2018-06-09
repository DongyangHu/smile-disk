package com.sd.fm.dao;

import java.util.HashMap;
import java.util.List;

import com.sd.fm.bean.SdShareFileBean;
import com.sd.fm.bean.SdShareFileBean2;

public class SdShareFileDao extends BaseDao{

    /**
     * 添加共享
     * @param bean
     * @return
     */
    public int insertShareFile(SdShareFileBean bean) {
        return this.getSqlSession().insert("shareFile.insertShare", bean);
    }
    
    /**
     * 删除共享
     * @param bean
     * @return
     */
    public int deleteShareFile(String shareId) {
        return this.getSqlSession().delete("shareFile.deleteShare", shareId);
    }
    
    /**
     * 收到的共享文件列表
     * @return
     */
    public List<SdShareFileBean2> getRecieveShareList(String userId, int pageNo, int pageSize){
        HashMap<String, Object> pageQueryParams = getPageQueryParams(pageNo, pageSize);
        pageQueryParams.put("userId", userId);
        return this.getSqlSession().selectList("shareFile.recieveShareList", pageQueryParams);
    }
    
    /**
     * 收到的共享文件列表条数
     * @return
     */
    public int getRecieveShareListCount(String userId){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return this.getSqlSession().selectOne("shareFile.recieveShareListCount", params);
    }
    
    /**
     * 发出的共享文件列表
     * @return
     */
    public List<SdShareFileBean2> getSendShareList(String userId, int pageNo, int pageSize){
        HashMap<String, Object> pageQueryParams = getPageQueryParams(pageNo, pageSize);
        pageQueryParams.put("userId", userId);
        return this.getSqlSession().selectList("shareFile.sendShareList", pageQueryParams);
    }
    
    /**
     * 发出的共享文件列表条数
     * @return
     */
    public int getSendShareListCount(String userId){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return this.getSqlSession().selectOne("shareFile.sendShareListCount", params);
    }
    
}
