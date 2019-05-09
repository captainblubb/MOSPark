package org.dhbw.mosbach.ai.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;


public final class Role implements Serializable {

    public static final String ADMIN = "admin";

    public static final String USER = "user";

    public static final String GUEST = "guest";

}
/*
@Entity
@XmlTransient
public class Role implements Serializable {


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
*/