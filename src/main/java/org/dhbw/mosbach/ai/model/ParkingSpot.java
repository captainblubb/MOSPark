package org.dhbw.mosbach.ai.model;

import javax.persistence.*;

@Entity
public class ParkingSpot {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
