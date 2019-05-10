package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.ParkingArea;
import org.dhbw.mosbach.ai.model.ParkingSpot;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


@Named
@Dependent
public class ParkingAreaDAO extends BaseDao<ParkingArea, Long> {

    @Inject
    ParkingSpotDAO parkingspotDAO;


    public ParkingAreaDAO(){
        super();
    }

    public boolean createParkingArea(String name, int totalSpots){

        ParkingArea parkingArea = new ParkingArea();
        //parkingArea.setId(id);
        parkingArea.setName(name);
        parkingArea.setTotalSpots(totalSpots);
        persistOrMerge(parkingArea);

        return true;
    }

    public int getFreeParkingSpots(ParkingArea parkingArea) {

        try {

            List<ParkingSpot> parkingSpots= parkingspotDAO.getParkingSpotsByArea(parkingArea);

            int free = 0;
            for (ParkingSpot parkSpot : parkingSpots) {
                if (parkSpot.getUser() == null) {
                    free++;
                }
            }

            return free;

        } catch (Exception exp) {
            System.out.println("___FAILED Count free Parking Spots___: " + exp);
        }

        return -1;
    }

}
