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

import com.sd.baseData.service.InitRedisService;


@Path("/sd_baseData/initRedis")
public class InitRedisRest {
    
    private static Log logger = LogFactory.getLog(InitRedisRest.class);
    private InitRedisService initRedisService = null;
    

    public InitRedisService getInitRedisService() {
        return initRedisService;
    }

    public void setInitRedisService(InitRedisService initRedisService) {
        this.initRedisService = initRedisService;
    }

    
    
    @GET
    @Path("/init")
    @Produces("application/json; charset=utf-8")
    @BadgerFish
    public HashMap<String, Object> initRedis(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        try {
            logger.info("init start ...");
            initRedisService.initUserInfo();
            initRedisService.initRoleModuleInfo();
            initRedisService.initModuleInfo();
            logger.info("init end ...");
            returnMap.put("code", "0");
        } catch (Exception e) {
            logger.error("init", e);
            returnMap.put("code", "-1");
        }
        return returnMap;
    }
    
    @GET
    @Path("/initSeq")
    @Produces("application/json; charset=utf-8")
    @BadgerFish
    public HashMap<String, Object> initRedisSeq(@Context HttpServletRequest request, @Context UriInfo info){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        try {
            logger.info("initSeq");
            initRedisService.initRedisSeq();
            returnMap.put("code", "0");
        } catch (Exception e) {
            returnMap.put("code", "-1");
            logger.error("initSeq:", e);
        }
        
        
        return returnMap;
    }
    
    
}
