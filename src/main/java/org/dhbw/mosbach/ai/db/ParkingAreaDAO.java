package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.ParkingArea;
import org.dhbw.mosbach.ai.model.ParkingSpot;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
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

    public boolean createParkingArea(Long id, String name){

        ParkingArea parkingArea = new ParkingArea();
        parkingArea.setId(id);
        parkingArea.setName(name);
        persistOrMerge(parkingArea);

        return true;
    }

    public int getFreeParkingSpots(ParkingArea parkingArea){

        try{

        List<ParkingSpot> parkingSpots = parkingspotDAO.getAllByField("parkingArea_id",parkingArea.getId());

        int free =0;
        for (ParkingSpot parkSpot:parkingSpots) {
            if (parkSpot.getUser()!=null){
                free++;
            }
        }

        return free;

        }catch (Exception exp){
            System.out.println("___FAILED Count free Parking Spots___: "+exp);
        }

        return -1;
    }





}
