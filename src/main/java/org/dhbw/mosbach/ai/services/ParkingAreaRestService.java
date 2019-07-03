package org.dhbw.mosbach.ai.services;


import org.dhbw.mosbach.ai.db.ParkingAreaDAO;
import org.dhbw.mosbach.ai.model.ParkingArea;

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
@Path("/parkingarea")
public class ParkingAreaRestService
{
    @Inject
    private ParkingAreaDAO parkingAreaDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private HttpServletRequest request;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<ParkingArea> getParkingAreas() throws Exception {
        if (request.getUserPrincipal() == null)
        {
            throw new WebApplicationException("not logged in", Response.Status.FORBIDDEN);
        }
        try{
            final List<ParkingArea> parkingAreas = parkingAreaDao.getParkingAreas();
            return parkingAreas;
        }
        catch(NullPointerException e){
            throw new NullPointerException("No parking areas!");
        }
    }
}
