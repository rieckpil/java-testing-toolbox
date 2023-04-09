package de.rieckpil.blog;

import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://quotes.rest")
public interface QuoteRestClient {

  @GET
  @Path("/qod")
  @Consumes(MediaType.APPLICATION_JSON)
  JsonObject getQuoteOfTheDay();
}
