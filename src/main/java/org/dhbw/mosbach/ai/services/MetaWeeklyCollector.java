package org.dhbw.mosbach.ai.services;

import org.checkerframework.checker.units.qual.C;
import org.dhbw.mosbach.ai.db.ParkingStatisticDAO;
import org.dhbw.mosbach.ai.tools.SQLKonverterTool;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

@Singleton
public class MetaWeeklyCollector {

    @Inject
    ParkingStatisticDAO parkingStatisticDAO;


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

        for (int dayInt = 0; dayInt < 5;dayInt++){

            String day = SQLKonverterTool.intToDayString(dayInt);

            for (int hour =MetaDataCollector.hourFrom; hour <= MetaDataCollector.hourTo; hour++){



                for (int minute = 0; minute < 60; minute+=MetaDataCollector.everyMin){





                }

            }

        }

    }

}