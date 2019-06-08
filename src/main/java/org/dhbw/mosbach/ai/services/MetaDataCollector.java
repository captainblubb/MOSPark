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


    private Calendar mapTimestampTo15(Calendar timestamp){
        //since timestamp.getMinutes depricated
        Calendar calendar = (Calendar)timestamp.clone();

        int minute =  -1;
        boolean hour = false;

        if ( calendar.get(Calendar.MINUTE)<= 7){

            minute = 0;

        }else if (calendar.get(Calendar.MINUTE)>7 && calendar.get(Calendar.MINUTE) <22){

            minute = 15;

        }else if (calendar.get(Calendar.MINUTE)>=22 && calendar.get(Calendar.MINUTE) < 38){

            minute = 30;

        }else if (calendar.get(Calendar.MINUTE)>=38 && calendar.get(Calendar.MINUTE) <=52){
            minute = 45;
        }else if (calendar.get(Calendar.MINUTE)>52){
            hour=true;
            minute = 0;
        }

        if (hour){
            calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY)+1);
        }
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        return calendar;

    }


    public int getHourFrom() {
        return hourFrom;
    }

    public int getHourTo() {
        return hourTo;
    }
}
