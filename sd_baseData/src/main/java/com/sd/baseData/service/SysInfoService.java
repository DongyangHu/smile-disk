package com.sd.baseData.service;


import java.util.HashMap;
import java.util.List;

import com.sd.baseData.bean.SdModuleInfoBean;
import com.sd.baseData.dao.SysInfoDao;

public class SysInfoService {
    
    private SysInfoDao sysInfoDao = null;
    
    
    public SysInfoDao getSysInfoDao() {
        return sysInfoDao;
    }
    public void setSysInfoDao(SysInfoDao sysInfoDao) {
        this.sysInfoDao = sysInfoDao;
    }



    /**
     * 获取序列
     * @param sql
     * @return
     */
    public Integer getMaxSeq(String sql) {
        return sysInfoDao.getMaxSeq(sql);
    }
    
    /**
     * 获取角色id对应的模块
     * @return
     */
    public HashMap<String, String> getAllRoleModule(){
        HashMap<String, String> returnMap = new HashMap<String, String>();
        List<String> allRoleId = sysInfoDao.getAllRoleId();
        for (String roleId : allRoleId) {
            String module = sysInfoDao.getRoleModuleById(roleId);
            returnMap.put(roleId, module);
        }
        return returnMap;
    }
    
    /**
     * 获取系统所有模块信息
     * @return
     */
    public List<SdModuleInfoBean> getAllModule(){
        return sysInfoDao.getAllModule();
    }
}
