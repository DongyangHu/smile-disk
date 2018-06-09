package com.sd.baseData.rest;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import com.sd.baseData.bean.SdModuleInfoBean;
import com.sd.baseData.bean.SdUserInfoBean;
import com.sd.baseData.common.SdBaseDataConstants;
import com.sd.baseData.service.UserInfoService;
import com.sd.common.encryp.EncrypConstants;
import com.sd.common.encryp.EncryptAES;
import com.sd.common.encryp.EncryptRSA;
import com.sd.common.i18n.SdI18n;
import com.sd.common.random.ValidateCodeUtil;
import com.sd.sso.filter.SdSessionConstant;
import com.sd.sso.filter.bean.SdUserSessionInfoBean;


@Path("/sd_baseData/userInfo")
public class UserInfoRest {
    
    private static Log logger = LogFactory.getLog(UserInfoRest.class);
    private UserInfoService userInfoService = null;
    
    
    public UserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    
    

    /**
     * 校验userCode唯一性
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/checkUserCode")
    @Produces("application/json;charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> checkUserCodeUnique(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        //用户语言
        String lang = request.getParameter("languageType");
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        String userCode = request.getParameter("userCode") == null ? "" : request.getParameter("userCode");
        try {
            //校验userCode唯一性
            boolean flag = userInfoService.checkUserCodeUnique(userCode);
            returnMap.put("flag", flag);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("checkUserCode:" + e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        
        return returnMap;
    }
    
    /**
     * 从redis中获取所有用户信息
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/getUserInfoListRedis")
    @Produces("application/json;charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> getUserInfoListRedis(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        try {
            List<SdUserInfoBean> userInfoList = userInfoService.getUserInfoListRedis();
            returnMap.put("userInfoList", userInfoList);
            returnMap.put("code", "0");
        } catch (Exception e) {
            logger.error("getUserInfoListRedis：", e);
            returnMap.put("code", "-1");
        }
        
        return returnMap;
    }
    
    
    /**
     * 从数据库中获取所有用户信息
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/getUserInfoList")
    @Produces("application/json;charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> getUserInfoList(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        
        try {
            String pageNoTemp = request.getParameter("pageNo") == null ? SdBaseDataConstants.DEFAULT_PAGE_NO : request.getParameter("pageNo");
            String pageSizeTemp = request.getParameter("pageSize") == null ? SdBaseDataConstants.DEFAULT_PAGE_SIZE : request.getParameter("pageSize");
            int pageNo = Integer.valueOf(pageNoTemp);
            int pageSize = Integer.valueOf(pageSizeTemp);
            HashMap<String, Object> userInfoMap = userInfoService.getUserInfoList(pageNo, pageSize);
            returnMap.putAll(userInfoMap);
            returnMap.put("code", "0");
        } catch (Exception e) {
            logger.error("getUserInfoList：", e);
            returnMap.put("code", "-1");
        }
        
        return returnMap;
    }
    
    /**
     * 用户注册
     * @param request
     * @param paramMap
     * @return
     */
    @POST
    @Path("/userRegister")
    @Produces("application/json;charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> userRegister(@Context HttpServletRequest request, HashMap<String, Object> paramMap){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String lang = request.getParameter("languageType");
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            String userCode = paramMap.get("userCode").toString();
            String password = EncryptAES.encryptAES(EncryptRSA.decrypt(paramMap.get("password").toString()), EncrypConstants.AES_DECRYPT_KEY);
            String email = paramMap.get("email").toString();
            userInfoService.userRegister(userCode, password, email);
            returnMap.put("message",SdI18n.getMessage("success", lang));
            returnMap.put("code", "0");
        } catch (Exception e) {
            logger.error("userRegister：", e);
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
            returnMap.put("code", "e10000");
        }
        
