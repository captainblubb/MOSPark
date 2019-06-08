package org.dhbw.mosbach.ai.services;

import org.dhbw.mosbach.ai.db.ParkingAreaDAO;
import org.dhbw.mosbach.ai.db.ParkingStatisticDAO;
import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.ParkingArea;
import org.dhbw.mosbach.ai.model.ParkingStatistics;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static org.dhbw.mosbach.ai.tools.SQLKonverterTool.mapTimestampTo15;


@Singleton
public class MetaDataCollector {

    @Inject
    ParkingAreaDAO parkingAreaDAO;

    @Inject
    ParkingStatisticDAO parkingStatisticDAO;

    public static final int hourFrom = 6;
    public static final int hourTo = 20;
    public static final int everyMin = 1;


    /***
     * Alle 15 Minuten zwischen aktuell (8-18 Uhr) werden Metadaten über jede Parking Area und die
     * freien Parkplätze erzeugt, um den Verlauf freier Parkplätze in einem Diagram anzeigen zu können.
     *
     *
     * @throws InterruptedException
     */
    @Schedule(second = "0", minute = "*/"+everyMin, hour = hourFrom+"-"+hourTo, persistent = false )
    public void atSchedule() throws InterruptedException {


        System.out.println("_______________Scheduled Task Meta Collector");

        try {

        List<ParkingArea> parkingAreas = parkingAreaDAO.getAll();

        for (ParkingArea pa :parkingAreas) {

            int freeSpots = parkingAreaDAO.getFreeParkingSpots(pa);

            int totalSpots = pa.getTotalSpots();

            Calendar calendar = mapTimestampTo15(Calendar.getInstance(Locale.GERMANY));
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

    public int getHourFrom() {
        return hourFrom;
    }

    public int getHourTo() {
        return hourTo;
    }
}
