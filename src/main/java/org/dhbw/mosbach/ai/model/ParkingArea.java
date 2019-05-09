package org.dhbw.mosbach.ai.model;

import javax.inject.Named;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "parkingArea")
@XmlTransient
public class ParkingArea implements Serializable {

    private long id;
    private String name;

    public void setParkingSpots(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    private List<ParkingSpot> parkingSpots;

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


    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setParkingSpots(ArrayList<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }
}
