package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;


@Entity
@XmlTransient
public class ParkingArea {

    public long id;

    @Id
    @GeneratedValue
    @XmlTransient
    public long getId()
    {
        return id;
    }



    @Column
    private List<ParkingSpot> parkingSpots;
}
