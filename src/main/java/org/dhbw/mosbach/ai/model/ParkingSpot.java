package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@Entity
@XmlTransient
public class ParkingSpot implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private int position;

    @ManyToOne(optional = false)
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
