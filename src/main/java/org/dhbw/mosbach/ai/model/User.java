package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@Entity
@XmlRootElement
public class User implements Serializable,Cloneable {


    private long id;
    private String name;
    private String licenseplate;

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



    @Column(nullable = false, length = 64, unique = true)
    @XmlAttribute(required = true)
    public String getLicenseplate()
    {
        return licenseplate;
    }



    @OneToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public ParkingSpot parkingSpot;



}
