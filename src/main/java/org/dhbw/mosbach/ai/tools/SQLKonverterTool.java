package org.dhbw.mosbach.ai.tools;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class SQLKonverterTool {


    /***
     * Mappt einen Wochentag als String in einen int, welcher die Wochentage in SQL repräsentiert.
     *
     * @param day
     * @return
     */
    public static int getSQLIntFromDay(String day){

        int daySQL=0;

        switch (day){

            case "Mon" :
            case "Monday" : daySQL=0;
                break;
            case "Tue" :
            case "Tuesday" :daySQL=1;
                break;

            case "Wed" :
            case "Wednesday" :daySQL=2;
                break;

            case "Thu" :
            case "Thursday" :daySQL=3;
                break;

            case "Fri" :
            case "Friday" :daySQL=4;
                break;

            case "Sat" :
            case "Saturday" :daySQL=5;
                break;

            case "Sun" :
            case "Sunday" :daySQL=6;
                break;

            default: daySQL= -1;
        }

        return daySQL;
    }

    /***
     * wandelt ein int aus einer SQL Abfrage in einen String des Wochentages um.
     * @param day
     * @return
     */
    public static String intToDayString(int day){

        return DayOfWeek.of(day+1).toString();

    }
}
