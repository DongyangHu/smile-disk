package com.sd.base.rest;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import com.alibaba.fastjson.JSONObject;
import com.sd.base.bean.TestUserBean;
import com.sd.base.service.TestService;

@Path("sd_base/testRest")
public class TestRest {

    private Log loger = LogFactory.getLog(TestRest.class);
    private TestService testService = null;

    public TestService getTestService() {
        return testService;
    }

    public void setTestService(TestService testService) {
        this.testService = testService;
    }
    
    
    @GET
    @Path("/query")
    @Produces("application/json;charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> testRest(@Context HttpServletRequest request, @Context UriInfo info ){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        try {
            List<TestUserBean> dataList = testService.testService();
            returnMap.put("code", "0");
            returnMap.put("dataList", dataList);
        } catch (Exception e) {
            e.printStackTrace();
            loger.error(e.getMessage());
            returnMap.put("messeage", "失败！");
            return returnMap;
        }
        return returnMap;
    }
    
    /**
     * 测试session
     * @param request
     * @param info
     * @return
     */
    @GET
    @Path("/testSession")
    @Produces("application/json;charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> testSessionRest(@Context HttpServletRequest request, @Context UriInfo info ){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        try {
            HttpSession session = request.getSession(false);
            returnMap.put("userInfo", JSONObject.toJSON(session.getAttribute("userInfo")));
            returnMap.put("server", "tomcat:sd_base");
            returnMap.put("sessionId", session.getId());
            returnMap.put("messeage", "成功！");
        } catch (Exception e) {
            e.printStackTrace();
            loger.error(e.getMessage());
            returnMap.put("messeage", "失败！");
            return returnMap;
        }
        return returnMap;
    }
}
