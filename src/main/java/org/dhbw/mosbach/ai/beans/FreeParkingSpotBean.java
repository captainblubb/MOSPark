package org.dhbw.mosbach.ai.beans;

import org.dhbw.mosbach.ai.model.ParkingSpot;
import org.dhbw.mosbach.ai.model.User;

import javax.inject.Inject;

public class FreeParkingSpotBean extends BaseBean {

    @Inject
    private UserDao userDao;

    @Inject
    private ParkingSpotDao parkingSpotDao;

    public String execute(String userId, String parkingSpotId)
    {
        User user = userDao.getUserById(userId);
        ParkingSpot parkingSpot = parkingSpotDao.getParkingSpotById(parkingSpotId);

        if (user.getId() == parkingSpot.getUser().getId())
        {
            parkingSpot.free();
            return "success";
        } else {
            return "fail";
        }
    }
}
