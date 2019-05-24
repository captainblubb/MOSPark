package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.Notification;
import org.dhbw.mosbach.ai.model.ParkingSpot;
import org.dhbw.mosbach.ai.model.User;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;


@Named
@Dependent
public class NotificationDAO extends BaseDao<Notification,Long> {

    public NotificationDAO(){
        super();
    }

    public boolean createNotification(User userFrom, User userTo, String message) {

        Notification notification = new Notification();

        if (userFrom != null && userTo != null && message != null && !message.equals("")) {

            notification.setUserFROM(userFrom);
            notification.setUserTO(userTo);
            notification.setNotification(message);
            try {
                persist(notification);
                return true;

            } catch (Exception exp) {
                System.out.println("EXP when creating notification" + exp);
                return false;
            }

        } else {
            return false;
        }
    }

    public List<Notification> getNotificationsOfUser(User userTo){

        List<Notification> notifications = new ArrayList<>();

        try {
        final String query = "from Notification e where e.UserTo.id = "+userTo.getId();
        //WORKS ->//final String query = "from ParkingSpot e where e.parkingArea.id = "+parkingArea.getId();
        System.out.println("_QUERY FOR PARKINGSPOTS: "+query);
        notifications = (List<Notification>) cacheable(em.createQuery(query, entityClass)).getResultList();


        }catch (Exception exp){
            System.out.println("Failed loading Notification "+exp);
        }

        return notifications;
    }

    public Notification getNotifcation(Long notifcationID){

        if (notifcationID!=null){
            try {

                return findById(notifcationID);

            } catch (Exception exp) {
                System.out.println("Failed loading Notification " + exp);
            }
        }

        return null;
    }

    public boolean deleteNotifcation(Notification notification) {
        if (notification!=null) {
            try {

                remove(notification);
                return true;

            } catch (Exception exp) {
                System.out.println("Failed loading Notification " + exp);
                return false;
            }
        }
        return false;
    }

    public List<Notification> getAllNotifications(){

        try{
            return getAll();
        }catch (Exception exp){
            System.out.println(" Failed to load all notifications "+exp);
        }
        return null;
    }
}
