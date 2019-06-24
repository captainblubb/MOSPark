package org.dhbw.mosbach.ai.services;

import org.dhbw.mosbach.ai.db.NotificationDAO;
import org.dhbw.mosbach.ai.db.UserDAO;
import org.dhbw.mosbach.ai.model.Notification;
import org.dhbw.mosbach.ai.model.ParkingSpot;
import org.dhbw.mosbach.ai.model.User;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("/notifications")
public class NotificationRestService
{
    @Inject
    private NotificationDAO notificationDao;
    @Inject
    private UserDAO userDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private HttpServletRequest request;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed("admin")
    public List<Notification> getAllNotifications()
    {
        if (request.getUserPrincipal() == null)
        {
            throw new WebApplicationException("not logged in", Response.Status.FORBIDDEN);
        }
        final List<Notification> allNotifications = notificationDao.getAllNotifications();
        return allNotifications;
    }

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<Notification> getNotificationsOfCurrentUser()
    {
        if (request.getUserPrincipal() == null)
        {
            throw new WebApplicationException("not logged in", Response.Status.FORBIDDEN);
        }

        String userName = request.getUserPrincipal().getName();
        User user = userDao.getUserByUsername(userName);

        return notificationDao.getNotificationsOfUser(user);
    }

    @POST
    @Path("/notify")
    @Consumes(MediaType.TEXT_XML)
    public void notifyUsers(List<ParkingSpot> parkingSpots)
    {
        if (request.getUserPrincipal() == null)
        {
            throw new WebApplicationException("not logged in", Response.Status.FORBIDDEN);
        }

        String username = request.getUserPrincipal().getName();
        User userFrom = userDao.getUserByUsername(username);
        try{
            for (ParkingSpot parkingSpot:parkingSpots){
                notificationDao.createNotification(userFrom, parkingSpot.getUser(), " Please return to your car, i want to leave.");
            }
        }
        catch(NullPointerException e){
            throw new NullPointerException("No parking spots selected!");
        }
    }

    @POST
    @Path("/dismiss")
    @Consumes(MediaType.TEXT_XML)
    public void dismissNotification(Long notificationID){

        if(request.getUserPrincipal() == null){
            throw new WebApplicationException("not logged in", Response.Status.FORBIDDEN);
        }

        if(notificationID!=null){
            // Notification notification = notificationDao.getNotifcation(notificationID);
            // notification.setDismissed(true);
        }
        else throw new NullPointerException("Invalid notification ID!");

        System.out.println("Notification dismissed.");
    }
}

