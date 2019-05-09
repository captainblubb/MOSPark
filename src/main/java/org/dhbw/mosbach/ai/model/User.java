package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

@Entity
@XmlRootElement
public class User implements Serializable,Cloneable {


    private long id;
    private String name;
    private String licenseplate;
    private String hashedPassword;
    private Role role;

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


    @Column(nullable= false)
    public Role getRole() {
        return role;
    }

    @OneToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public ParkingSpot parkingSpot;


    //TODO: Check Params
    @OneToMany
    public List<Notification> notificationList;

    @Column
    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setRole(Role role) {
        this.role = role;
    }
}
