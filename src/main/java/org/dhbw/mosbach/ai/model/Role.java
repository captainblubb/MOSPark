package org.dhbw.mosbach.ai.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlTransient
public class Role {


    private String role;
    private int permissions;


    @GeneratedValue
    @XmlTransient
    @Column(nullable = false, length = 64, unique = true)
    public String getRole() {
        return role;
    }

    @Column(nullable = false, length = 64, unique = true)
    @GeneratedValue
    @XmlTransient
    public int getPermissions() {
        return permissions;
    }

}
