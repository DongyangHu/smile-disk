package com.sd.fm.rest;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

@Path("/rest/testRest")
public class TestRest {
    
    @GET
    @Path("/demo1")
    @Produces("application/json; charset=UTF-8")
    @BadgerFish
    public HashMap<String, Object> demo1(@Context HttpServletRequest request, @Context UriInfo info){
        
        System.out.println("hello world!");
        return null;
    }
}
