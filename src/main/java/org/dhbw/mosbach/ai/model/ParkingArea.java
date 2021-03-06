package org.dhbw.mosbach.ai.model;

import javax.inject.Named;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
public class ParkingArea implements Serializable {

    public long id;
    private String name;
    private int totalSpots;

    @Id
    @GeneratedValue
    public long getId()
    {
        return id;
    }


    @Column(nullable = false, length = 64, unique = false)
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

    @Column(nullable = false)
    public int getTotalSpots() {
        return totalSpots;
    }

    public void setTotalSpots(int totalSpots) {
        this.totalSpots = totalSpots;
    }
}
