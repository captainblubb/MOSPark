package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.DailyAverageMetaData;
import org.dhbw.mosbach.ai.model.ParkingStatistics;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class DailyAverageMetaDataDAO extends BaseDao<DailyAverageMetaData, Long> {

    public DailyAverageMetaDataDAO(){
        super();
    }

    /***
     * Gibt die durschnittliche Parkplatz Situation zu der Kombination Tag:Stunde:Minute zurück
     *
     * @param dayOfWeek
     * @param hour
     * @param minute
     * @return
     * @throws Exception
     */
    public DailyAverageMetaData getByTimestamp(String dayOfWeek, int hour, int minute ) throws Exception{

        DailyAverageMetaData dailyAverageMetaData= null;

            if (super.em!= null) {


                final String query =
                        "SELECT * FROM ParkingStatistics p"+
                                " WHERE DAY(p.timestamp) ="+dayOfWeek+
                                " AND HOUR(p.timestamp) ="+hour+
                                " AND MINUTE(p.timestamp) ="+minute;

                System.out.println("_QUERY FOR ParkingStatistics: "+query);
                List<DailyAverageMetaData> dailyAverageMetaDatas = ((List<DailyAverageMetaData>) cacheable(em.createQuery(query, entityClass)).getResultList());

                if (dailyAverageMetaDatas.size()>0){
                    dailyAverageMetaData = dailyAverageMetaDatas.get(0);
                }
            }else {
                System.out.println("ParkingStatistics em = null ");
                throw new NullPointerException();
            }

        return dailyAverageMetaData;

    }


    private String intToDayString(int day){

        return DayOfWeek.of(day+1).toString();

    }
}
