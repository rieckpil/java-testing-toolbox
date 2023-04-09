package de.rieckpil.blog;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("sample")
@Produces(MediaType.TEXT_PLAIN)
public class SampleResource {

  @Inject
  @ConfigProperty(name = "message")
  private String message;

  @Inject @RestClient private QuoteRestClient quoteRestClient;

  @GET
  @Path("/message")
  public String getMessage() {
    return message;
  }

  @GET
  @Path("/quotes")
  public String getQuotes() {
    var quoteOfTheDayPointer = Json.createPointer("/contents/quotes/0/quote");
    return quoteOfTheDayPointer.getValue(quoteRestClient.getQuoteOfTheDay()).toString();
  }
}
