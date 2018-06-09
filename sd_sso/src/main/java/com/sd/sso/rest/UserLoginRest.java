package com.sd.sso.rest;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import com.sd.common.encryp.EncrypConstants;
import com.sd.common.encryp.EncryptAES;
import com.sd.common.encryp.EncryptRSA;
import com.sd.common.i18n.SdI18n;
import com.sd.sso.common.SdSsoConstant;
import com.sd.sso.filter.SdSessionConstant;
import com.sd.sso.filter.bean.SdUserSessionInfoBean;
import com.sd.sso.service.UserLoginService;

@Path("/sd_sso/login")
public class UserLoginRest {
    
    private Log logger = LogFactory.getLog(UserLoginRest.class);
    private UserLoginService userLoginService = null;

    public UserLoginService getUserLoginService() {
        return userLoginService;
    }

    public void setUserLoginService(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }
    
    
    /**
     * 用户登录
     * @param request
     * @param paramMap
     * @return
     */
    @POST
    @Path("")
    @Produces("application/json;charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> userLogin(@Context HttpServletRequest request, HashMap<String, Object> paramMap){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String lang = "";
        try {
            HttpSession session = request.getSession();
            lang = paramMap.get("languageType") == null ? SdSsoConstant.DEFAULT_LANGYAGE_TYPE : paramMap.get("languageType").toString();
            String userCode = paramMap.get("userCode") == null ? "" :paramMap.get("userCode").toString();
            String password = paramMap.get("password") == null ? "" : paramMap.get("password").toString();
            password = EncryptAES.encryptAES(EncryptRSA.decrypt(password), EncrypConstants.AES_DECRYPT_KEY);
            SdUserSessionInfoBean sdUserInfoBean = userLoginService.userLogin(userCode, password);
            if(null != sdUserInfoBean) {
                sdUserInfoBean.setLang(lang);
                session.setAttribute(SdSessionConstant.SESSION_USER_INFO, sdUserInfoBean);
                logger.info("userLogin----success----userCode:" + sdUserInfoBean.getUserCode() + "  sessionId:" + session.getId() + "--------");
                returnMap.put("type", "1");
                returnMap.put("code", "0");
                returnMap.put("message", SdI18n.getMessage("loginSuccess", lang));
            }else {
                returnMap.put("type", "-1");
                returnMap.put("code", "0");
                returnMap.put("message", SdI18n.getMessage("e10001", lang));
            }
            
        } catch (Exception e) {
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
            logger.error("user login: " + e);
        }
        
        return returnMap;
    }

    /**
     * 退出登录
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/out")
    @Produces("application/json; charset=utf-8")
    @BadgerFish
    public HashMap<String,String> userLoginout(@Context HttpServletRequest request,@Context UriInfo info) {
        HashMap<String,String> returnMap = new HashMap<String,String>();
        String lang="";
        try {
            SdUserSessionInfoBean sdUserInfoBean = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
            lang = sdUserInfoBean.getLang();
            request.getSession().removeAttribute(SdSessionConstant.SESSION_USER_INFO);
            request.getSession().invalidate();
            returnMap.put("code", "0");
            returnMap.put("message",SdI18n.getMessage("success",lang));
        } catch (Exception e) {
            returnMap.put("code", "e10000");
            returnMap.put("message",SdI18n.getMessage("e10000",lang));
            logger.error("userLoginout:", e);
        }
        return returnMap;
    }
    
}
