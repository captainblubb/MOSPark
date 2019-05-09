package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ParkingSpot implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;


    @OneToOne
    private ParkingArea parkingArea;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingArea getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(ParkingArea parkingArea) {
        this.parkingArea = parkingArea;
    }
}
