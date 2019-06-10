package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

@Entity
public class Notification {


    @Id
    @GeneratedValue
    private Long id;

    private String notification;

    private boolean dissmissed=false;

    private Timestamp dissmissedDate;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User UserFROM;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User UserTO;

    @Column(nullable = false, length = 64, unique = true)
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

    public void setNotification(String notification) {
        this.notification = notification;
    }

    @Column(nullable = true)
    public boolean isDissmissed() {
        return dissmissed;
    }

    public void setDissmissed(boolean dissmissed) {
        this.dissmissed = dissmissed;
    }

    @Column
    public Timestamp getDissmissedDate() {
        return dissmissedDate;
    }

    public void setDissmissedDate(Timestamp dissmissedDate) {
        this.dissmissedDate = dissmissedDate;
    }
}
