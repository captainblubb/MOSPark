package org.dhbw.mosbach.ai.db.org.dhbw.mosbach.ai.Provider;

import org.dhbw.mosbach.ai.db.UserDAO;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class DataProvider {

    @Inject
    private UserDAO userDAO;

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

    }

    private void generateParkingAreas(){

    }

    private void generateParkingSpots(){

    }

    public void startUp(){
        System.out.println("__________STAAART DATA PROVIDER________________");
    }

}
