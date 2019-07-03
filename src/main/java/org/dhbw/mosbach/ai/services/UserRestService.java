package org.dhbw.mosbach.ai.services;

import org.dhbw.mosbach.ai.db.UserDAO;
import org.dhbw.mosbach.ai.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.security.Principal;
import java.util.Set;

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
            User user = userDAO.getUserByUsername(username);

            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

            String jws = Jwts.builder().setSubject(username).signWith(key).compact();

            userDAO.changeLicensePlate(user, jws);

            try{
                request.login(username, password);
                userDAO.persist(user);
            }
            catch(ServletException exception){
                
            }
            catch(Exception exp){

            }
        }
        else {
            System.out.println("Invalid username or password, please try again.");
        }
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public void logout(){

        String username = request.getUserPrincipal().getName();
        User user = userDAO.getUserByUsername(username);

        userDAO.changeLicensePlate(user, null);

        try{
            request.logout();
            userDAO.persist(user);
        }
        catch(ServletException exception){

        }
        catch(Exception exp){

        }
    }
}
