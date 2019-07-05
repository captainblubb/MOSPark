package org.dhbw.mosbach.ai.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * This class is used to tell to the server that there are REST services.
 * An alternative is to declare a servlet in the web.xml.
 *
 * @author marco
 */

@ApplicationPath("rest") // the 'rest' adress is mapped to the REST services
public class RestConfig extends Application{ // a javax.ws.rs.core.Application must be extended

}