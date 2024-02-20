package com.sample.configurations.filters;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Provider
@Priority(9999)
@Slf4j
public class BypassContainerRequestFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String path = containerRequestContext.getUriInfo().getAbsolutePath().toString();
        log.info("inside BypassContainerRequestFilter path {}", path);
        if(path.contains("api/3")) {
            containerRequestContext.abortWith(Response.fromResponse(callSvc()).build());
        }
    }

    private Response callSvc() {
        return Response.ok("This msg is from BypassContainerRequestFilter").build();
    }
}
