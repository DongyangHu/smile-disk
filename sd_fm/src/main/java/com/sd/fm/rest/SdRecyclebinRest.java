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

import com.sd.common.i18n.SdI18n;
import com.sd.fm.common.SdFmConstants;
import com.sd.fm.service.SdFileInfoService;
import com.sd.sso.filter.SdSessionConstant;
import com.sd.sso.filter.bean.SdUserSessionInfoBean;

/**
 * 回收站相关接口
 * @author HuDongyang
 *
 */
@Path("/rest/recyclebin")
public class SdRecyclebinRest {
    
    public static Log logger = LogFactory.getLog(SdRecyclebinRest.class);
    private SdFileInfoService sdFileInfoService = null;
    
    
    public SdFileInfoService getSdFileInfoService() {
        return sdFileInfoService;
    }
    public void setSdFileInfoService(SdFileInfoService sdFileInfoService) {
        this.sdFileInfoService = sdFileInfoService;
    }


    /**
     * 回收站文件列表
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/list")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> getResList(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdFmConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            //分页参数
            String pageNoTemp = request.getParameter("pageNo") == null ? SdFmConstants.DEFAULT_PAGE_NO : request.getParameter("pageNo");
            String pageSizeTemp = request.getParameter("pageSize") == null ? SdFmConstants.DEFAULT_PAGE_SIZE : request.getParameter("pageSize");
            int pageNo = Integer.valueOf(pageNoTemp);
            int pageSize = Integer.valueOf(pageSizeTemp);
            //文件名称筛选(解码)
            String fileName = request.getParameter("fileName");
            if(null != fileName && !"".equals(fileName)) {
                fileName = URLDecoder.decode(URLDecoder.decode(fileName, "UTF-8"), "UTF-8");
            }
            
            HashMap<String, Object> recyclebinFileList = sdFileInfoService.getFileList(userInfo.getUserId(), fileName, null, null, "0", pageNo, pageSize);
            returnMap.putAll(recyclebinFileList);
            returnMap.put("code", "0");
            returnMap.put("message", SdI18n.getMessage("success", lang));
        } catch (Exception e) {
            logger.error("/recyclebin/list", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        return returnMap;
    }
    
    @SuppressWarnings("unchecked")
    @POST
    @Path("/recover")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> recoverFile(@Context HttpServletRequest request, HashMap<String, Object> paramMap){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String lang = userInfo.getLang();
        if(null == lang || "".equals(lang)) {
            lang = SdFmConstants.DEFAULT_LANGYAGE_TYPE;
        }
        try {
            ArrayList<String> fileIdArray = (ArrayList<String>) paramMap.get("fileIdArray");
            
            boolean flag = sdFileInfoService.recoverFile(fileIdArray);
            if(flag) {//恢复成功
                returnMap.put("code", "0");
                returnMap.put("message", SdI18n.getMessage("success", lang));
            }else {
                returnMap.put("code", "0");
                returnMap.put("message", SdI18n.getMessage("e10000", lang));
            }
        } catch (Exception e) {
            logger.error("/recyclebin/recover", e);
            returnMap.put("code", "e10000");
            returnMap.put("message", SdI18n.getMessage("e10000", lang));
        }
        return returnMap;
    }
    
    
    
}
