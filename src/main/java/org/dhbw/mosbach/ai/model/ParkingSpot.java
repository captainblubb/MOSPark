package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@Entity
public class ParkingSpot implements Serializable {

    public Long id;

    @OneToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    private int horizontal;

    private int vertical;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ParkingArea", referencedColumnName = "id")
    private ParkingArea parkingArea;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Id
    @GeneratedValue
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


    @Column(nullable = false)
    public int getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(int row) {
        this.horizontal = row;
    }

    @Column(nullable = false)
    public int getVertical() {
        return vertical;
    }

    public void setVertical(int column) {
        this.vertical = column;
    }
}