package com.sd.baseData.dao;

import java.util.HashMap;
import java.util.List;

import com.sd.baseData.bean.SdModuleInfoBean;

public class SysInfoDao extends BaseDao{

    /**
     * 获取序列
     * @param sql
     * @return
     */
    public Integer getMaxSeq(String sql) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("maxSeqSql", sql);
        return this.getSqlSession().selectOne("sysInfo.getMaxSeq", params);
    }
    
    /**
     * 获取角色模块映射表中全部的角色Id
     * @return
     */
    public List<String> getAllRoleId(){
        return this.getSqlSession().selectList("sysInfo.getAllRoleId");
    }
    
    /**
     * 根据角色id获取角色对应的模块信息
     * @param roleId
     * @return
     */
    public String getRoleModuleById(String roleId) {
        StringBuffer sb = new StringBuffer();
        List<String> moduleIdList = this.getSqlSession().selectList("sysInfo.getRoleModuleById", roleId);
        for(int i = 0; i < moduleIdList.size(); i++) {
            if(i != (moduleIdList.size() - 1)) {
                sb.append(moduleIdList.get(i));
                sb.append(",");
            }else {
                sb.append(moduleIdList.get(i));
            }
        }
        return sb.toString();
    }
    /**
     * 获取所有模块信息
     */
    public List<SdModuleInfoBean> getAllModule(){
        return this.getSqlSession().selectList("sysInfo.getAllModule");
    }
}
