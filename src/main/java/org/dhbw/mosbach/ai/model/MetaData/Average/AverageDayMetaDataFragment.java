package org.dhbw.mosbach.ai.model.MetaData.Average;


import org.dhbw.mosbach.ai.model.MetaData.AveragageByDay.AverageDayOfWeekMetaData;
import org.dhbw.mosbach.ai.model.ParkingArea;

        import javax.persistence.*;
        import java.util.Calendar;

/***
 * Stellt die freien Parkplätze zu einem bestimmten Datum und Uhrzeit dar.
 *
 */
@Entity
public class AverageDayMetaDataFragment {

    private Long id;
    private int freeSpots;
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
