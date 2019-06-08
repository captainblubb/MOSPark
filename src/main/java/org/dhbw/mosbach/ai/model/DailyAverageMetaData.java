package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import java.sql.Timestamp;


/***
 * Stellt
 *
 */
@Entity
public class DailyAverageMetaData {

    private Long id;
    private int freeSpots;
    private ParkingArea parkingArea;
    private Timestamp timestamp;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    public ParkingArea getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(ParkingArea parkingArea) {
        this.parkingArea = parkingArea;
    }

    @Column(nullable = false)
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Column(nullable = false)
    public int getFreeSpots() {
        return freeSpots;
    }

    public void setFreeSpots(int freeSpots) {
        this.freeSpots = freeSpots;
    }
}
