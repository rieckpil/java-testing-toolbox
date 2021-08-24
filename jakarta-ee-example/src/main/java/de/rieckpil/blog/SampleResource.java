package de.rieckpil.blog;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("sample")
public class SampleResource {

  @GET
  public Response message() {
    return Response.ok("Hello World!").build();
  }

}
