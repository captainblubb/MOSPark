package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.ParkingArea;
import org.dhbw.mosbach.ai.model.ParkingSpot;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Named
@Dependent
public class ParkingSpotDAO extends BaseDao<ParkingSpot,Long> {

    public ParkingSpotDAO(){
        super();
    }

    public boolean createParkingSpot(ParkingArea parkingArea,int position){
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setPosition(position);
        parkingSpot.setParkingArea(parkingArea);
        System.out.println("Persist or merge create Parking Spot");
        persistOrMerge(parkingSpot);
        return true;

    }

    public List<ParkingSpot> getParkingSpotsByArea(ParkingArea parkingArea){

        List<ParkingSpot> parkingSpots= new ArrayList<>();
        try{

            if (super.em!= null) {


                final String query = "from ParkingSpot e where e.parkingArea.id = "+parkingArea.getId();
                //WORKS ->//final String query = "from ParkingSpot e where e.parkingArea.id = "+parkingArea.getId();
                System.out.println("_QUERY FOR PARKINGSPOTS: "+query);
                parkingSpots = (List<ParkingSpot>) cacheable(em.createQuery(query, entityClass)).getResultList();


            }else {
                System.out.println("__X__X_X_XX EM NULL N Parking SPOT DAO");
            }
        }catch (Exception exp){
            System.out.println(" _______parkingArea_id failed"+exp);

        }
        return parkingSpots;
    }
}
