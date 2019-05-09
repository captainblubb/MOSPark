package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.ParkingSpot;

import javax.enterprise.context.Dependent;
import javax.inject.Named;


@Named
@Dependent
public class ParkingspotDAO extends BaseDao<ParkingSpot,Long> {

    public ParkingspotDAO(){
        super();
    }

    public boolean createParkingSpot(){

        ParkingSpot parkingSpot = new ParkingSpot();


        return true;

    }
}
