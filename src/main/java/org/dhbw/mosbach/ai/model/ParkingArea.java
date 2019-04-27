package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;


@Entity
@XmlTransient
public class ParkingArea {

    public long id;
    public String name;

    @Id
    @GeneratedValue
    @XmlTransient
    public long getId()
    {
        return id;
    }


    @Column(nullable = false, length = 64, unique = true)
    @XmlAttribute(required = true)
    public String getName()
    {
        return name;
    }


    @Column
    private List<ParkingSpot> parkingSpots;
}
