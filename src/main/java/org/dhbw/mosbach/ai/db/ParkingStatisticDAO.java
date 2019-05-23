package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.ParkingStatistics;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Named
@Dependent
public class ParkingStatisticDAO extends BaseDao<ParkingStatistics, Long> {

    public ParkingStatisticDAO(){
        super();
    }



}
