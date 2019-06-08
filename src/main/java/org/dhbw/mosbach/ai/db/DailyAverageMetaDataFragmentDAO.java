package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.AverageDayMetaDataFragment;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.time.DayOfWeek;
import java.util.List;

@Named
@Dependent
public class DailyAverageMetaDataFragmentDAO extends BaseDao<AverageDayMetaDataFragment, Long> {

    public DailyAverageMetaDataFragmentDAO(){
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
    public AverageDayMetaDataFragment getByTimestamp(String dayOfWeek, int hour, int minute ) throws Exception{

        AverageDayMetaDataFragment averageDayMetaDataFragment = null;

            if (super.em!= null) {


                final String query =
                        "SELECT * FROM ParkingStatistics p"+
                                " WHERE DAY(p.timestamp) ="+dayOfWeek+
                                " AND HOUR(p.timestamp) ="+hour+
                                " AND MINUTE(p.timestamp) ="+minute;

                System.out.println("_QUERY FOR ParkingStatistics: "+query);
                List<AverageDayMetaDataFragment> averageDayMetaDataFragments = ((List<AverageDayMetaDataFragment>) cacheable(em.createQuery(query, entityClass)).getResultList());

                if (averageDayMetaDataFragments.size()>0){
                    averageDayMetaDataFragment = averageDayMetaDataFragments.get(0);
                }
            }else {
                System.out.println("ParkingStatistics em = null ");
                throw new NullPointerException();
            }

        return averageDayMetaDataFragment;

    }


    private String intToDayString(int day){

        return DayOfWeek.of(day+1).toString();

    }
}
