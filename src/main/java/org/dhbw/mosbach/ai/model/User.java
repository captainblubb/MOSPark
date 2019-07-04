package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

@Entity(name = "User")
public class User implements Serializable,Cloneable {


    private long id;
    private String name;
    private String licenseplate;
    private String role;
    private byte[] hash;
    private byte[] salt;
    private String jsonToken;

    @Id
    @GeneratedValue
    public long getId()
    {
        return id;
    }

    @Column(nullable = false, length = 64, unique = true)
    public String getName()
    {
        return name;
    }


    @Column(nullable = false)
    public byte[] getHash() {
        return hash;
    }


    @Column(nullable = false, length = 64, unique = true)
    public String getLicenseplate()
    {
        return licenseplate;
    }


    @Column(nullable = false, length = 64, unique = false)
    public String getRole() {
        return role;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    @OneToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ParkingSpot parkingSpot;


    @Column
    public byte[] getSalt() {
        return salt;
    }

    @Column()
    public String getJsonToken() { return jsonToken; }

    public void setJsonToken(String jsonToken) { this.jsonToken = jsonToken; }

    public void setName(String name) {
        this.name = name;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setRole(String role) {
        this.role = role;
    }


    public void setHash(byte[] hash) {
        this.hash = hash;
    }


    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
