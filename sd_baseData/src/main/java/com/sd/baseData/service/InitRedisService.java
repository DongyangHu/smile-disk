package com.sd.baseData.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.sd.baseData.bean.SdModuleInfoBean;
import com.sd.baseData.bean.SdUserInfoBean;
import com.sd.baseData.common.RedisKeyConstants;
import com.sd.baseData.common.RedisSequenceConstants;
import com.sd.common.redis.RedisManager;

public class InitRedisService {
    
    private static Log logger = LogFactory.getLog(InitRedisService.class);
    private UserInfoService userInfoService = null;
    private SysInfoService sysInfoService = null;
    private RedisManager redisManager = null;
    

    public UserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    public SysInfoService getSysInfoService() {
        return sysInfoService;
    }

    public void setSysInfoService(SysInfoService sysInfoService) {
        this.sysInfoService = sysInfoService;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    /**
     * 初始化redis序列
     */
    public void initRedisSeq() throws Exception{
        Properties prop = new Properties();
            prop.load(getClass().getClassLoader().getResourceAsStream("redisSequences.properties"));

            Iterator<Entry<Object, Object>> it = prop.entrySet().iterator();
            while (it.hasNext()) {
                Entry<Object, Object> entry = it.next();
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                Integer maxValue = sysInfoService.getMaxSeq(value);
                String seq = maxValue.toString();
                
                logger.info("key:" + RedisSequenceConstants.SEQ_NAMESPACE + ":" + key);
                logger.info("value :" + seq);
                redisManager.put(RedisSequenceConstants.SEQ_NAMESPACE, key, seq);
            }
    }
    
    /**
     * 初始化用户信息到redis
     */
    public void initUserInfo() {
        List<SdUserInfoBean> userInfoList = userInfoService.getUserInfoList();
        logger.info("init userInfo start ...");
        for (SdUserInfoBean sdUserInfoBean : userInfoList) {
            String userString = JSONObject.toJSONString(sdUserInfoBean);
            redisManager.put(RedisKeyConstants.USER_INFO_NAMESPACE, sdUserInfoBean.getUserId(), userString);
        }
        logger.info("init userInfo end ...");
    }
    
    /**
     * 初始化角色模块信息到redis
     */
    public void initRoleModuleInfo() {
        HashMap<String, String> allRoleModuleMap = sysInfoService.getAllRoleModule();
        logger.info("init rolemodule start ...");
        Iterator<Entry<String, String>> iterator = allRoleModuleMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            redisManager.put(RedisKeyConstants.ROLE_MODULE_NAMESPACE, key, value);
        }
        logger.info("init rolemodule end ...");
    }
    
    /**
     * 初始化模块信息到redis
     */
    public void initModuleInfo() {
        List<SdModuleInfoBean> allModules = sysInfoService.getAllModule();
        logger.info("init module start ...");
        for (SdModuleInfoBean bean : allModules) {
            String key = bean.getModuleId();
            String value = JSONObject.toJSONString(bean);
            redisManager.put(RedisKeyConstants.MODULE_NAMESPACE, key, value);
        }
        logger.info("init module end ...");
    }
    

}
