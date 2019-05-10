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

     /*
        Generates all Data for ParkingSpots and ParkingAreas
      */
    @PostConstruct
    public void init(){
        boolean userSucc = userDAO.createUser("Peter","SHA-FS-9","swag");
        System.out.println("_________USER CREATE: "+userSucc+" _____________");

        generateParkingAreas();
        generateParkingSpots();

    }

    /*
              Posi Beschreibung Parkplätze XXXX

              1. -> ParkingArea
              2. -> Vertikale Reihe
              3. -> Position in Column


              Area 1
              ____________________________________
              |   P1     .      .       .   P13   |
              |__________.  P7  . P10   ._________|         P1 -> 1000  P7 -> 1100  P10 -> 1200  P13 -> 1300
              |   P2     .      .       .   P14   |         P2 -> 1001  P8 -> 1101  P11 -> 1201  P14 -> 1301
              |__________. . .  . . . . ._________|         ...         ...         ...          ...
              |   P3     .      .       .   P15   |
              |__________.  P8  . P11   ._________|
              |   P4     .      .       .   P16   |
              |__________. . . . . . . .._________|
              |   P5     .      .       .   P17   |
              |__________.  P9  . P12   ._________|
              |   P6     .      .       .   P18   |
              |__________. . . . . . . .._________|

                      Area 2
              ____________________________________
              |   P1     .      .       .   P13   |
              |__________.  P7  . P10   ._________|         P1 -> 2000  P7 -> 2100  P10 -> 2200  P13 -> 2300
              |   P2     .      .       .   P14   |         P2 -> 2001  P8 -> 2101  P11 -> 2201  P14 -> 2301
              |__________. . .  . . . . ._________|         ...         ...         ...          ...
              |   P3     .      .       .   P15   |
              |__________.  P8  . P11   ._________|
              |   P4     .      .       .   P16   |
              |__________. . . . . . . .._________|
              |   P5     .      .       .   P17   |
              |__________.  P9  . P12   ._________|
              |   P6     .      .       .   P18   |
              |__________. . . . . . . .._________|

       */
    private void generateParkingAreas(){

        System.out.println("____CREATE PARKING AREA DUMMY ___");
        boolean b = parkingAreaDAO.createParkingArea("A-Gebäude",10);
        boolean c = parkingAreaDAO.createParkingArea("B-Gebäude",20);
        System.out.println(" CREATE PARKING AREA DUMMY RESULT : "+b);
    }

    private void generateParkingSpots(){


        System.out.println("____CREATE PARKING SPOTS DUMMY ___");
        List<ParkingArea> parkingAreas = parkingAreaDAO.getAll();

        for (ParkingArea parkingArea: parkingAreas) {

            int counter = 0;
            for (int i = 0; i <parkingArea.getTotalSpots();i++){
                boolean b = parkingSpotDAO.createParkingSpot(parkingArea,counter);

                System.out.println("____CREATE PARKING SPOTS DUMMY Result ___"+b);

                counter++;
            }


        }

    }

}
