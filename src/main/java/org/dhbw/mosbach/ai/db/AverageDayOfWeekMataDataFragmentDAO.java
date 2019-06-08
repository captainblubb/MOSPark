package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.MetaData.AveragageByDay.AverageDayOfWeekMetaDataFragment;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.time.DayOfWeek;
import java.util.List;

@Named
@Dependent
public class AverageDayOfWeekMataDataFragmentDAO extends BaseDao<AverageDayOfWeekMetaDataFragment, Long> {

    public AverageDayOfWeekMataDataFragmentDAO(){
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
    public AverageDayOfWeekMetaDataFragment getByTimestamp(String dayOfWeek, int hour, int minute ) throws Exception{

        AverageDayOfWeekMetaDataFragment averageDayOfWeekMetaDataFragment = null;

            if (super.em!= null) {

                final String query =
                        "SELECT * FROM ParkingStatistics p"+
                                " WHERE DAY(p.timestamp) ="+dayOfWeek+
                                " AND HOUR(p.timestamp) ="+hour+
                                " AND MINUTE(p.timestamp) ="+minute;

                System.out.println("_QUERY FOR ParkingStatistics: "+query);
                List<AverageDayOfWeekMetaDataFragment> averageDayOfWeekMetaDataFragments = ((List<AverageDayOfWeekMetaDataFragment>) cacheable(em.createQuery(query, entityClass)).getResultList());

                if (averageDayOfWeekMetaDataFragments.size()>0){
                    averageDayOfWeekMetaDataFragment = averageDayOfWeekMetaDataFragments.get(0);
                }
            }else {
                System.out.println("ParkingStatistics em = null ");
                throw new NullPointerException();
            }

        return averageDayOfWeekMetaDataFragment;

    }


    private String intToDayString(int day){

        return DayOfWeek.of(day+1).toString();

    }
}
