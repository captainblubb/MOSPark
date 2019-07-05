package org.dhbw.mosbach.ai.services;


import org.dhbw.mosbach.ai.db.ParkingSpotDAO;
import org.dhbw.mosbach.ai.db.UserDAO;
import org.dhbw.mosbach.ai.model.ParkingSpot;
import org.dhbw.mosbach.ai.model.User;

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
@Path("parkingspots")
public class ParkingSpotRestService
{
    @Inject
    private ParkingSpotDAO parkingSpotDao;
    @Inject
    private UserDAO userDao;

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<ParkingSpot> getParkingSpots()
    {

        try{
            final List<ParkingSpot> allParkingSpots = parkingSpotDao.getAll();
            return allParkingSpots;
        }
        catch(NullPointerException e){
            throw new NullPointerException("No parkingspots!");
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
        return null;
    }

    @POST
    @Path("occupy")
    @Consumes(MediaType.APPLICATION_JSON)
    public void occupyParkingSpot(Long userID, ParkingSpot parkingSpot)
    {
        if(parkingSpot!=null && userID!=null){
            User user = userDao.getUserById(userID);
            System.out.println("____________TRYING OCCUPY WITH USER: "+user.getName()+" ON PARKINGSPOT: "+parkingSpot.getId());
            boolean bool = parkingSpotDao.parkUserOnParkingSpot(parkingSpot, user);
            System.out.println("____________OCCUPYING SUCCESSFUL: "+bool);
            try{
                System.out.println("____________TRYING PERSIST");
                parkingSpotDao.persist(parkingSpot);
                System.out.println("____________PERSIST SUCCESSFUL");
            }
            catch (Exception exp){
                exp.printStackTrace();
            }
        }
        else{
            System.out.println("Invalid parking spot!");
        }
    }

    @POST
    @Path("free")
    @Consumes(MediaType.APPLICATION_JSON)
    public void freeParkingSpot(ParkingSpot parkingSpot)
    {
        User user = parkingSpot.getUser();
        System.out.println("____________TRYING PARKING OUT USER: "+user.getName()+" FROM PARKINGSPOT: "+parkingSpot.getId());
        boolean bool = parkingSpotDao.parkOutUser(user);
        System.out.println("____________PARKING OUT USER SUCCESSFUL: "+bool);
        try{
            System.out.println("____________TRYING PERSIST");
            parkingSpotDao.persist(parkingSpot);
            System.out.println("____________PERSIST SUCCESSFUL");
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
    }
}
