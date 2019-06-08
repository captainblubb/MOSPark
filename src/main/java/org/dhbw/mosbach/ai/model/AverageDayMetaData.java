package org.dhbw.mosbach.ai.model;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

/***
 * Durchschnittliche Belegung der Parkplätze der letzen 5 Wochentage
 *
 */
@Entity
public class AverageDayMetaData {

    private Long id;
    private ParkingArea parkingArea;
    private Calendar timestamp;

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



}
