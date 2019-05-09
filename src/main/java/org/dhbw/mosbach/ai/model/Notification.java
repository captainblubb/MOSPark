package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
public class Notification {


    @Id
    @GeneratedValue
    private Long id;

    private String notification;

    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User UserFROM;

    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User UserTO;

    @Column(nullable = false, length = 64, unique = true)
    @XmlAttribute(required = true)
    public String getNotification()
    {
        return notification;
    }


    @Column
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUserFROM() {
        return UserFROM;
    }

    public void setUserFROM(User userFROM) {
        UserFROM = userFROM;
    }

    public User getUserTO() {
        return UserTO;
    }

    public void setUserTO(User userTO) {
        UserTO = userTO;
    }
}