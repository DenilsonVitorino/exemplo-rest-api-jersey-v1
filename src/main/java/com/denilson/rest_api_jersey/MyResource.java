package com.denilson.rest_api_jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

// The Java class will be hosted at the URI path "/myresource"
@Path("/myresource")
public class MyResource {

    // TODO: update the class to suit your needs
    
    // The Java method will process HTTP GET requests
    @GET 
    // The Java method will produce content identified by the MIME Media
    // type "text/plain"
    @Produces("text/plain;charset=utf-8")
    public String getIt() {
        return "Ol? mundo!";
    }
}
