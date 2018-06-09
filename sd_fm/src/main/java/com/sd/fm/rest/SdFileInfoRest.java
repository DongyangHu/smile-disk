package com.sd.fm.rest;

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

import com.alibaba.fastjson.JSONArray;
import com.sd.common.i18n.SdI18n;
import com.sd.fm.bean.SdFileBean;
import com.sd.fm.common.SdFmConstants;
import com.sd.fm.service.SdFileInfoService;
import com.sd.sso.filter.SdSessionConstant;
import com.sd.sso.filter.bean.SdUserSessionInfoBean;

/**
 * 文件相关接口
 * @author HuDongyang
 *
 */
@Path("/rest/fileInfo")
public class SdFileInfoRest {
    
    public static Log logger = LogFactory.getLog(SdFileInfoRest.class);
    private SdFileInfoService sdFileInfoService = null;

    public SdFileInfoService getSdFileInfoService() {
        return sdFileInfoService;
    }
    public void setSdFileInfoService(SdFileInfoService sdFileInfoService) {
        this.sdFileInfoService = sdFileInfoService;
    }
    
    /**
     * 查询单条文件记录信息
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/file")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> getFileInfo(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdFmConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            String fileId = request.getParameter("fileId");
            SdFileBean file = sdFileInfoService.getFileInfo(fileId);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
            returnMap.put("fileInfo", file);
        } catch (Exception e) {
            logger.error("/fileInfo/file", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        return returnMap;
    }
    
    /**
     * 新建文件夹
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/createDir")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> createDir(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdFmConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            //文件夹名
            String dirName = URLDecoder.decode(URLDecoder.decode(request.getParameter("dirName"), "UTF-8"), "UTF-8");
            //父节点
            String parentDirId = request.getParameter("parentDirId");
            //用户id
            String userId = userInfo.getUserId();
            sdFileInfoService.createDir(userId, dirName, parentDirId);
            
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/fileInfo/file", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        return returnMap;
    }
    /**
     * 获取文件夹列表
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/getDirList")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> getFileDirList(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdFmConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            //父节点
            String parentDirId = request.getParameter("parentDirId") == null ? "" : request.getParameter("parentDirId");
            //用户id
            String userId = userInfo.getUserId();
            
            JSONArray fileDirList = sdFileInfoService.getFileDirList(parentDirId, userId, null);
            
            returnMap.put("fileDirList", fileDirList);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/getDirList", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        return returnMap;
    }
    
    /**
     * 获取文件列表
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/getFileList")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> getFileList(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdFmConstants.DEFAULT_LANGYAGE_TYPE;
        }
        
        try {
            String userId = userInfo.getUserId();
            String parentDirId = request.getParameter("parentDirId") == null ? "" : request.getParameter("parentDirId");
            String fileName = request.getParameter("fileName");
            String fileType = request.getParameter("fileType");
            if(null != fileName && !"".equals(fileName)) {
                fileName = URLDecoder.decode(URLDecoder.decode(fileName, "UTF-8"), "UTF-8");
            }
            String pageNoTemp = request.getParameter("pageNo") == null ? SdFmConstants.DEFAULT_PAGE_NO : request.getParameter("pageNo");
            String pageSizeTemp = request.getParameter("pageSize") == null ? SdFmConstants.DEFAULT_PAGE_SIZE : request.getParameter("pageSize");
            int pageNo = Integer.valueOf(pageNoTemp);
            int pageSize = Integer.valueOf(pageSizeTemp);
            
            HashMap<String, Object> fileList = sdFileInfoService.getFileList(userId, fileName, parentDirId, fileType, "1", pageNo, pageSize);
            
            returnMap.putAll(fileList);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/getFileList", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        
        return returnMap;
    }
    
    /**
     * 删除文件夹
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/deleteFileDirById")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> deleteFileDirById(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdFmConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            String dirId = request.getParameter("dirId") == null ? "" : request.getParameter("dirId");
            int flag = sdFileInfoService.deleteFileDirById(dirId, userInfo.getUserId());
            if(flag == 1) {//删除成功
                returnMap.put("code", "0");
                returnMap.put("type", "1");
                returnMap.put("message", SdI18n.getMessage("deleteFileDir_success", lang));
            }else if(flag == 0){//存在文件
                returnMap.put("code", "0");
                returnMap.put("type", "0");
                returnMap.put("message", SdI18n.getMessage("deleteFileDir_existFile", lang));
            }else {//删除失败
                returnMap.put("code", "0");
                returnMap.put("type", "2");
                returnMap.put("message", SdI18n.getMessage("deleteFileDir_fail", lang));
            }
            
        } catch (Exception e) {
            logger.error("/deleteFileDir", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        
        return returnMap;
    }
    /**
     * 删除文件
     * @param request
     * @param info
     * @return
     */
    @SuppressWarnings("unchecked")
    @POST
    @Path("/deleteFileById")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> deleteFileById(@Context HttpServletRequest request, HashMap<String, Object> params){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdFmConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            ArrayList<String> fileIdArray = (ArrayList<String>) params.get("fileIdArray");
            String type = (String) params.get("type");
            boolean flag = false;
            //临时
            if(type.equals("1")) {
                flag = sdFileInfoService.deleteFileById(fileIdArray);
            }
            //彻底删除
            if(type.equals("2")) {
                flag = sdFileInfoService.deleteFileByIdPhysical(fileIdArray);
            }
            
            
            if(flag) {//删除成功
                returnMap.put("code", "0");
                returnMap.put("message", SdI18n.getMessage("success", lang));
            }else {
                returnMap.put("code", "0");
                returnMap.put("message", SdI18n.getMessage("e10000", lang));
            }
        } catch (Exception e) {
            logger.error("/deleteFileDir", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        
        return returnMap;
    }
    /**
     * 文件重命名
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/rename")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> updateFileName(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdFmConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            String fileId = request.getParameter("fileId") == null ? "" : request.getParameter("fileId");
            String newFileName = URLDecoder.decode(URLDecoder.decode(request.getParameter("newFileName"), "UTF-8"), "UTF-8");
            boolean flag = sdFileInfoService.updateFileName(fileId, newFileName);
            if(flag) {//成功
                returnMap.put("code", "0");
                returnMap.put("message", SdI18n.getMessage("success", lang));
            }else {
                returnMap.put("code", "0");
                returnMap.put("message", SdI18n.getMessage("deleteFileDir_fail", lang));
            }
        } catch (Exception e) {
            logger.error("/deleteFileDir", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        
        return returnMap;
    }
    
    /**
     * 文件夹重命名
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/renameDir")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> updateFileDirName(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdFmConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            String fileId = request.getParameter("dirId") == null ? "" : request.getParameter("dirId");
            String newFileName = URLDecoder.decode(URLDecoder.decode(request.getParameter("dirName"), "UTF-8"), "UTF-8");
            boolean flag = sdFileInfoService.updateFileDirName(fileId, newFileName);
            if(flag) {//成功
                returnMap.put("code", "0");
                returnMap.put("message", SdI18n.getMessage("success", lang));
            }else {
                returnMap.put("code", "0");
                returnMap.put("message", "重命名失败");
            }
        } catch (Exception e) {
            logger.error("/deleteFileDir", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        
        return returnMap;
    }
    
    
}
