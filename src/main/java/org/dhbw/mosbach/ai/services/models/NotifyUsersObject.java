package org.dhbw.mosbach.ai.services.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class NotifyUsersObject {

    @XmlElement
    public Long userID;

    @XmlElement
    public List<Long> ids;
}