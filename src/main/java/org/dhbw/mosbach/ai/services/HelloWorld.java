package org.dhbw.mosbach.ai.services;


import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;


@ApplicationScoped
@Path("helloworld")
@Provider()
public class HelloWorld {

    @Context
    private UriInfo context;


    public HelloWorld() {
    }

    /**
     * Retrieves representation of an instance of helloWorld.HelloWorld
     * @return an insátance of java.lang.String
     */
    @Path("helloWorld")
    @GET
    @Produces("text/html")
    public String getHtml() {
        return "<html><body><h1>Hello, World!!</h1></body></html>";
    }
}