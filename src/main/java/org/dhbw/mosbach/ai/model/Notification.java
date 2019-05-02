package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Notification {

    private String notification;

    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public User UserFROM;

    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public User UserTO;

    @Column(nullable = false, length = 64, unique = true)
    @XmlAttribute(required = true)
    public String getNotification()
    {
        return notification;
    }



}
