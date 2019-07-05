package org.dhbw.mosbach.ai.services;

import org.dhbw.mosbach.ai.db.ParkingAreaDAO;
import org.dhbw.mosbach.ai.db.ParkingStatisticDAO;
import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.ParkingArea;
import org.dhbw.mosbach.ai.model.ParkingStatistics;
import org.dhbw.mosbach.ai.tools.SQLKonverterTool;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;



@Singleton
public class MetaDataCollector {

    @Inject
    ParkingAreaDAO parkingAreaDAO;

    @Inject
    ParkingStatisticDAO parkingStatisticDAO;

    /***
     * Alle 15 Minuten zwischen aktuell (8-20 Uhr) werden Metadaten über jede Parking Area und die
     * freien Parkplätze erzeugt, um den Verlauf freier Parkplätze in einem Diagram anzeigen zu können.
     *
     *
     * @throws InterruptedException
     */
    //@Schedule(second = "0", minute = "*/"+MetaDataConfiguration.everyMin, hour = MetaDataConfiguration.hourFrom+"-"+MetaDataConfiguration.hourTo, persistent = false )
    @Schedule(second = "0", minute = "*/1", hour = "*", persistent = false )
    public void atSchedule() throws InterruptedException {


        System.out.println("_______________Scheduled Task Meta Collector");

        try {

        List<ParkingArea> parkingAreas = parkingAreaDAO.getAll();

        for (ParkingArea pa :parkingAreas) {

            int freeSpots = parkingAreaDAO.getFreeParkingSpots(pa);

            int totalSpots = pa.getTotalSpots();

            Calendar calendar = SQLKonverterTool.mapTimestampTo15(Calendar.getInstance(Locale.GERMANY));
            ParkingStatistics parkingStatistics = new ParkingStatistics();
            parkingStatistics.setTimestamp(calendar);
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
