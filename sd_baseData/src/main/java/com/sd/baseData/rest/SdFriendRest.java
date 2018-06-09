package com.sd.baseData.rest;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import com.sd.baseData.common.SdBaseDataConstants;
import com.sd.baseData.service.SdFriendService;
import com.sd.common.i18n.SdI18n;
import com.sd.sso.filter.SdSessionConstant;
import com.sd.sso.filter.bean.SdUserSessionInfoBean;

@Path("/sd_baseData/friend")
public class SdFriendRest {
    
    
    private static Log logger = LogFactory.getLog(SdFriendRest.class);
    private SdFriendService sdFriendService = null;
    
    
    
    
    public SdFriendService getSdFriendService() {
        return sdFriendService;
    }
    public void setSdFriendService(SdFriendService sdFriendService) {
        this.sdFriendService = sdFriendService;
    }



    /**
     * 获取好友列表
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/getFriendList")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> getFriendList(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        
        try {
            String userId = userInfo.getUserId();
            String pageNoTemp = request.getParameter("pageNo") == null ? SdBaseDataConstants.DEFAULT_PAGE_NO : request.getParameter("pageNo");
            String pageSizeTemp = request.getParameter("pageSize") == null ? SdBaseDataConstants.DEFAULT_PAGE_SIZE : request.getParameter("pageSize");
            int pageNo = Integer.valueOf(pageNoTemp);
            int pageSize = Integer.valueOf(pageSizeTemp);
            
            HashMap<String, Object> friendList = sdFriendService.getFriendList(userId, pageNo, pageSize);
            
            returnMap.putAll(friendList);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/getFriendList", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        
        return returnMap;
    }
    
    /**
     * 获取好友申请列表
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/getApplyList")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> getApplyList(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        
        try {
            String userId = userInfo.getUserId();
            String pageNoTemp = request.getParameter("pageNo") == null ? SdBaseDataConstants.DEFAULT_PAGE_NO : request.getParameter("pageNo");
            String pageSizeTemp = request.getParameter("pageSize") == null ? SdBaseDataConstants.DEFAULT_PAGE_SIZE : request.getParameter("pageSize");
            int pageNo = Integer.valueOf(pageNoTemp);
            int pageSize = Integer.valueOf(pageSizeTemp);
            HashMap<String, Object> friendApplyList = sdFriendService.getFriendApplyList(userId, pageNo, pageSize);
            
            returnMap.putAll(friendApplyList);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/getFriendList", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        
        return returnMap;
    }
    
    /**
     * 删除好友
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/delete")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> deleteFriendById(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        String friendId = request.getParameter("friendId");
        try {
            String userId = userInfo.getUserId();
            sdFriendService.deleteFriendById(userId, friendId);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/deleteFriend", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        
        return returnMap;
    }
    
    
    /**
     * 发起好友申请
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/apply")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> sendFriendApply(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        String friendId = request.getParameter("friendId");
        try {
            String userId = userInfo.getUserId();
            int sendFriendApply = sdFriendService.sendFriendApply(userId, friendId);
            returnMap.put("code", "0");
            if(sendFriendApply == 1) {
                returnMap.put("message", SdI18n.getMessage("success", lang));
            }
            if(sendFriendApply == 0) {
                returnMap.put("message", SdI18n.getMessage("friendApply_exist", lang));
            }
        } catch (Exception e) {
            logger.error("/sendFriendApply", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        
        return returnMap;
    }
    
    /**
     * 处理好友申请
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/handleApply")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> handleFriendApply(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        String friendId = request.getParameter("friendId");
        String applyId = request.getParameter("applyId");
        String result = request.getParameter("result");
        try {
            String userId = userInfo.getUserId();
            sdFriendService.updateFriendApply(applyId, result, userId, friendId);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/handleApply", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        
        return returnMap;
    }
    
    
}
