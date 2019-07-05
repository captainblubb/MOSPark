package org.dhbw.mosbach.ai.services;

import io.jsonwebtoken.ExpiredJwtException;
import org.dhbw.mosbach.ai.db.NotificationDAO;
import org.dhbw.mosbach.ai.db.ParkingSpotDAO;
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

import java.util.ArrayList;
import java.util.List;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.dhbw.mosbach.ai.services.models.NotifyUsersObject;

import java.security.Key;
import java.util.Map;

@ApplicationScoped
@Path("notifications")
public class NotificationRestService
{
    @Inject
    private NotificationDAO notificationDao;
    @Inject
    private UserDAO userDao;
    @Inject
    private ParkingSpotDAO parkingSpotDAO;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private HttpServletRequest request;

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed("admin")
    public List<Notification> getAllNotifications()
    {
        try{
            final List<Notification> allNotifications = notificationDao.getAll();
            return allNotifications;
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
        return null;
    }

    @POST
    @Path("user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<String> getNotificationsOfCurrentUser(Map<String, Long> map)
    {
        Long userID = map.get("userId");
        User user = userDao.getUserById(userID);
        try{
            List<Notification> notifications = notificationDao.getAllByField("name",user.getName());
            List<String> ids = new ArrayList<>();

            ids.add("userID: "+user.getId());
            for(Notification notification:notifications){
                ids.add("notificationID: "+notification.getId());
            }
            return ids;
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
        return null;
    }

    @POST
    @Path("notify")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void notifyUsers(NotifyUsersObject object)
    {
        Long userID = object.userID;
        User userFrom = userDao.getUserById(userID);

        List<ParkingSpot> parkingSpots=new ArrayList<>();

        for(Long l:object.ids){
            parkingSpots.add(parkingSpotDAO.getParkingSpotByID(l));
        }

        try{
            for (ParkingSpot parkingSpot:parkingSpots){
                notificationDao.createNotification("MOVE ORDER",userFrom, parkingSpot.getUser(), "Please return to your car, i want to leave.");
            }
        }
        catch(NullPointerException e){
            throw new NullPointerException("No parking spots selected!");
        }
    }


    @POST
    @Path("dismiss")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void dismissNotification(Map<String, Long> map){
        Long notificationId = map.get("notificationId");
        if(notificationId!=null){
            Notification notification = notificationDao.getNotifcation(notificationId);
            notification.setDissmissed(true);
            try{
                notificationDao.persist(notification);
            }
            catch(Exception exp){
                exp.printStackTrace();
            }
        }
        else throw new NullPointerException("Invalid notification ID!");

        System.out.println("Notification dismissed.");
    }
}

