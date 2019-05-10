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