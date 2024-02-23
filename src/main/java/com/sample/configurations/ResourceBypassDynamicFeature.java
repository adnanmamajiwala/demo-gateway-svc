package com.sample.configurations;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sample.AppConfig;
import com.sample.v1.servlets.BypassV2Servlet;


import jakarta.ws.rs.Path;
import jakarta.ws.rs.container.DynamicFeature;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.FeatureContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceBypassDynamicFeature implements DynamicFeature {

    private final ServletContextHandler context;
    private final AppConfig config;
    private Set<Class<?>> resourceClassSet = new HashSet<>();

    public ResourceBypassDynamicFeature(ServletContextHandler context, AppConfig config) {
        this.context = context;
        this.config = config;
    }

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {

        Class<?> resourceClass = resourceInfo.getResourceClass();
        Path annotation = resourceClass.getAnnotation(Path.class);
        if (annotation != null
                && !resourceClassSet.contains(resourceClass)
                && annotation.value().contains("api")) {
            String path = "/v2/api/1";
            log.info("Path to be bypassed - {}, Method name {}", path, resourceInfo.getResourceMethod().getName());

            context.addServlet(new ServletHolder(BypassV2Servlet.class), "/v2/api/1");
            resourceClassSet.add(resourceClass);
        }
    }

}
