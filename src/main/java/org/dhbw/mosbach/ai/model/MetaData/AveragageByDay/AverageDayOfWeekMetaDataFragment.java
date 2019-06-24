package org.dhbw.mosbach.ai.model.MetaData.AveragageByDay;

import org.dhbw.mosbach.ai.model.ParkingArea;

import javax.persistence.*;
import java.util.Calendar;


/***
 * Stellt die freien Parkplätze zu einem bestimmten Datum und Uhrzeit dar.
 *
 */
@Entity
public class AverageDayOfWeekMetaDataFragment {

    private Long id;
    private int freeSpots;
    private Calendar timestamp;
    private AverageDayOfWeekMetaData averageDayOfWeekMetaData;


    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public AverageDayOfWeekMetaData getAverageDayOfWeekMetaData() {
        return averageDayOfWeekMetaData;
    }

    public void setAverageDayOfWeekMetaData(AverageDayOfWeekMetaData averageDayOfWeekMetaData) {
        this.averageDayOfWeekMetaData = averageDayOfWeekMetaData;
    }
}
