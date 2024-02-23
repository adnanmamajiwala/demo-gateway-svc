package com.sample.v2.resources;

import com.sample.AppConfig;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/sample")
public class SampleResource {

    private final AppConfig appConfig;

    @Inject
    public SampleResource(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/get")
    public String get() {
        return "Current value is " + appConfig.isBypassEnabled();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/flip")
    public String flip() {
        appConfig.flip();
        return "Successful flipped now value is - " + appConfig.isBypassEnabled();
    }

}
