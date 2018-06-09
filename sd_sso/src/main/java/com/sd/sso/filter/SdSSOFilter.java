package com.sd.sso.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * Servlet Filter implementation class SdSSOFilter
 */
public class SdSSOFilter implements Filter {

    private static Log logger = LogFactory.getLog(SdSSOFilter.class);
    private String forwardUrl = null;
    private String noCheckUrl = null;
    
    
	public void destroy() {
	    
	}

	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	    
	    HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;
	    HttpSession session = request.getSession(false);
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json; charset=utf-8");
	    
	    if(null != noCheckUrl && !"".equals(noCheckUrl)) {
	        String requestURI = request.getRequestURI();
	        if(isNoCheckUrl(requestURI)) {
	            logger.info("noCheckUrl: " + requestURI);
	            chain.doFilter(request, response);
	        }else {
	            if(null == session) {
	                logger.info("------session is not exsits------");
	                
	                String returnString = getReturnString();
	                response.getWriter().write(returnString);
	                //response.sendRedirect(forwardUrl);
	            }else {
	                Object userInfo = WebUtils.getSessionAttribute(request, SdSessionConstant.SESSION_USER_INFO);
	                if(null == userInfo) {
	                    logger.info("sessionId:" + session.getId());
	                    logger.info("------userInfo is not exsits------");
	                    String returnString = getReturnString();
	                    response.getWriter().write(returnString);
	                    //response.sendRedirect(forwardUrl);
	                }else {
	                    logger.info("sessionId:" + session.getId());
	                    chain.doFilter(request, response);
	                }
	            }
	        }
	    }else {
            if(null == session) {
                logger.info("------session is not exsits------");
                
                String returnString = getReturnString();
                response.getWriter().write(returnString);
                //response.sendRedirect(forwardUrl);
            }else {
                Object userInfo = WebUtils.getSessionAttribute(request, SdSessionConstant.SESSION_USER_INFO);
                if(null == userInfo) {
                    logger.info("sessionId:" + session.getId());
                    logger.info("------userInfo is not exsits------");
                    String returnString = getReturnString();
                    response.getWriter().write(returnString);
                    //response.sendRedirect(forwardUrl);
                }else {
                    logger.info("sessionId:" + session.getId());
                    chain.doFilter(request, response);
                }
            }	        
	    }
	    
	}

	public boolean isNoCheckUrl(String uri) {
	    StringTokenizer st = new StringTokenizer(noCheckUrl, ",");
	    while(st.hasMoreTokens()) {
	        if(uri.equals(st.nextToken())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
	    
	    //初始化不需要登录就可以执行的接口
	    this.noCheckUrl = fConfig.getInitParameter("noCheckUrl");
	    
	    //获取跳转的地址
        InputStream inStream = SdSSOFilter.class.getClassLoader().getResourceAsStream("sso.properties");
        Properties props = new Properties();
        try {
            if(inStream != null) {
                props.load(inStream);
                forwardUrl = props.getProperty("ssoLoginUrl", null);
            }
            else {
                logger.warn("sso.properties文件不存在");
            }
        } catch (IOException e) {
            logger.warn("读取sso.properties文件异常",e);
        }
	}
	
	private String getReturnString() {
	    JSONObject object = new JSONObject();
	    object.put("code", "e10002");
	    object.put("message", "Permission denied");
	    object.put("serverUrl", forwardUrl);
	    return object.toJSONString();
	}

}
