package org.dhbw.mosbach.ai.services;

import org.checkerframework.checker.units.qual.C;
import org.dhbw.mosbach.ai.db.AverageDayMetaDataDAO;
import org.dhbw.mosbach.ai.db.DailyAverageMetaDataFragmentDAO;
import org.dhbw.mosbach.ai.db.ParkingAreaDAO;
import org.dhbw.mosbach.ai.db.ParkingStatisticDAO;
import org.dhbw.mosbach.ai.model.AverageDayMetaData;
import org.dhbw.mosbach.ai.model.AverageDayMetaDataFragment;
import org.dhbw.mosbach.ai.model.ParkingArea;
import org.dhbw.mosbach.ai.model.ParkingStatistics;
import org.dhbw.mosbach.ai.tools.SQLKonverterTool;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.lang.reflect.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.*;

import static org.dhbw.mosbach.ai.tools.SQLKonverterTool.mapTimestampTo15;

@Singleton
public class MetaWeeklyCollector {

    @Inject
    ParkingStatisticDAO parkingStatisticDAO;

    @Inject
    ParkingAreaDAO parkingAreaDAO;

    @Inject
    DailyAverageMetaDataFragmentDAO dailyAverageMetaDataFragmentDAO;

    @Inject
    AverageDayMetaDataDAO averageDayMetaDataDAO;


    /***
     * Jeden Sonntag werden die durchschnittlichen Werte für jeden Wochentag erzeugt. Für jeden Wochentag
     * wird aus den vergangen x Tagen (aktuell 14) die freien Parkplätze wochentags spezifisch
     * berechnet um eine Tendenz für die nächste Woche angeben zu können.
     *
     * @throws InterruptedException
     */
    //Midnights on sunday -> Collect Average of last 14 days
    //@Schedule(second = "0", minute = "0", hour = "0", dayOfWeek = "Sun", persistent = false)
    @Schedule(second = "0", minute = "*/3", hour = "*", persistent = false )
    public void atSchedule() throws InterruptedException {

        /*
            1. Get All ParkingStatisstics of last 2 weeks
            2. get All AverageDayMetaDataFragment (Mo-Fr)
            3. Calculate for every 15 minutes average of each Day
         */
        try {

            Calendar calenderNow = mapTimestampTo15(Calendar.getInstance(Locale.GERMANY));

            List<ParkingArea> parkingAreas = parkingAreaDAO.getAll();

            for (ParkingArea parkingArea: parkingAreas) {

                for (int dayInt = 0; dayInt < 5; dayInt++) {

                    String day = SQLKonverterTool.intToDayString(dayInt);

                    // Get All Parking statistics of specific day of the week, Mo-Fr, of one parking Area!
                    List<ParkingStatistics> parkingStatics = parkingStatisticDAO.getParkingStatics(day, MetaDataConfiguration.ofLastDays, parkingArea.getName());;

                    //For each day a AverageDayMetaData is created, where all
                    //AverageDayMetaData fragments of the specific day are referenzed to
                    AverageDayMetaData averageDayMetaData = new AverageDayMetaData();
                    averageDayMetaData.setTimestamp(calenderNow);
                    averageDayMetaData.setParkingArea(parkingArea);

                    //persist
                    averageDayMetaDataDAO.persist(averageDayMetaData);

                    for (int hour = MetaDataConfiguration.hourFrom; hour <= MetaDataConfiguration.hourTo; hour++) {

                        //Found will be saved in here, average is calculated by those and saved as MetaDataWeekly Fragment
                        ArrayList<ParkingStatistics> foundParkingStatistics = new ArrayList<>();

                        for (int minute = 0; minute < 60; minute += MetaDataConfiguration.everyMin) {
                            //Get Parkingstatistic for day,hour,minute
                            for (ParkingStatistics parkStat: parkingStatics) {
                                Calendar calendar = parkStat.getTimestamp();

                                if (calendar.get(Calendar.HOUR) == hour && calendar.get(Calendar.DAY_OF_WEEK) == dayInt && calendar.get(Calendar.MINUTE)==minute){
                                    foundParkingStatistics.add(parkStat);
                                }

                            }

                            if (foundParkingStatistics.size() >0) {
                                // Calculate free spots
                                int freeSlotsAll = 0;
                                for (ParkingStatistics pStat : foundParkingStatistics) {
                                    freeSlotsAll+=pStat.getFreeParkingSpots();
                                }

                                int freeSpotsAvg = (int)((double)freeSlotsAll/(double)foundParkingStatistics.size());

                                //Save as Fragment
                                AverageDayMetaDataFragment metaDataFragment = new AverageDayMetaDataFragment();
                                metaDataFragment.setAverageDayMetaData(averageDayMetaData);
                                metaDataFragment.setTimestamp(foundParkingStatistics.get(0).getTimestamp());
                                metaDataFragment.setParkingArea(parkingArea);
                                metaDataFragment.setFreeSpots(freeSpotsAvg);

                                //Persist
                                dailyAverageMetaDataFragmentDAO.persist(metaDataFragment);

                            }
                        }

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}