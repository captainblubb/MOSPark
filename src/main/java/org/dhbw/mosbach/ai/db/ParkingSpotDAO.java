package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.ParkingArea;
import org.dhbw.mosbach.ai.model.ParkingSpot;

import javax.enterprise.context.Dependent;
import javax.inject.Named;


@Named
@Dependent
public class ParkingSpotDAO extends BaseDao<ParkingSpot,Long> {

    public ParkingSpotDAO(){
        super();
    }

    public boolean createParkingSpot(ParkingArea parkingArea){

        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setParkingArea(parkingArea);
        persistOrMerge(parkingSpot);
        return true;

    }
}
