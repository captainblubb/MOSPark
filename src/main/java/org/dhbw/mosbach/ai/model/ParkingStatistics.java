package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.sql.Timestamp;

@Entity
@XmlTransient
public class ParkingStatistics {

    private Timestamp timestamp;
    private ParkingArea parkingArea;
    private int freeParkingSpots;
    private int maximumParkingSpots;
    private Long id;


    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @ManyToOne(optional = false)
    public ParkingArea getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(ParkingArea parkingArea) {
        this.parkingArea = parkingArea;
    }

    @Column(nullable = false)
    public int getFreeParkingSpots() {
        return freeParkingSpots;
    }

    public void setFreeParkingSpots(int freeParkingSpots) {
        this.freeParkingSpots = freeParkingSpots;
    }

    @Column(nullable = false)
    public int getMaximumParkingSpots() {
        return maximumParkingSpots;
    }

    @Column(nullable = false)
    public void setMaximumParkingSpots(int maximumParkingSpots) {
        this.maximumParkingSpots = maximumParkingSpots;
    }


    public void setId(Long id) {
        this.id = id;
    }
}
