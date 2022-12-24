package org.acme.rest.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test.txt")
@RegisterRestClient
public interface ExtensionsService {

    @GET
    String get();

}
