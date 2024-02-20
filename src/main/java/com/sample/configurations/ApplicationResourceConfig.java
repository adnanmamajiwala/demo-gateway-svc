package com.sample.configurations;

import jakarta.ws.rs.ApplicationPath;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class ApplicationResourceConfig extends ResourceConfig {

    public ApplicationResourceConfig(ServletContextHandler context) {
        packages("com.sample");
        register(new ResourceBypassDynamicFeature(context));
    }


}
