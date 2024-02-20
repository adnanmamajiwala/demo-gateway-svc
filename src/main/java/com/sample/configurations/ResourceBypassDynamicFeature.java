package com.sample.configurations;

import com.sample.v1.servlets.BypassV2Servlet;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.container.DynamicFeature;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.FeatureContext;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ResourceBypassDynamicFeature implements DynamicFeature {

    private final ServletContextHandler context;
    private final Set<Class<?>> servletApplied = new HashSet<>();

    public ResourceBypassDynamicFeature(ServletContextHandler context) {
        this.context = context;
    }

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {

        Class<?> resourceClass = resourceInfo.getResourceClass();
        Path annotation = resourceClass.getAnnotation(Path.class);

        if(annotation != null
                && !servletApplied.contains(resourceClass)
                && annotation.value().contains("api")) {

            String path = "/v2/api/1";
            log.info("Path to be bypassed - {}, Method name {}", path, resourceInfo.getResourceMethod().getName());

            context.addServlet(new ServletHolder(BypassV2Servlet.class), "/v2/api/1");
            servletApplied.add(resourceClass);
        }
    }

}
