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

        int totalSpotsOfA=0;
        for (int i = 0; i <ParkingAreaDefinition.parkingAreaA.length;i++){
            totalSpotsOfA+=ParkingAreaDefinition.parkingAreaA[i];
        }
        boolean a = parkingAreaDAO.createParkingArea(ParkingAreaDefinition.parkingAreaAName,totalSpotsOfA);

        int totalSpotsOfB=0;
        for (int i = 0; i <ParkingAreaDefinition.parkingAreaB.length;i++){
            totalSpotsOfB+=ParkingAreaDefinition.parkingAreaB[i];
        }
        boolean b = parkingAreaDAO.createParkingArea(ParkingAreaDefinition.parkingAreaBName,totalSpotsOfB);

        int totalSpotsOfC=0;
        for (int i = 0; i <ParkingAreaDefinition.parkingAreaC.length;i++){
            totalSpotsOfC+=ParkingAreaDefinition.parkingAreaC[i];
        }
        boolean c = parkingAreaDAO.createParkingArea(ParkingAreaDefinition.parkingAreaCName,totalSpotsOfC);

        System.out.println(" CREATE PARKING AREA DUMMY RESULT 1 : "+a);
        System.out.println(" CREATE PARKING AREA DUMMY RESULT 2 : "+b);
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

                int[] corpus = ParkingAreaDefinition.getCorpusOfParkingArea(parkingArea.getName());

                //Create Parking
                for (int column = 0; column<corpus.length;column++) {

                    for (int row = 0; row<corpus[column];row++){

                        boolean b = parkingSpotDAO.createParkingSpot(parkingArea,column,row);
                        System.out.println("Create Parking Spot on "+parkingArea.getName()+" in column "+column+" in row "+row+" result: "+b);

                    }

                }

            }

        }catch (Exception exp){
            System.out.println("Exception when loading parkingAreas "+exp);
        }

    }

}
