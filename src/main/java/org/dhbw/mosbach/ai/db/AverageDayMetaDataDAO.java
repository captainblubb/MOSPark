package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.AverageDayMetaData;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Named
@Dependent
public class AverageDayMetaDataDAO extends BaseDao<AverageDayMetaData,Long> {

    public AverageDayMetaDataDAO(){
        super();
    }




}