        return returnMap;
    }
    
    /**
     * 用户邮箱校验
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/checkEmail")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> validateRegister(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String lang = request.getParameter("languageType");
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            String registerId = request.getParameter("registerId");
            String userCode = request.getParameter("userCode");
            String userEmail = request.getParameter("userEmail");
            String checkCode = request.getParameter("checkCode");
            if(null == registerId || "".equals(registerId)) {
                returnMap.put("code", 2);
                returnMap.put("message", SdI18n.getMessage("success", lang));
                return returnMap;
            }
            if(null == userCode || "".equals(userCode)) {
                returnMap.put("code", 2);
                returnMap.put("message", SdI18n.getMessage("success", lang));
                return returnMap;
            }
            if(null == userEmail || "".equals(userEmail)) {
                returnMap.put("code", 2);
                returnMap.put("message", SdI18n.getMessage("success", lang));
                return returnMap;
            }
            if(null == checkCode || "".equals(checkCode)) {
                returnMap.put("code", 2);
                returnMap.put("message", SdI18n.getMessage("success", lang));
                return returnMap;
            }
            registerId = EncryptAES.decryptAES(request.getParameter("registerId"), EncrypConstants.AES_DECRYPT_KEY);
            userCode = EncryptAES.decryptAES(request.getParameter("userCode"), EncrypConstants.AES_DECRYPT_KEY);
            userEmail = EncryptAES.decryptAES(request.getParameter("userEmail"), EncrypConstants.AES_DECRYPT_KEY);
            checkCode = EncryptAES.decryptAES(request.getParameter("checkCode"), EncrypConstants.AES_DECRYPT_KEY);
            int type = userInfoService.validateRegister(registerId, userCode, userEmail, checkCode);
            returnMap.put("code", type);
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
            logger.error("checkEmail:", e);
        }
        
        return returnMap;
    }
    
    
    /**
     * 获取用户的模块id列表
     * @param request
     * @param INFO
     * @return
     */
    @GET
    @Path("/getUserModuleList")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> getUserModuleList(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String lang = "";
        String parentModuleId = request.getParameter("parentModuleId") == null ? "" : request.getParameter("parentModuleId");
        try {
            //获取用户信息
            SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
            lang = userInfo.getLang() == null ? SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE : userInfo.getLang();
            //用户角色信息
            String roleId = userInfo.getRoleId() == null ? SdBaseDataConstants.DEFAULT_USER_ROLE : userInfo.getRoleId();
            List<SdModuleInfoBean> roleModuleList = userInfoService.getRoleModuleList(roleId, parentModuleId);
            returnMap.put("moduleList", roleModuleList);
            returnMap.put("code","0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
            logger.error("getUserModuleList:", e);            
        }
        
        return returnMap;
    }
    
    /**
     * 获取用户邮箱
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/getUserEmail")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> getUserInfoByParams(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String lang = request.getParameter("languageType");
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            String userId = request.getParameter("userId");
            String userCode = request.getParameter("userCode");
            SdUserInfoBean userInfo = userInfoService.getUserInfoByParams(userCode, userId);
            String userEmail = userInfo.getUserEmail();
            request.getSession().setAttribute(SdBaseDataConstants.USER_EMAIL_ADDR, userEmail);
            userEmail = userEmail.substring(0, 3) + "***" + userEmail.substring(userEmail.indexOf("@") - 2, userEmail.indexOf("@")) + userEmail.substring(userEmail.indexOf("@"));
            returnMap.put("userEmail", userEmail);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
            logger.error("getUserInfo", e);
        }
        return returnMap;
    }
    
    /**
     * 获取用户信息
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/user")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> getUser(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String lang = request.getParameter("languageType");
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
            SdUserInfoBean userInfoBean = userInfoService.getUserInfoByParams(null, userInfo.getUserId());
            returnMap.put("user", userInfoBean);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
            logger.error("getUserInfo", e);
        }
        return returnMap;
    }
    
    /**
     * 根据code获取用户信息
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> getUserInfo(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String lang = request.getParameter("languageType");
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        String userCode = request.getParameter("userCode");
        try {
            SdUserInfoBean userInfoBean = userInfoService.getUserInfoByParams(userCode, null);
            returnMap.put("user", userInfoBean);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
            logger.error("getUserInfo", e);
        }
        return returnMap;
    }
    
    /**
     * 发送邮箱验证码
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/sendEmailCode")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> sendEmailCode(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String lang = request.getParameter("languageType");
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            String userCode = request.getParameter("userCode");
            String userEmail = (String) request.getSession().getAttribute(SdBaseDataConstants.USER_EMAIL_ADDR);
            String checkCode = ValidateCodeUtil.getStringRandom(6);
            request.getSession().setAttribute(SdBaseDataConstants.EMAIL_CHECK_CODE, checkCode);
            userInfoService.sendEmailCode(userCode, userEmail, checkCode);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
            logger.error("getUserInfo", e);
        }
        return returnMap;
    }
    /**
     * 校验邮箱验证码
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/checkEmailCode")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> checkEmailCode(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String lang = request.getParameter("languageType");
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            String checkCode = request.getParameter("checkCode");
            String sessionCheckCode = (String) request.getSession().getAttribute(SdBaseDataConstants.EMAIL_CHECK_CODE);
            boolean flag = false;
            if(checkCode.equals(sessionCheckCode)) {
                flag = true;
            }
            returnMap.put("flag", flag);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
            logger.error("getUserInfo", e);
        }
        return returnMap;
    }
    
    /**
     * 重设密码
     * @param request
     * @param info
     * @return
     */
    @POST
    @Path("/resetUserPwd")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> resetUserPwd(@Context HttpServletRequest request, HashMap<String, Object> paramMap){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String lang = paramMap.get("languageType").toString();
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            String userCode = paramMap.get("userCode").toString();
            String password = EncryptAES.encryptAES(EncryptRSA.decrypt(paramMap.get("password").toString()), EncrypConstants.AES_DECRYPT_KEY);
            userInfoService.updateUserInfo(userCode, password);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
            logger.error("resetUserPwd", e);
        }
        return returnMap;
    }
    
    
    /**
     * 更改用户状态
     * @param request
     * @param info
     * @return
     */
    @POST
    @Path("/changeUserStatus")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> changeUserStatus(@Context HttpServletRequest request, HashMap<String, Object> paramMap){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        
        try {
            String userId = paramMap.get("userId").toString();
            String status = paramMap.get("status").toString();
            userInfoService.changeUserStatus(userId, status);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
            logger.error("changeUserStatus", e);
        }
        return returnMap;
    }
    
    /**
     * 管理员重置用户密码
     * @param request
     * @param info
     * @return
     */
    @POST
    @Path("/refreshUserPwd")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> refreshUserPwd(@Context HttpServletRequest request, HashMap<String, Object> paramMap){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        
        try {
            String userCode = paramMap.get("userCode").toString();
            String password = EncryptAES.encryptAES(SdBaseDataConstants.DEFAULT_USER_PWD, EncrypConstants.AES_DECRYPT_KEY);
            userInfoService.updateUserInfo(userCode, password);
            
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
            logger.error("refreshUserPwd", e);
        }
        return returnMap;
    }
    
}
