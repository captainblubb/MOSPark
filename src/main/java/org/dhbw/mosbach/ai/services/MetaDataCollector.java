package org.dhbw.mosbach.ai.services;

import org.dhbw.mosbach.ai.db.ParkingAreaDAO;
import org.dhbw.mosbach.ai.db.ParkingStatisticDAO;
import org.dhbw.mosbach.ai.model.ParkingArea;
import org.dhbw.mosbach.ai.model.ParkingStatistics;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;


@Singleton
public class MetaDataCollector {

    @Inject
    ParkingAreaDAO parkingAreaDAO;

    @Inject
    ParkingStatisticDAO parkingStatisticDAO;


    @Schedule(second = "0", minute = "*/1", hour = "*", persistent = false )
    public void atSchedule() throws InterruptedException {
        System.out.println("_______________Scheduled Task Meta Collector");

        try {

        List<ParkingArea> parkingAreas = parkingAreaDAO.getAll();

        for (ParkingArea pa :parkingAreas) {

            int freeSpots = parkingAreaDAO.getFreeParkingSpots(pa);

            int totalSpots = pa.getTotalSpots();

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());


            ParkingStatistics parkingStatistics = new ParkingStatistics();
            parkingStatistics.setTimestamp(timestamp);
            parkingStatistics.setFreeParkingSpots(freeSpots);
            parkingStatistics.setMaximumParkingSpots(totalSpots);
            parkingStatistics.setParkingArea(pa);

            parkingStatisticDAO.persist(parkingStatistics);

            System.out.println("_____PARKING STATISTIC PERSISTED______");


        }

        }catch (Exception exp){

            System.out.println("FAILED PARKING STATISTIC ");
        }


    }


}
