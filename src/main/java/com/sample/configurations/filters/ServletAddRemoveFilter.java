package com.sample.configurations.filters;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Priority;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;

import com.sample.AppConfig;
import com.sample.v1.servlets.BypassV2Servlet;


import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import lombok.extern.slf4j.Slf4j;


@Priority(1)
@Slf4j
public class ServletAddRemoveFilter implements ContainerRequestFilter {
    private final ServletContextHandler context;
    private final AppConfig config;
    private boolean isServletAdded = false;

    @Inject
    public ServletAddRemoveFilter(ServletContextHandler context, AppConfig config) {
        this.context = context;
        this.config = config;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
//        String path = containerRequestContext.getUriInfo().getAbsolutePath().toString();

        if (isNull(context)) {
            log.info("Inside ServletAddRemoveFilter context or path were null returning back- Noop");
            return;
        }

        log.info("Inside ServletAddRemoveFilter - isBypassEnabled: {} - isServletAdded: {}", config.isBypassEnabled(), isServletAdded);

        ServletHolder servlet = new ServletHolder(BypassV2Servlet.class);
        if (config.isBypassEnabled()) {
            if(!isServletAdded) {
                context.addServlet(servlet, "/v2/api/1");
                isServletAdded = true;
            }
        } else {
            if(isServletAdded) {
                removeServlets(BypassV2Servlet.class);
                isServletAdded = false;
            }
        }

    }

    public void removeServlets(Class<?> servlet) {
        try {
            ServletHandler handler = context.getServletHandler();
            List<ServletHolder> servlets = new ArrayList<>();
            Set<String> names = new HashSet<>();

            for (ServletHolder holder : handler.getServlets()) {
                if (servlet.isInstance(holder.getServlet())) {
                    names.add(holder.getName());
                } else {
                    servlets.add(holder);
                }
            }

            List<ServletMapping> mappings = new ArrayList<>();

            for (ServletMapping mapping : handler.getServletMappings()) {
                if (!names.contains(mapping.getServletName())) {
                    mappings.add(mapping);
                }
            }

            handler.setServletMappings(mappings.toArray(new ServletMapping[0]));
            handler.setServlets(servlets.toArray(new ServletHolder[0]));
        } catch (Exception e) {
            log.error("Error while removing servlet - ", e);
        }
    }

}
