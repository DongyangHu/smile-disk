package com.sd.fm.rest;

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

import com.sd.common.i18n.SdI18n;
import com.sd.fm.common.SdFmConstants;
import com.sd.fm.service.SdShareFileService;
import com.sd.sso.filter.SdSessionConstant;
import com.sd.sso.filter.bean.SdUserSessionInfoBean;

@Path("/rest/fileShare")
public class SdShareFileRest {
    
    private static Log logger = LogFactory.getLog(SdShareFileRest.class);
    private SdShareFileService sdShareFileService = null;

    public SdShareFileService getSdShareFileService() {
        return sdShareFileService;
    }
    public void setSdShareFileService(SdShareFileService sdShareFileService) {
        this.sdShareFileService = sdShareFileService;
    }
    
    /**
     * 分享文件
     * @param request
     * @param info
     * @return
     */
    @SuppressWarnings("unchecked")
    @POST
    @Path("/share")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> shareFile(@Context HttpServletRequest request, HashMap<String, Object> params){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdFmConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            //分享的文件名
            String fileId = params.get("fileId").toString();
            //接收人
            ArrayList<String> receiveUserIdArray = (ArrayList<String>) params.get("friendIdArray");
            //分享的用户id
            String shareUserId = userInfo.getUserId();
            sdShareFileService.insertShareFile(shareUserId, receiveUserIdArray, fileId);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/fileShare/share", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        return returnMap;
    }
    
    /**
     * 取消文件分享
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/cancel")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> shareFileCancel(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdFmConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            //分享的id
            String shareId = request.getParameter("shareId");
            sdShareFileService.deleteShareFile(shareId);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/fileShare/share", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        return returnMap;
    }
    
    /**
     * 收到的分享
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/receiveShare")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> receiveShareList(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdFmConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            String pageNoTemp = request.getParameter("pageNo") == null ? SdFmConstants.DEFAULT_PAGE_NO : request.getParameter("pageNo");
            String pageSizeTemp = request.getParameter("pageSize") == null ? SdFmConstants.DEFAULT_PAGE_SIZE : request.getParameter("pageSize");
            int pageNo = Integer.valueOf(pageNoTemp);
            int pageSize = Integer.valueOf(pageSizeTemp);
            
            HashMap<String, Object> recieveShareList = sdShareFileService.getRecieveShareList(userInfo.getUserId(), pageNo, pageSize);
            returnMap.putAll(recieveShareList);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/receiveShare", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        return returnMap;
    }
    
    /**
     * 发出的分享
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/sendShare")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> sendShareList(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdFmConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            String pageNoTemp = request.getParameter("pageNo") == null ? SdFmConstants.DEFAULT_PAGE_NO : request.getParameter("pageNo");
            String pageSizeTemp = request.getParameter("pageSize") == null ? SdFmConstants.DEFAULT_PAGE_SIZE : request.getParameter("pageSize");
            int pageNo = Integer.valueOf(pageNoTemp);
            int pageSize = Integer.valueOf(pageSizeTemp);
            
            HashMap<String, Object> recieveShareList = sdShareFileService.getSendShareList(userInfo.getUserId(), pageNo, pageSize);
            returnMap.putAll(recieveShareList);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/sendShare", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        return returnMap;
    }
    
}
