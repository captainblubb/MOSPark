package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@Entity
@XmlRootElement
public class User implements Serializable,Cloneable {


    private long id;

    @Id
    @GeneratedValue
    @XmlTransient
    public long getId()
    {
        return id;
    }

    @OneToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ParkingSpot parkingSpot;


}
