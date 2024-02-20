package com.sample.v2.resources;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api")
public class BypassedResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/1")
    public String bypassed() {
        return "This should be excluded 1";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/2")
    public String bypassedThroughGenericFilter() {
        return "This should be excluded 2";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/3")
    public String bypassedThroughContainerRequestFilter() {
        return "This should be excluded 3";
    }
}
