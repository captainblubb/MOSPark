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
import javax.transaction.Transactional;
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
@Path("user")
public class UserRestService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private HttpServletRequest request;

    @POST
    @Path("register")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void register(String username, String licensePlate, String password)
    {
        try {

            System.out.println("_____________CREATE USER API CALL params:" +username +","+licensePlate+","+password);
            boolean success = userDAO.createUser(username, licensePlate, password);
            System.out.println("______________CREATE USER Result : "+success);
        } catch (Exception e) {
            throw new WebApplicationException("something went wrong...", Response.Status.BAD_REQUEST);
        }
    }

    @POST
    @Transactional
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String login(String username, String password)
    {
        System.out.println("_____________LOGIN USER API CALL params:" +username +","+password);

        boolean userAuth = userDAO.authentificateUser(username, password);

        System.out.println("_____________LOGIN USER API Credentials right? "+userAuth);
        if (userAuth) {

            User user = userDAO.getUserByUsername(username);

            System.out.println("_______GET USER BY NAME id = "+user.getId());

            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

            String jws = Jwts.builder().setSubject(username).signWith(key).compact();

            System.out.println(" Crazy stuff ");
            user.setJsonToken(jws);
            System.out.println(" Crazy stuff persisted");

            try{
                System.out.println(" request login ");
                System.out.println(" request login success");
                userDAO.persist(user);
                System.out.println("____________LOG IN USER SUCCESS");
                return jws;
            }
            catch(Exception exp){
                exp.printStackTrace();
            }
        }
        else {
            System.out.println("Invalid username or password, please try again.");
        }
        return null;
    }

    @POST
    @Path("logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void logout(Long userID){

        User user = userDAO.getUserById(userID);

        user.setJsonToken(null);

        try{
            userDAO.persist(user);
        }
        catch(ServletException exception){
            exception.printStackTrace();
        }
        catch(Exception exp){
            exp.printStackTrace();
        }
    }
}