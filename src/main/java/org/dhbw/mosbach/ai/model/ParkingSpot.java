package org.dhbw.mosbach.ai.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ParkingSpot implements Serializable {

    public Long id;

    private User user;

    private int pAreaColumn;

    private int pAreaRow;

    private boolean occupied;

    private ParkingArea parkingArea;


    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column()
    public int getPAreaColumn() {
        return pAreaColumn;
    }

    public void setPAreaColumn(int row) {
        this.pAreaColumn = row;
    }

    @Column()
    public int getPAreaRow() {
        return pAreaRow;
    }

    public void setPAreaRow(int column) {
        this.pAreaRow = column;
    }


    @OneToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user!=null) {
            occupied = true;
        }else {
            occupied =false;
        }
    }


    @ManyToOne
    public ParkingArea getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(ParkingArea parkingArea) {
        this.parkingArea = parkingArea;
    }

    @Column(nullable = false)
    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}