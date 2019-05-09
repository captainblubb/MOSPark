package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.ParkingArea;

import javax.enterprise.context.Dependent;
import javax.inject.Named;


@Named
@Dependent
public class ParkingAreaDAO extends BaseDao<ParkingArea, Long> {


    public ParkingAreaDAO(){
        super();
    }



}
