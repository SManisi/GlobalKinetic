/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.globalkinetic.webservice;

import com.mycompany.globalkinetic.bo.GlobalBo;
import com.mycompany.globalkinetic.domain.UserDo;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Siyanda
 */
@Path("/GlobalWebService")
public class GlobalKineticWebService {
    
    private static final String SUCCESS = "<result>success</result>";
    private static final String FAIL = "<result>fail</result>";
    
    @GET
    @Path("/createUser")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String createUser(@FormParam("userName") String userName, @FormParam("phoneNumber") String phoneNumber,
                           @FormParam("password") String password, @FormParam("isAdmin") String isAdmin,
                           @Context HttpServletResponse servletResponse ) throws IOException {
        boolean isAdminBoolean = false;
        if (isAdmin.equals("1")) {
            isAdminBoolean = true;
        }
        int userId = GlobalBo.createUser(userName, phoneNumber, password, isAdminBoolean);
        if (userId > 0) {
            return SUCCESS;
        } else {
            return FAIL;
        }
    }
    
    @GET
    @Path("/userLogIn")
    @Produces(MediaType.APPLICATION_XML)
    public boolean userLogIn(@PathParam("userName") String userName, @PathParam("password") String password) {
        return GlobalBo.userLogIn(userName, password);
    }
    
    @GET
    @Path("/retrieveUserDoList")
    @Produces(MediaType.APPLICATION_XML)
    public List<UserDo> retrieveUserDoList(@PathParam("onlyRecentLoggedInUsers") boolean onlyRecentLoggedInUsers) {
        return GlobalBo.retrieveUserDoList(onlyRecentLoggedInUsers);
    }
}
