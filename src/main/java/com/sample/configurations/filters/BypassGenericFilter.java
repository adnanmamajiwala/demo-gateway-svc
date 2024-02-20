package com.sample.configurations.filters;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Request;

import java.io.IOException;

@Slf4j
public class BypassGenericFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String pathInfo = getPathInfo(servletRequest);
        log.info("BypassGenericFilter is being invoked");
        if(pathInfo.contains("api/2")) {
            log.info("api/2 path request received {}", pathInfo);
            servletResponse.getWriter().println("Returning back from MyGenericFilter");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private static String getPathInfo(ServletRequest servletRequest) {
        return ((Request) servletRequest).getRequestURI();
    }

}
