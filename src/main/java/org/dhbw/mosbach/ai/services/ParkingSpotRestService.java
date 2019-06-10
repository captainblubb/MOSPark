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
@Path("/parkingspots/")
public class ParkingSpotRestService
{
    @Inject
    private ParkingSpotDAO parkingSpotDao;
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
    public List<ParkingSpot> getParkingSpots()
    {
        if (request.getUserPrincipal() == null)
        {
            throw new WebApplicationException("not logged in", Response.Status.FORBIDDEN);
        }
        try{
            final List<ParkingSpot> allParkingSpots = parkingSpotDao.getAllParkingSpots();
            return allParkingSpots;
        }
        catch(NullPointerException e){
            throw new NullPointerException("No parkingspots!");
        }
    }

    @POST
    //@PATH("/occupy")
    @Consumes(MediaType.TEXT_XML)
    public void occupyParkingSpot(ParkingSpot parkingSpot)
    {
        if (request.getUserPrincipal() == null)
        {
            throw new WebApplicationException("not logged in", Response.Status.FORBIDDEN);
        }
        if(parkingSpot!=null){
            String username = request.getUserPrincipal().getName();
            User user = userDao.getUserByUsername(username);

            parkingSpotDao.parkUserOnParkingSpot(parkingSpot, user);
        }
        else{
            System.out.println("Invalid parking spot!");
        }
    }

    @POST
    //@PATH("/free")
    @Consumes(MediaType.TEXT_XML)
    public void freeParkingSpot(ParkingSpot parkingSpot)
    {
        if (request.getUserPrincipal() == null)
        {
            throw new WebApplicationException("not logged in", Response.Status.FORBIDDEN);
        }

        String username = request.getUserPrincipal().getName();
        User user = userDao.getUserByUsername(username);

        parkingSpotDao.parkOutUser(user);
    }
}
