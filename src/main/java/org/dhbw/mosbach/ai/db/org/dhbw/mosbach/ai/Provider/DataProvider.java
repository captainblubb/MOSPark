package org.dhbw.mosbach.ai.db.org.dhbw.mosbach.ai.Provider;

import org.dhbw.mosbach.ai.db.ParkingAreaDAO;
import org.dhbw.mosbach.ai.db.ParkingSpotDAO;
import org.dhbw.mosbach.ai.db.UserDAO;

import javax.annotation.PostConstruct;
import javax.annotation.security.RunAs;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.dhbw.mosbach.ai.model.ParkingArea;
import org.dhbw.mosbach.ai.model.ParkingSpot;
import org.dhbw.mosbach.ai.model.Role;

import java.util.List;

@Startup
@Singleton
@RunAs(value = Role.ADMIN)
public class DataProvider {

    @Inject
    private UserDAO userDAO;

    @Inject
    private ParkingAreaDAO parkingAreaDAO;

    @Inject
    private ParkingSpotDAO parkingSpotDAO;


    public DataProvider(){
        //Gets called on Startup
        System.out.println("______________INITIALZATION DATAPROVICER STARTUP_________");
     }

    /***
     * Generiert alle Parking Areas inkl. Parking Spots
     */
    @PostConstruct
    public void init(){
        boolean userSucc = userDAO.createUser("Peter","SHA-FS-9","swag");
        System.out.println("_________USER CREATE: "+userSucc+" _____________");

        generateParkingAreas();
        generateParkingSpots();

    }

    /*
        Position wird in ->Parking Area, Column und Row gespeichert.

       */

    /***
     * Erstellt die Parking Areas
     *
     *
     */
    private void generateParkingAreas(){

        System.out.println("____CREATE PARKING AREA DUMMY ___");
        boolean b = parkingAreaDAO.createParkingArea("A-Gebäude",10);
        boolean c = parkingAreaDAO.createParkingArea("B-Gebäude",20);
        System.out.println(" CREATE PARKING AREA DUMMY RESULT 1 : "+b);
        System.out.println(" CREATE PARKING AREA DUMMY RESULT 2 : "+c);
    }

    /***
     * generiert die Parkplätze zu den Parking Areas
     *
     *
     */
    private void generateParkingSpots(){

        System.out.println("____CREATE PARKING SPOTS DUMMY ___");
        try {
            List<ParkingArea> parkingAreas = parkingAreaDAO.getAll();

            for (ParkingArea parkingArea : parkingAreas) {

                int counter = 0;
                for (int i = 0; i < parkingArea.getTotalSpots(); i++) {
                    boolean b = parkingSpotDAO.createParkingSpot(parkingArea, counter % 10, counter);
                    System.out.println("____CREATE PARKING SPOTS DUMMY Result ___" + b);

                    counter++;
                }
            }

        }catch (Exception exp){
            System.out.println("Exception when loading parkingAreas "+exp);
        }

    }

}
