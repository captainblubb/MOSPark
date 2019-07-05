package org.dhbw.mosbach.ai.services.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ParkingSpotObject {
    @XmlElement
    public Long id;

    @XmlElement
    public Long userId;

    @XmlElement
    public Long areaId;

    @XmlElement
    public int column;

    @XmlElement
    public int row;
}