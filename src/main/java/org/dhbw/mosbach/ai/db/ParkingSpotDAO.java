package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.model.ParkingArea;
import org.dhbw.mosbach.ai.model.ParkingSpot;
import org.dhbw.mosbach.ai.model.User;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;


@Named
@Dependent
public class ParkingSpotDAO extends BaseDao<ParkingSpot,Long> {

    @Inject
    UserDAO userDAO;

    public ParkingSpotDAO(){
        super();
    }


    /***
     * Erstellt ein Parking Spot, used by DataProvider.
     *
     * @param parkingArea
     * @param column
     * @param row
     * @return
     */
    public boolean createParkingSpot(ParkingArea parkingArea,int column, int row){

        try {
            ParkingSpot parkingSpot = new ParkingSpot();
            parkingSpot.setPAreaRow(column);
            parkingSpot.setPAreaColumn(row);
            parkingSpot.setParkingArea(parkingArea);
            System.out.println("Persist or merge create Parking Spot");
            persistOrMerge(parkingSpot);


            return true;
        }catch (Exception exp){
            System.out.println("failed creating Parkign Spot "+exp);
        }
        return false;

    }

    /***
     * Gibt alle Parking Spots zurückd die einer bestimmten Parkign Area untergeordnet sind
     *
     * @param parkingArea
     * @return
     */
    public List<ParkingSpot> getParkingSpotsByArea(ParkingArea parkingArea){

        List<ParkingSpot> parkingSpots= new ArrayList<>();
        try{

            if (super.em!= null) {


                final String query = "from ParkingSpot e where e.parkingArea.id = "+parkingArea.getId();
                //WORKS ->//final String query = "from ParkingSpot e where e.parkingArea.id = "+parkingArea.getId();
                System.out.println("_QUERY FOR PARKINGSPOTS: "+query);
                parkingSpots = (List<ParkingSpot>) cacheable(em.createQuery(query, entityClass)).getResultList();


            }else {
                System.out.println("__X__X_X_XX EM NULL N Parking SPOT DAO");
            }
        }catch (Exception exp){
            System.out.println(" _______parkingArea_id failed"+exp);

        }
        return parkingSpots;
    }


    /***
     * Einen User ausparken, auf jenem parkplatz auf der er sich gerade befindet.
     *
     * @param user
     * @return
     */
    public boolean parkOutUser(User user){

        try {

            ParkingSpot parkingSpot = user.getParkingSpot();
            parkingSpot.setUser(null);
            user.setParkingSpot(null);

            userDAO.persistOrMerge(user);
            persistOrMerge(parkingSpot);

            return true;
        }catch (Exception exp){

            System.out.println("failed parking out user "+exp);
            return false;
        }

    }

    /***
     * Gibt den user zurück, welcher sich auf einem betimmten Parkplatz befindet
     *
     * @param position
     * @return
     */
    public User getUserByParkingPositon(int position){

        ParkingSpot parkingSpot = getParkingspotByPosition(position);

        if (parkingSpot!=null){
            return parkingSpot.getUser();
        }else {
            return null;
        }


    }

    /***
     * Parkt einen User auf einem bestimmten parkplatz
     *
     *
     * @param parkingSpot
     * @param user
     * @return
     */
    public boolean parkUserOnParkingSpot(ParkingSpot parkingSpot, User user){

        if (parkingSpot.getUser() == null && user.getParkingSpot()== null){


            try{

            parkingSpot.setUser(user);
            user.setParkingSpot(parkingSpot);

            persistOrMerge(parkingSpot);
            userDAO.persistOrMerge(user);


            }catch (Exception exp){
                System.out.println("failed to park user "+exp);
                parkingSpot.setUser(null);
                user.setParkingSpot(null);
                return false;
            }

        }

        return false;
    }


    /***
     * Gibt einen Parkplatz anhand seiner Position zurück
     *
     *
     *
     * @param position
     * @return
     */
    public ParkingSpot getParkingspotByPosition(int position){

        ParkingSpot parkingSpot = null;
        try {

                if (super.em!= null) {


                    List<ParkingSpot> parkingSpots = null;

                    final String query = "from ParkingSpot e where e.position = "+position;
                    //WORKS ->//final String query = "from ParkingSpot e where e.parkingArea.id = "+parkingArea.getId();
                    System.out.println("_QUERY FOR PARKINGSPOTS: "+query);
                    parkingSpots = (List<ParkingSpot>) cacheable(em.createQuery(query, entityClass)).getResultList();

                    if (parkingSpots.size()>=1){

                        parkingSpot = parkingSpots.get(0);
                    }

                }else {
                    System.out.println("__X__X_X_XX EM NULL N Parking SPOT DAO");
                }



        }catch (Exception exp){

            System.out.println(" failed to get ParkingSpot by Position "+exp);
            return null;
        }

        return parkingSpot;
    }

    public ParkingSpot getParkingSpotByID(Long id){

        try{
            return findById(id);
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
        return null;
    }
}
