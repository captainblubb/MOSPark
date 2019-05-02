package org.dhbw.mosbach.ai.beans;

import org.dhbw.mosbach.ai.model.ParkingSpot;
import org.dhbw.mosbach.ai.model.User;

import javax.inject.Inject;

public class OccupyParkingSpotBean extends BaseBean {
    @Inject
    private UserDao userDao;

    @Inject
    private ParkingSpotDao parkingSpotDao;

    public void execute(String userId, String parkingSpotId) {
        User user = userDao.getUserById(userId);
        ParkingSpot parkingSpot = parkingSpotDao.getParkingSpotById(parkingSpotId);

        parkingSpotDao.updateOccupyingUser(parkingSpot, user);
    }
}
