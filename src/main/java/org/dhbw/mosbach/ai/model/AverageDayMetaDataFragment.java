package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;


/***
 * Stellt
 *
 */
@Entity
public class AverageDayMetaDataFragment {

    private Long id;
    private int freeSpots;
    private ParkingArea parkingArea;
    private Calendar timestamp;
    private AverageDayMetaData averageDayMetaData;


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
    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    @Column(nullable = false)
    public int getFreeSpots() {
        return freeSpots;
    }

    public void setFreeSpots(int freeSpots) {
        this.freeSpots = freeSpots;
    }


    @ManyToOne
    public AverageDayMetaData getAverageDayMetaData() {
        return averageDayMetaData;
    }

    public void setAverageDayMetaData(AverageDayMetaData averageDayMetaData) {
        this.averageDayMetaData = averageDayMetaData;
    }
}
