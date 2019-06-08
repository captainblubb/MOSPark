package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.ParkingSpot;
import org.dhbw.mosbach.ai.model.ParkingStatistics;
import org.dhbw.mosbach.ai.tools.SQLKonverterTool;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Named
@Dependent
public class ParkingStatisticDAO extends BaseDao<ParkingStatistics, Long> {

    public ParkingStatisticDAO(){
        super();
    }

    /***
     * Gibt eine Liste von ParkingStatistics zurück der Vergangenen x Tage (x = ofLastDays),
     * welche die Eigenschaften erfüllt :
     *
     * @param day : Tag als String (Monday,Tuesday...)
     * @param hour : Stunde
     * @param minute : Minute
     * @param ofLastDays : der letzen x Tage
     * @param parkingArea : auf welcher ParkingArea
     *
     * Damit kann man z.b. die Parksituation für alle Dienstag zu einer gewissen Uhrzeit abfragen.
     *
     * @return Liste von ParkingStatistics die die Query erfüllen
     */
    public List<ParkingStatistics> getParkingStatics(String day, int hour, int minute, int ofLastDays, String parkingArea){

        List<ParkingStatistics> parkingStatistics = new ArrayList<>();

        int daySQL = SQLKonverterTool.getSQLIntFromDay(day);

        //Generate Timestamp before X days at 00:00
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)-ofLastDays);
        calendar.set(Calendar.HOUR ,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.MILLISECOND,0);


        /*
            SELECT * FROM ParkingStatistics p
            WHERE timestamp >"+calendar.toString()+"
            AND DAY(p.timestamp) ="+day+"
            AND HOUR(p.timestamp) ="+hour+"
            AND MINUTE(p.timestamp) ="+minute
         */
        try{

            if (super.em!= null) {


                final String query =
                        "SELECT * FROM ParkingStatistics p WHERE timestamp >"+calendar.toString()+
                                " AND p.parkingArea.name ="+parkingArea+
                                " AND DAY(p.timestamp) ="+daySQL+
                                " AND HOUR(p.timestamp) ="+hour+
                                " AND MINUTE(p.timestamp) ="+minute;

                System.out.println("_QUERY FOR ParkingStatistics: "+query);
                parkingStatistics = (List<ParkingStatistics>) cacheable(em.createQuery(query, entityClass)).getResultList();

            }else {
                System.out.println("ParkingStatistics em = null ");
                return new ArrayList<>();
            }
        }catch (Exception exp){
            System.out.println("Failed Parking Statstic query"+exp);
        }

        return parkingStatistics;
    }


    /***
     * Gibt eine Liste von ParkingStatistics zurück der Vergangenen x Tage (x = ofLastDays),
     * welche die Eigenschaften erfüllt :
     *
     * @param hour
     * @param minute
     * @param ofLastDays
     * @param parkingArea
     *
     * @return Liste von ParkingStatistics die die Query erfüllen
     */
    public List<ParkingStatistics> getParkingStatics(int hour, int minute, int ofLastDays,String parkingArea){

        List<ParkingStatistics> parkingStatistics = new ArrayList<>();

        //Generate Timestamp before X days at 00:00
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)-ofLastDays);
        calendar.set(Calendar.HOUR ,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.MILLISECOND,0);


        /*
            SELECT * FROM ParkingStatistics p
            WHERE timestamp >"+calendar.toString()+"
            AND DAY(p.timestamp) ="+day+"
            AND HOUR(p.timestamp) ="+hour+"
            AND MINUTE(p.timestamp) ="+minute
         */
        try{

            if (super.em!= null) {


                final String query =
                        "SELECT * FROM ParkingStatistics p WHERE timestamp >"+calendar.toString()+
                                " AND p.parkingArea.name ="+parkingArea+
                                " AND HOUR(p.timestamp) ="+hour+
                                " AND MINUTE(p.timestamp) ="+minute;

                System.out.println("_QUERY FOR ParkingStatistics: "+query);
                parkingStatistics = (List<ParkingStatistics>) cacheable(em.createQuery(query, entityClass)).getResultList();

            }else {
                System.out.println("ParkingStatistics em = null ");
                return new ArrayList<>();
            }
        }catch (Exception exp){
            System.out.println("Failed Parking Statstic query"+exp);
        }

        return parkingStatistics;
    }


    /***
     * Gibt eine Liste von ParkingStatistics zurück der Vergangenen x Tage (x = ofLastDays),
     * welche die Eigenschaften erfüllt :
     *
     * @param day : Tag als String (Monday,Tuesday...)
     * @param ofLastDays : der letzen x Tage
     * @param parkingArea : auf welcher ParkingArea
     *
     * Damit kann man z.b. die Parksituation für alle Dienstag zu einer gewissen Uhrzeit abfragen.
     *
     * @return Liste von ParkingStatistics die die Query erfüllen
     */
    public List<ParkingStatistics> getParkingStatics(String day, int ofLastDays, String parkingArea){

        List<ParkingStatistics> parkingStatistics = new ArrayList<>();

        int daySQL = SQLKonverterTool.getSQLIntFromDay(day);

        //Generate Timestamp before X days at 00:00
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)-ofLastDays);
        calendar.set(Calendar.HOUR ,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.MILLISECOND,0);

        /*
            SELECT * FROM ParkingStatistics p
            WHERE timestamp >"+calendar.toString()+"
            AND DAY(p.timestamp) ="+day+"
            AND HOUR(p.timestamp) ="+hour+"
            AND MINUTE(p.timestamp) ="+minute
         */
        try{

            if (super.em!= null) {


                final String query =
                        "SELECT *" +
                                " FROM ParkingStatistics p "+
                                " WHERE timestamp >"+calendar.toString()+
                                " AND p.parkingArea.name ="+parkingArea+
                                " AND DAY(p.timestamp) ="+daySQL;

                System.out.println("_QUERY FOR ParkingStatistics: "+query);
                parkingStatistics = (List<ParkingStatistics>) cacheable(em.createQuery(query, entityClass)).getResultList();

            }else {
                System.out.println("ParkingStatistics em = null ");
                return new ArrayList<>();
            }
        }catch (Exception exp){
            System.out.println("Failed Parking Statstic query"+exp);
        }

        return parkingStatistics;
    }

    /***
     * Gibt eine Liste von ParkingStatistics zurück der Vergangenen x Tage (x = ofLastDays),
     * welche die Eigenschaften erfüllt :
     *
     * @param ofLastDays : der letzen x Tage
     * @param parkingArea : auf welcher ParkingArea
     *
     * Damit kann man z.b. die Parksituation für alle Dienstag zu einer gewissen Uhrzeit abfragen.
     *
     * @return Liste von ParkingStatistics die die Query erfüllen
     */
    public List<ParkingStatistics> getParkingStatics(int ofLastDays, String parkingArea){

        List<ParkingStatistics> parkingStatistics = new ArrayList<>();

        //Generate Timestamp before X days at 00:00
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)-ofLastDays);
        calendar.set(Calendar.HOUR ,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.MILLISECOND,0);

        /*
            SELECT * FROM ParkingStatistics p
            WHERE timestamp >"+calendar.toString()+"
            AND DAY(p.timestamp) ="+day+"
            AND HOUR(p.timestamp) ="+hour+"
            AND MINUTE(p.timestamp) ="+minute
         */
        try{

            if (super.em!= null) {


                final String query =
                        "SELECT *" +
                                " FROM ParkingStatistics p "+
                                " WHERE timestamp >"+calendar.toString()+
                                " AND p.parkingArea.name ="+parkingArea;

                System.out.println("_QUERY FOR ParkingStatistics: "+query);
                parkingStatistics = (List<ParkingStatistics>) cacheable(em.createQuery(query, entityClass)).getResultList();

            }else {
                System.out.println("ParkingStatistics em = null ");
                return new ArrayList<>();
            }
        }catch (Exception exp){
            System.out.println("Failed Parking Statstic query"+exp);
        }

        return parkingStatistics;
    }



}



