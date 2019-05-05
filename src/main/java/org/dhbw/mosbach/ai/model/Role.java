package org.dhbw.mosbach.ai.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlTransient
public class Role {


    private Long id;
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

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
