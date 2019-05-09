package org.dhbw.mosbach.ai.db.org.dhbw.mosbach.ai.Daemon;

import org.dhbw.mosbach.ai.db.UserDAO;

import javax.annotation.PostConstruct;
import javax.annotation.security.RunAs;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Named;

@Startup
@Singleton
public class DataProvider {

    @Inject
    private UserDAO userDAO;

    public DataProvider(){

        //Gets called on Startup
        System.out.println("______________INITIALZATION DATAPROVICER STARTUP_________");
     }

    @PostConstruct
    public void init(){
        boolean userSucc = userDAO.createUser("Peter","SHA-FS-9","swag");
        System.out.println("_________USER CREATE: "+userSucc+" _____________");

    }

    public void startUp(){
        System.out.println("__________STAAART DATA PROVIDER________________");
    }

}
