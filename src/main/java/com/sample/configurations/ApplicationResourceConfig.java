package com.sample.configurations;

import jakarta.ws.rs.ApplicationPath;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.sample.AppConfig;
import com.sample.configurations.filters.BypassGenericFilter;
import com.sample.configurations.filters.ServletAddRemoveFilter;

@ApplicationPath("/")
public class ApplicationResourceConfig extends ResourceConfig {

    public ApplicationResourceConfig(ServletContextHandler context) {
        AppConfig config = new AppConfig();
        register(getBinder(config, context));
        register(ServletAddRemoveFilter.class);
        packages("com.sample");
    }

    private static AbstractBinder getBinder(AppConfig config, ServletContextHandler context) {
        return new AbstractBinder() {
            @Override
            protected void configure() {
                bind(config);
                bind(context);
            }
        };
    }

}
