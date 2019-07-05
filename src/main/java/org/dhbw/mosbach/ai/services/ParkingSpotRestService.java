package org.dhbw.mosbach.ai.services;


import org.dhbw.mosbach.ai.db.ParkingSpotDAO;
import org.dhbw.mosbach.ai.db.UserDAO;
import org.dhbw.mosbach.ai.model.ParkingSpot;
import org.dhbw.mosbach.ai.model.User;
import org.dhbw.mosbach.ai.services.models.ParkingSpotObject;

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
import java.util.Map;

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
    public List<ParkingSpotObject> getParkingSpots()
    {
        try{
            final List<ParkingSpot> allParkingSpots = parkingSpotDao.getAll();
            List<ParkingSpotObject> parkingSpotObjects = new ArrayList<>();
            for (ParkingSpot parkingSpot: allParkingSpots){
                ParkingSpotObject object = new ParkingSpotObject();
                object.id = parkingSpot.getId();
                if(parkingSpot.getUser()!=null) {
                    object.userId = parkingSpot.getUser().getId();
                }else {
                    object.userId = -1l;
                }
                object.areaId = parkingSpot.getParkingArea().getId();
                object.column = parkingSpot.getPAreaColumn();
                object.row = parkingSpot.getPAreaRow();
                parkingSpotObjects.add(object);
            }
            return parkingSpotObjects;
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
    @Transactional
    public void occupyParkingSpot(Map<String, Long> map)
    {
        Long userID = map.get("userID");
        Long parkingSpotID = map.get("parkingSpotID");
        ParkingSpot parkingSpot= parkingSpotDao.getParkingSpotByID(parkingSpotID);
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
    @Transactional
    public void freeParkingSpot(Map<String, Long> map)
    {
        Long parkingSpotID = map.get("parkingSpotId");
        ParkingSpot parkingSpot = parkingSpotDao.getParkingSpotByID(parkingSpotID);

        User user = parkingSpot.getUser();

        System.out.println("__TRYING PARKING OUT USER: "+user.getName()+" FROM PARKINGSPOT: "+parkingSpot.getId());
        boolean bool = parkingSpotDao.parkOutUser(user);
        System.out.println("__PARKING OUT USER SUCCESSFUL: "+bool);
        try{
            System.out.println("__TRYING PERSIST");
            parkingSpotDao.persist(parkingSpot);
            System.out.println("__PERSIST SUCCESSFUL");
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
    }
}
