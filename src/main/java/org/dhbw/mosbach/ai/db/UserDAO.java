package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.model.Role;
import org.dhbw.mosbach.ai.model.User;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Name;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Named
@Dependent
public class UserDAO extends BaseDao<User,Long> {

    public UserDAO(){
        super();
        System.out.println("____XXXX____X_X_X_X__X_UserDaoConstruktor__XX_X__X_X__X_X_X");
    }

    public boolean createUser(String name, String licensePlate, String Password){

        Role role = new Role();
        role.setPermissions(1);
        role.setRole("Admin");
        User user = new User();
        // user.setRole(role);
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
