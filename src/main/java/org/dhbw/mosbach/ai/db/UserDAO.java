package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.model.User;

import javax.inject.Named;
import javax.naming.Name;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named("userDAO")
public class UserDAO extends BaseDao<User,Long> {

    public boolean createUser(String name, String licensePlate, String Password){

        User user = new User();
        user.setHashedPassword(hashPassword(Password));
        user.setLicenseplate(licensePlate);
        user.setName(name);
        super.persist(user);
        return true;
    }

    public boolean authentificateUser(String name, String password){

        User user = findByUnique("Name",name);
        if (user != null) {

            if (user.getHashedPassword().equals(hashPassword(password))){

                return true;
            }
        }

            return false;

    }

    public boolean changeLicensePlate(String newLicensePlate){


        return true;
    }


    private String hashPassword(String password){

        String salt = "";
        return password;
    }
}
