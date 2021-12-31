package de.rieckpil.blog;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://quotes.rest")
public interface QuoteRestClient {

  @GET
  @Path("/qod")
  @Consumes(MediaType.APPLICATION_JSON)
  JsonObject getQuoteOfTheDay();
}
