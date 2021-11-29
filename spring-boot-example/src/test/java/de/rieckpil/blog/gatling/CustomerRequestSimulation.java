package de.rieckpil.blog.gatling;

import java.time.Duration;
import java.util.UUID;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

public class CustomerRequestSimulation extends Simulation {

  HttpProtocolBuilder httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
    .userAgentHeader("Gatling/Performance Test");

  ScenarioBuilder scn = scenario("Load Test HTTP POST API")
    .exec(http("spring-boot-backend-request")
      .post("/api/customers")
      .body(StringBody(session -> "{ \"username\": \"" + randomUsername() + "\" }"))
    );

  {
    setUp(scn.injectOpen(constantUsersPerSec(50).during(Duration.ofSeconds(15)).randomized()))
      .protocols(httpProtocol);
  }

  public String randomUsername() {
    return UUID.randomUUID().toString();
  }
}
