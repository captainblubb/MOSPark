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

    /***
     * Erstellt eine Notification, von einem User an einen User mit bestimmter message
     * @param userFrom
     * @param userTo
     * @param message
     * @return
     */
    public boolean createNotification(String content, User userFrom, User userTo, String message) {

        Notification notification = new Notification();

        if (userFrom != null && userTo != null && message != null && !message.equals("")) {

            notification.setUserFROM(userFrom);
            notification.setUserTO(userTo);
            notification.setNotification(message);
            notification.setContent(content);
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


    /***
     * Gibt alle Notifications zurück anhand des Users der sie empfangen soll
     * @param userTo
     * @return
     */
    public List<Notification> getNotification(User userTo){

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

    /***
     * Gibt eine Notification anhand ihrer ID zurück
     * @param notifcationID
     * @return
     */
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

    /***
     * löscht eine Notification aus der DB
     * @param notification
     * @return
     */
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
}
