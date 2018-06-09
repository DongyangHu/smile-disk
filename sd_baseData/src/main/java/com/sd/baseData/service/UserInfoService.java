package com.sd.baseData.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.sd.baseData.bean.SdModuleInfoBean;
import com.sd.baseData.bean.SdUserInfoBean;
import com.sd.baseData.bean.SdUserRegisterBean;
import com.sd.baseData.common.RedisKeyConstants;
import com.sd.baseData.common.RedisSequenceConstants;
import com.sd.baseData.common.SdBaseDataConstants;
import com.sd.baseData.dao.UserInfoDao;
import com.sd.common.encryp.EncrypConstants;
import com.sd.common.encryp.EncryptAES;
import com.sd.common.mail.SendMailUtil;
import com.sd.common.random.ValidateCodeUtil;
import com.sd.common.redis.RedisManager;
import com.sd.common.time.SdDateUtil;
import com.sd.common.time.SdTime;
import com.sd.common.time.TimeCompare;
import com.sd.sso.filter.SdSSOFilter;

public class UserInfoService {
    
    private static Log logger = LogFactory.getLog(UserInfoService.class);
    private UserInfoDao userInfoDao = null;
    private RedisManager redisManager = null;
    

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    /**
     * 校验userCode唯一
     * @param userCode
     * @return
     */
    public boolean checkUserCodeUnique(String userCode) {
        boolean flag = true;
        if(null != userCode && "" != userCode) {
            int count = userInfoDao.checkUserCodeUnique(userCode);
            if(count!=0) {
                flag = false;
            }
        }else {
            flag = false;
        }
        return flag;
    }
    
    /**
     * 从数据库获取所有用户列表
     * @return
     */
    public HashMap<String, Object> getUserInfoList(int pageNo, int pageSize){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        List<SdUserInfoBean> userInfoList = userInfoDao.getUserInfoList(pageNo, pageSize);
        int totalCount = userInfoDao.getUserInfoListCount();
        returnMap.put("userInfoList", userInfoList);
        returnMap.put("totalCount", totalCount);
        return returnMap;
    }
    
    public List<SdUserInfoBean> getUserInfoList(){
        return userInfoDao.getUserInfoList();
    }
    
    /**
     * 从redis获取所有用户列表
     */
    public List<SdUserInfoBean> getUserInfoListRedis(){
        List<SdUserInfoBean> userInfoList = new ArrayList<SdUserInfoBean>();
        Set<Entry<String, String>> userListSet = redisManager.getSetByNamespace(RedisKeyConstants.USER_INFO_NAMESPACE);
        Iterator<Entry<String, String>> iterator = userListSet.iterator();
        while (iterator.hasNext()) {
            Entry<String, String> userInfo = iterator.next();
            JSONObject jsonObject = JSONObject.parseObject(userInfo.getValue());
            SdUserInfoBean bean = JSONObject.toJavaObject(jsonObject, SdUserInfoBean.class);
            userInfoList.add(bean);
        }
        return userInfoList;
    }
    
