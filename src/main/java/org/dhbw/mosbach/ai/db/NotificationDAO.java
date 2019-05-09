package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.Notification;

import javax.enterprise.context.Dependent;
import javax.inject.Named;


@Named
@Dependent
public class NotificationDAO extends BaseDao<Notification,Long> {

    public NotificationDAO(){
        super();
    }
}
