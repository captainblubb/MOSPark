package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.AverageDayOfWeekMetaData;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Named
@Dependent
public class AverageDayOfWeekMetaDataDAO extends BaseDao<AverageDayOfWeekMetaData,Long> {

    public AverageDayOfWeekMetaDataDAO(){
        super();
    }


}