    /**
     * 用户注册
     * @param userCode
     * @param password
     * @param email
     */
    public void userRegister(String userCode, String password, String email) throws Exception{
        SdUserRegisterBean bean = new SdUserRegisterBean();
        long registerId = redisManager.getSequence(RedisSequenceConstants.SEQ_USER_REGISTER);
        String checkCode = ValidateCodeUtil.getStringRandom(6);
        
        bean.setRegisterId(registerId + "");
        bean.setUserCode(userCode);
        bean.setUserPassword(password);
        bean.setUserEmail(email);
        bean.setCheckCode(checkCode);
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.add(Calendar.DATE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sdf.format(now.getTime());
        SdTime time = new SdTime(dateTime);
        bean.setInvalidTime(time);
        //用户注册信息保存到数据库
        int count = userInfoDao.saveUserRegisterInfo(bean);
        if(count > 0) {
            //校验链接和邮件内容
            String validateUrl = createValidateUrl(registerId+"", userCode, email, checkCode);
            String emailContent = createValidateEmailContent(userCode, validateUrl);
            String mailSubject = getMailSubject(SdBaseDataConstants.MAIL_SUBJECT_REGISTER);
            //发送邮件
            SendMailUtil.SendMailSSL(email, userCode, emailContent, mailSubject);
        }
    }
    
    /**
     * 获取配置文件
     * @param path
     * @return
     */
    private Properties getProperties(String path) {
        Properties props = new Properties();
        try {
            InputStream inputStream = UserInfoService.class.getClassLoader().getResourceAsStream(path);
            if(null != inputStream) {
                props.load(inputStream);
            }else {
                logger.warn(path + "文件不存在");
            }
        }catch (Exception e) {
            logger.warn("读取" + path +"文件异常",e);
        }
        return props;
    }
    
    /**
     * 生成校验用户注册的链接
     * @param bean
     * @return
     */
    private String createValidateUrl(String registerId, String userCode, String userEmail, String checkCode) {
        String validateUrl = "";
        //获取参数
        Properties props = getProperties("sso.properties");
        String url = props.getProperty("checkEmailUrl",null);
        registerId = "registerId=" + EncryptAES.encryptAES(registerId,EncrypConstants.AES_DECRYPT_KEY);
        userCode = "userCode=" + EncryptAES.encryptAES(userCode,EncrypConstants.AES_DECRYPT_KEY);
        userEmail = "userEmail=" + EncryptAES.encryptAES(userEmail,EncrypConstants.AES_DECRYPT_KEY);
        checkCode = "checkCode=" + EncryptAES.encryptAES(checkCode,EncrypConstants.AES_DECRYPT_KEY);
        
        //生成链接
        validateUrl += url + "?";
        validateUrl += registerId + "&";
        validateUrl += userCode + "&";
        validateUrl += userEmail + "&";
        validateUrl += checkCode;
        
        return validateUrl;
    }
    
    /**
     * 创建邮件内容
     * @param userCode
     * @param validateUrl
     * @return
     */
    private String createValidateEmailContent (String userCode, String validateUrl) {
        String htmlContent = "";
        
        htmlContent += "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>邮箱校验</title></head>"
                + "<body><div style=' margin: 0 auto; width:600px; background-color:#FFF; "
                + "border:1px solid #dcdddd;border-bottom:none; box-shadow: -3px 0 10px #eaebec,0 0px 0 #FFF,"
                + "0 0px 0 #FFF, 3px 0 10px #eaebec;'><table border='0' ><tbody><tr><td style='border-bottom:1px "
                + "dotted #e5e5e5;padding:0; width: 590px;'>";
        htmlContent += "<h3 style='color: #32CD32; font-size: 18px; cursor:pointer;'"
                + "  onclick=\"javascript:window.open('" + SdBaseDataConstants.SD_WEB_INDEX + "')\">Smile Disk</h3>";
        htmlContent += "</td></tr><tr><td style='border-bottom:1px dotted #e5e5e5;padding:10px 0; width: 590px;'>"
                + "<h3 style=' font-size:14px;line-height:46px; color:#000000;margin:0;padding:0;'>亲爱的"+userCode+"您好：</h3>";
        htmlContent += "<p style='color:#000000; font-size:14px;line-height:24px;margin:5px;padding:0;'>"
                + "您的注册邮箱校验链接（该链接24小时内有效，不能重复使用）：<br/>";
        htmlContent += "<a href='" + validateUrl + "' target='_top' style='color: #006699;'>" + "点击验证" + "</a></p></td></tr>"
                + "<tr><td style='border-bottom:1px dotted #e5e5e5;padding:10px 0; width: 590px;'>"
                + "<p style='font-size:12px;line-height:22px;color:#848383;margin:0;padding:0;'>";
        htmlContent += "这是Smile Disk系统自动发的用户身份绑定邮件，<span style='color:#ff3000;'>请勿直接回复该邮件</span><br>如果不是您本"
                + "人的操作，可能是有用户误输入您的邮箱地址，您可以忽略此邮件。</p></td></tr></tbody></table></div></body></html>";
        
        return htmlContent;
    }
    
    /**
     * 注册邮箱验证
     * @param registerId
     * @param userCode
     * @param userEmail
     * @param checkCode
     * @return
     */
    public int validateRegister(String registerId, String userCode, String userEmail, String checkCode) {
        //0：正确，1：超时，2：错误连接
        int type = -1;
        SdUserRegisterBean bean = userInfoDao.getUserRegisterInfoByParam(registerId, userCode, userEmail, checkCode);
        if(null != bean) {
            SdTime invalidTime = bean.getInvalidTime();
            String time = invalidTime.getTime();
            //失效时间在当前时间之前，则失效
            if(TimeCompare.isBeforeNow(time)) {
                type = 1;
            }else {//正确，将用户信息插入表中
                SdUserInfoBean userInfoBean = new SdUserInfoBean();
                long userId = redisManager.getSequence(RedisSequenceConstants.SEQ_USER_INFO);
                userInfoBean.setUserId(userId + "");
                userInfoBean.setUserCode(bean.getUserCode());
                userInfoBean.setUserPassword(bean.getUserPassword());
                userInfoBean.setUserNikename(bean.getUserCode());
                userInfoBean.setUserEmail(bean.getUserEmail());
                userInfoBean.setUserStatus("1");
                userInfoBean.setCrtime(new SdTime(SdDateUtil.getNowTime()));
                userInfoDao.saveUserInfo(userInfoBean);
                userInfoDao.deleteUserRegisterInfoById(bean.getRegisterId());
                type = 0;
            }
        }else {
            type = 2;
        }
        return type;
    }
    
    /**
     * 根据角色id获取模块信息
     * @param roleId
     * @return
     */
    public List<SdModuleInfoBean> getRoleModuleList (String roleId, String parentModuleId){
        List<SdModuleInfoBean> returnList = new ArrayList<SdModuleInfoBean>();
        //角色对应的所有模块id
        String modules = redisManager.get(RedisKeyConstants.ROLE_MODULE_NAMESPACE + ":" + roleId);
        String[] idArray = modules.split(",");
        for (int i = 0; i < idArray.length; i++) {
            idArray[i] = RedisKeyConstants.MODULE_NAMESPACE + ":" + idArray[i];
        }
        //获取模块id对应的模块信息
        Set<Entry<String, String>> set = redisManager.get(idArray);
        Iterator<Entry<String, String>> iterator = set.iterator();
        //相关判断逻辑
        while(iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            //转换为java对象
            SdModuleInfoBean bean = JSONObject.parseObject(entry.getValue()).toJavaObject(SdModuleInfoBean.class);
            //模块父节点
            String parentId = bean.getModuleParentId();
            //参数不为空时
            if(null != parentModuleId && !"".equals(parentModuleId)) {
                if(null != parentId && parentId.equals(parentModuleId)) {
                    returnList.add(bean);
                }
            }else {//参数为空时
                if(null == parentId || "".equals(parentId)) {
                    returnList.add(bean);
                }
            }
        }
        return returnList;
    }
    
    
    /**
     * 根据用户id或者code获取单个用户信息
     * @param userCode
     * @param userId
     * @return
     */
    public SdUserInfoBean getUserInfoByParams(String userCode, String userId) {
        if(null != userCode && !"".equals(userCode)) {
            return userInfoDao.getUserInfoByParams(userCode, null);
        }else if(null != userId && !"".equals(userId)) {
            return userInfoDao.getUserInfoByParams(null, userId);
        }else {
            return null;
        }
    }
    
    /**
     * 像用户邮箱发送验证码
     * @param userEmail
     */
    public void sendEmailCode(String userCode, String userEmail, String checkCode) throws Exception{
        String emailContent = createEmailCodeContent(userCode, checkCode);
        String mailSubject = getMailSubject(SdBaseDataConstants.MAIL_SUBJECT_CODE);
        SendMailUtil.SendMailSSL(userEmail, userCode, emailContent, mailSubject);
    }
    
    /**
     * 生成邮箱验证码
     * @param userCode
     * @param checkCode
     * @return
     */
    private String createEmailCodeContent (String userCode, String checkCode) {
        String htmlContent = "";
        
        htmlContent += "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>邮箱校验</title></head>"
                + "<body><div style=' margin: 0 auto; width:600px; background-color:#FFF; "
                + "border:1px solid #dcdddd;border-bottom:none; box-shadow: -3px 0 10px #eaebec,0 0px 0 #FFF,"
                + "0 0px 0 #FFF, 3px 0 10px #eaebec;'><table border='0' ><tbody><tr><td style='border-bottom:1px "
                + "dotted #e5e5e5;padding:0; width: 590px;'>";
        htmlContent += "<h3 style='color: #32CD32; font-size: 18px; cursor:pointer;'"
                + "  onclick=\"javascript:window.open('" + SdBaseDataConstants.SD_WEB_INDEX + "')\">Smile Disk</h3>";
        htmlContent += "</td></tr><tr><td style='border-bottom:1px dotted #e5e5e5;padding:10px 0; width: 590px;'>"
                + "<h3 style=' font-size:14px;line-height:46px; color:#000000;margin:0;padding:0;'>亲爱的"+userCode+"您好：</h3>";
        htmlContent += "<p style='color:#000000; font-size:14px;line-height:24px;margin:5px;padding:0;'>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;您本次的验证码是：<span style='font-weight: bold;'>" + checkCode + "</span>，";
        htmlContent += "请在网页中填写，完成验证。 " + "</a></p></td></tr>"
                + "<tr><td style='border-bottom:1px dotted #e5e5e5;padding:10px 0; width: 590px;'>"
                + "<p style='font-size:12px;line-height:22px;color:#848383;margin:0;padding:0;'>";
        htmlContent += "这是Smile Disk系统自动发出的用于用户校验的邮件，<span style='color:#ff3000;'>请勿直接回复该邮件</span><br>如果不是您本"
                + "人的操作，可能是有用户误输入您的邮箱地址，您可以忽略此邮件。</p></td></tr></tbody></table></div></body></html>";
        
        return htmlContent;
    }
    
    /**
     * 获取邮件主题
     * @param subjectType
     * @return
     */
    private String getMailSubject(String subjectType){
        InputStream inStream = SdSSOFilter.class.getClassLoader().getResourceAsStream("sdMail.properties");
        Properties props = new Properties();
        try {
            props.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props.getProperty(subjectType,props.getProperty(SdBaseDataConstants.MAIL_SUBJECT_FEDAULT));
    }
    
    /**
     * 修改用户信息
     * @param userCode 用户编码
     * @param userPwd 用户密码
     */
    public void updateUserInfo(String userCode, String userPwd) {
        userInfoDao.updateUserInfo(userCode, userPwd);
    }
    
    /**
     * 修改用户状态
     * @param userId
     * @param status
     */
    public void changeUserStatus(String userId, String status) {
        userInfoDao.changeUserStatus(userId, status);
    }
    
}
