package org.dhbw.mosbach.ai.db;

import org.dhbw.mosbach.ai.db.base.BaseDao;
import org.dhbw.mosbach.ai.db.base.Hashing;
import org.dhbw.mosbach.ai.model.ParkingSpot;
import org.dhbw.mosbach.ai.model.Role;
import org.dhbw.mosbach.ai.model.User;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@Dependent
public class UserDAO extends BaseDao<User,Long> {

    public UserDAO(){
        super();
    }

    /***
     * Legt einen Benutzer an, mit Name Passwort und Numernschild
     * Passwort wird gehashed gespeichert.
     *
     * @param name
     * @param licensePlate
     * @param password
     *
     * @return True= funktioniert /False = Fehler
     */
    public boolean createUser(String name, String licensePlate, String password){

        try {

        if (findByUnique("name",name) == null) {
            User user = new User();
            user.setRole(Role.USER);
            byte[] salt = generateSalt();
            user.setSalt(salt);
            user.setHash(hashPassword(password, salt));
            user.setLicenseplate(licensePlate);
            user.setName(name);
            user.setRole(Role.USER);
            super.persist(user);
            return true;
        }else {
            return false;
        }

        }catch (Exception exp) {
            System.out.println(" failed create User " + exp);
        }

        return false;

    }

    /***
     * Authentifizieren eines Users
     *
     * @param name
     * @param password
     * @return True = authentifiziert / false = authentifizierung nicht möglich
     */
    public boolean authentificateUser(String name, String password){

        try {


            User user = getUserByUsername(name);

            System.out.println("______________AUTH USER FIND BY UNIQUE NAME "+ user.getId());

            if (user != null) {

                byte[] userHash = user.getHash();
                byte[] verifyHash = hashPassword(password, user.getSalt());

                boolean auth = true;

                for(int i = 0; i < userHash.length; i++){

                    if (userHash[i] != verifyHash[i]){
                        auth = false;
                        break;
                    }
                }
                return auth;
            }else{

                System.out.println("____-____Auth user failed didnt find user");
            }

        }catch (Exception exp) {
            System.out.println("failed authentifcate user" + exp);
        }
        return false;
    }



    /***
     * Get User By ID
     *
     * @param id
     * @return User
     */
    public User getUserById(Long id){

        try{
            return findById(id);
        }catch (Exception exp){
            System.out.println(" failed finding user by id "+ exp);
        }
        return null;
    }

    /***
     *
     *
     * @param user
     * @param newLicensePlate
     * @return
     */
    public boolean changeLicensePlate(User user,String newLicensePlate){

        try {

            if (user != null) {
                user.setLicenseplate(newLicensePlate);
                merge(user);
                return true;
            } else {
                return false;
            }

        }catch (Exception exp){
        System.out.println("failed change License plate user"+exp);
        return false;
    }
    }


    /***
     * Generates Salt to hash the User
     * @return
     */
    private byte[] generateSalt(){
        return Hashing.getNextSalt();
    }


    /***
     * Hashes a password, including also a salt
     *
     * @param password
     * @param salt
     * @return
     */
    private byte[] hashPassword(String password, byte[] salt){
        return Hashing.hash(password.toCharArray(),salt);
    }


    public User getUserByUsername(String username){
        try{
            return findByUnique("name", username);
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
        return null;
    }


}
