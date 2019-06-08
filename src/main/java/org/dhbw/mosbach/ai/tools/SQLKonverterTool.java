package org.dhbw.mosbach.ai.tools;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.Calendar;

public class SQLKonverterTool {


    /***
     * Mappt einen Wochentag als String in einen int, welcher die Wochentage in SQL repräsentiert.
     *
     * @param day
     * @return
     */
    public static int getSQLIntFromDay(String day){

        int daySQL=0;

        switch (day.toLowerCase()){

            case "mon" :
            case "monday" : daySQL=0;
                break;
            case "tue" :
            case "tuesday" :daySQL=1;
                break;

            case "wed" :
            case "wednesday" :daySQL=2;
                break;

            case "thu" :
            case "thursday" :daySQL=3;
                break;

            case "fri" :
            case "friday" :daySQL=4;
                break;

            case "sat" :
            case "saturday" :daySQL=5;
                break;

            case "sun" :
            case "sunday" :daySQL=6;
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

    /***
     *
     * Mapped einen Calendar zeitpunkt zu einem genauen 15 Minuten Schritt
     *
     * YYYY-MM-DD 17:15:02 --> YYYY-MM-DD 17:15:00
     *
     * @param timestamp
     * @return
     */
    public static Calendar mapTimestampTo15(Calendar timestamp){
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

    public static Timestamp convertCalenderToTimestamp(Calendar calendar){
        return new Timestamp(calendar.getTimeInMillis());
    }


}
