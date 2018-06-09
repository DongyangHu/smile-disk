package com.sd.fm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test")
public class TestController {
    
    
    
    
    @RequestMapping(value = "demo1", method = RequestMethod.GET ,produces= "text/plain;charset=UTF-8")
    public @ResponseBody String uploadFile(HttpServletRequest request, HttpServletResponse response) {
        String param = request.getParameter("data");
        System.out.println("hello world!" + param);
        
        
        return null;
    }
}
