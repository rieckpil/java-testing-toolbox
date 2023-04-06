package de.rieckpil.blog.microshedtesting;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;
import org.mockserver.client.MockServerClient;

import static de.rieckpil.blog.microshedtesting.SampleApplicationConfig.mockServer;
import static io.restassured.RestAssured.given;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.MediaType.JSON_UTF_8;

@MicroShedTest
@SharedContainerConfig(SampleApplicationConfig.class)
class SampleResourceIT {

  @Test
  void shouldReturnSampleMessage() {
    given()
      .get("/resources/sample/message")
      .then()
      .statusCode(200)
      .body(Matchers.is("Hello World from MicroShed Testing"));
  }

  @Test
  void shouldReturnQuoteOfTheDay() {

    JsonObject resultQuote = Json.createObjectBuilder()
      .add("contents",
        Json.createObjectBuilder().add("quotes",
          Json.createArrayBuilder().add(Json.createObjectBuilder()
            .add("quote", "Do not worry if you have built your castles in the air. " +
              "They are where they should be. Now put the foundations under them."))))
      .build();

    new MockServerClient(mockServer.getContainerIpAddress(), mockServer.getServerPort())
      .when(request("/qod"))
      .respond(response().withBody(resultQuote.toString(), JSON_UTF_8));

    String result = given()
      .get("/resources/sample/quotes")
      .then()
      .statusCode(200)
      .body(Matchers.containsString("They are where they should be"))
      .extract()
      .as(String.class);

    System.out.println("Quote of the day: " + result);
  }
}
