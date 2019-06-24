package org.dhbw.mosbach.ai.services;

import org.dhbw.mosbach.ai.db.UserDAO;
import javax.servlet.http.HttpServletRequest;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/user")
public class UserRestService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private HttpServletRequest request;

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public void register(
            @FormParam("username") String username,
            @FormParam("licensePlate") String licensePlate,
            @FormParam("password") String password
    ) {
        try {
            userDAO.createUser(username, licensePlate, password);
        } catch (Exception e) {
            throw new WebApplicationException("something went wrong...", Response.Status.BAD_REQUEST);
        }
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public void login(
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {
        if (userDAO.authentificateUser(username, password)) {
            System.out.println("Password correct, please proceed.");
            //TODO JSON WEBTOKEN
            //TODO USER -> setUserPrincipal() ?
        }
        else {
            System.out.println("Invalid username or password, please try again.");
        }
    }
}
