package com.sd.baseData.rest;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

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

import com.sd.baseData.common.SdBaseDataConstants;
import com.sd.baseData.service.SdNoticeService;
import com.sd.common.i18n.SdI18n;
import com.sd.sso.filter.SdSessionConstant;
import com.sd.sso.filter.bean.SdUserSessionInfoBean;

@Path("/sd_baseData/notice")
public class SdNoticeRest {
    
    private static Log logger = LogFactory.getLog(SdNoticeRest.class);
    private SdNoticeService sdNoticeService = null;
    
    
    public SdNoticeService getSdNoticeService() {
        return sdNoticeService;
    }
    public void setSdNoticeService(SdNoticeService sdNoticeService) {
        this.sdNoticeService = sdNoticeService;
    }





    /**
     * 获取消息列表
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/allList")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> getAllNoticeList(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        
        try {
            String noticeName = request.getParameter("noticeName");
            if(null != noticeName && !"".equals(noticeName)) {
                noticeName = URLDecoder.decode(URLDecoder.decode(noticeName, "UTF-8"), "UTF-8");
            }
            String pageNoTemp = request.getParameter("pageNo") == null ? SdBaseDataConstants.DEFAULT_PAGE_NO : request.getParameter("pageNo");
            String pageSizeTemp = request.getParameter("pageSize") == null ? SdBaseDataConstants.DEFAULT_PAGE_SIZE : request.getParameter("pageSize");
            int pageNo = Integer.valueOf(pageNoTemp);
            int pageSize = Integer.valueOf(pageSizeTemp);
            HashMap<String, Object> noticeListMap = sdNoticeService.getAllNoticeList(noticeName, pageNo, pageSize);
            
            returnMap.putAll(noticeListMap);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/allList", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        
        return returnMap;
    }
    
    
    /**
     * 获取消息列表
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/list")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> getNoticeList(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        
        try {
            String userId = userInfo.getUserId();
            String noticeName = request.getParameter("noticeName");
            if(null != noticeName && !"".equals(noticeName)) {
                noticeName = URLDecoder.decode(URLDecoder.decode(noticeName, "UTF-8"), "UTF-8");
            }
            String pageNoTemp = request.getParameter("pageNo") == null ? SdBaseDataConstants.DEFAULT_PAGE_NO : request.getParameter("pageNo");
            String pageSizeTemp = request.getParameter("pageSize") == null ? SdBaseDataConstants.DEFAULT_PAGE_SIZE : request.getParameter("pageSize");
            int pageNo = Integer.valueOf(pageNoTemp);
            int pageSize = Integer.valueOf(pageSizeTemp);
            HashMap<String, Object> noticeListMap = sdNoticeService.getNoticeList(userId, noticeName, pageNo, pageSize);
            
            returnMap.putAll(noticeListMap);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/getNoticeList", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        
        return returnMap;
    }
    
    /**
     * 修改消息状态
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/status")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> updateStatus(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        
        try {
            String userId = userInfo.getUserId();
            String noticeId = request.getParameter("noticeId");
            sdNoticeService.updateStatus(userId, noticeId);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/updateStatus", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        
        return returnMap;
    }
    
    /**
     * 发布消息
     * @param request
     * @param info
     * @return
     */
    @POST
    @Path("/publishNotice")
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
            String userId = userInfo.getUserId();
            String noticeTitle = paramMap.get("title").toString();
            String noticeContent = paramMap.get("content").toString();
            sdNoticeService.publishNotice(userId, noticeTitle, noticeContent);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
            logger.error("publishNotice", e);
        }
        return returnMap;
    }
    
    /**
     * 发布消息给用户
     * @param request
     * @param info
     * @return
     */
    @SuppressWarnings("unchecked")
    @POST
    @Path("/noticeUser")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> noticeUser(@Context HttpServletRequest request, HashMap<String, Object> params){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            //消息ID
            String noticeId = params.get("noticeId").toString();
            //用户ID列表
            ArrayList<String> userIdArray = (ArrayList<String>) params.get("userIdArray");
            sdNoticeService.noticeUser(noticeId, userIdArray);
            
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("noticeUser", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        return returnMap;
    }
    
    /**
     * 删除消息
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/delete")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> deleteNotice(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdBaseDataConstants.DEFAULT_LANGYAGE_TYPE;
        }
        
        try {
            String noticeId = request.getParameter("noticeId");
            sdNoticeService.deleteNotice(noticeId);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/delete", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        
        return returnMap;
    }
    
    
}
