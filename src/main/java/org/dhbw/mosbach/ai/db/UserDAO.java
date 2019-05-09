package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.db.base.Hashing;
import org.dhbw.mosbach.ai.model.Role;
import org.dhbw.mosbach.ai.model.User;

import javax.enterprise.context.Dependent;
import javax.inject.Named;


@Named
@Dependent
public class UserDAO extends BaseDao<User,Long> {

    public UserDAO(){
        super();
    }

    /*
    Creates User
     */
    public boolean createUser(String name, String licensePlate, String password){


        if (findByUnique("name",name) == null) {
            User user = new User();
            user.setRole(Role.USER);
            // user.setRole(role);
            byte[] salt = generateSalt();
            user.setSalt(salt);
            user.setHash(hashPassword(password,salt));
            user.setLicenseplate(licensePlate);
            user.setName(name);
            super.persist(user);
            return true;
        }else {
            return false;
        }

    }

    /*
        authentifcate User
     */
    public boolean authentificateUser(String name, String password){

        User user = findByUnique("name",name);

        if (user != null) {

            if (user.getHash().equals(hashPassword(password,user.getSalt()))){

                return true;
            }
        }
        return false;
    }

    public User getUserById(Long id){
        return getUserById(id);
    }

    /*
    change
     */
    public boolean changeLicensePlate(User user,String newLicensePlate){

        if (user!=null) {
            user.setLicenseplate(newLicensePlate);
            merge(user);
            return true;
        }else {
            return false;
        }
    }

    private byte[] generateSalt(){
        return Hashing.getNextSalt();
    }

    private byte[] hashPassword(String password, byte[] salt){
        return Hashing.hash(password.toCharArray(),salt);
    }



}
