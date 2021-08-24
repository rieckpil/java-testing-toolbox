package de.rieckpil.blog;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("sample")
public class SampleResource {

  @GET
  public Response message() {
    return Response.ok("Hello World!").build();
  }

}
