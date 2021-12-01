package de.rieckpil.blog.gatling;

import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

public class CustomerRequestSimulation extends Simulation {

  Iterator<Map<String, Object>> feeder =
    Stream.generate((Supplier<Map<String, Object>>) ()
      -> Collections.singletonMap("username", UUID.randomUUID().toString())
    ).iterator();

  HttpProtocolBuilder httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
    .userAgentHeader("Gatling/Performance Test");

  ScenarioBuilder scn = scenario("Load Test HTTP POST API")
    .feed(feeder)
    .exec(http("spring-boot-backend-request")
      .post("/api/customers")
      .header("Content-Type", "application/json")
      .body(StringBody("{ \"username\": \"${username}\" }"))
    );

  public CustomerRequestSimulation() {
    setUp(scn.injectOpen(constantUsersPerSec(50).during(Duration.ofSeconds(15)).randomized()))
      .protocols(httpProtocol);
  }
}
