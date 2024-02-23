package com.sample;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import com.sample.configurations.ApplicationResourceConfig;
import com.sample.configurations.filters.BypassGenericFilter;
import com.sample.configurations.filters.ServletAddRemoveFilter;
import com.sample.v1.servlets.ExistingV1Servlet;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = createServer();
        server.start();
        server.join();
    }

    private static Server createServer() throws Exception {
        Server server = new Server(8090);

        ServletContextHandler context = new ServletContextHandler(server, "/");
        ServletHolder servletHolder = new ServletHolder(new ServletContainer(new ApplicationResourceConfig(context)));
        servletHolder.setInitOrder(0);

        context.addServlet(servletHolder, "/v2/*");
        context.addServlet(ExistingV1Servlet.class, "/v1/*");
        context.addFilter(new FilterHolder(new BypassGenericFilter()), "/*", null);
        return server;
    }

}