package com.sample.v2.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/sample")
public class SampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/test")
    public String get() {
        return "Test successful";
    }

}
