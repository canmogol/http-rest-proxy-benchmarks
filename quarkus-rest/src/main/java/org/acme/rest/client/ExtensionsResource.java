package org.acme.rest.client;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class ExtensionsResource {

    @Inject
    @RestClient
    ExtensionsService extensionsService;

    @GET
    public String get() {
        return extensionsService.get();
    }
}
