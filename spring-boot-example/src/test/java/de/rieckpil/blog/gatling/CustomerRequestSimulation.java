package de.rieckpil.blog.gatling;

import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.http.HttpDsl.header;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class CustomerRequestSimulation extends Simulation {

  HttpProtocolBuilder httpProtocol = HttpDsl.http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
    .userAgentHeader("Gatling/Performance Test");

  Iterator<Map<String, Object>> feeder =
    Stream.generate((Supplier<Map<String, Object>>) ()
      -> Collections.singletonMap("username", UUID.randomUUID().toString())
    ).iterator();

  ScenarioBuilder scn = CoreDsl.scenario("Load Test Creating Customers")
    .feed(feeder)
    .exec(http("create-customer-request")
      .post("/api/customers")
      .header("Content-Type", "application/json")
      .body(StringBody("{ \"username\": \"${username}\" }"))
      .check(status().is(201))
      .check(header("Location").saveAs("location"))
    )
    .exec(http("get-customer-request")
      .get(session -> session.getString("location"))
      .check(status().is(200))
    );

  public CustomerRequestSimulation() {
    this.setUp(scn.injectOpen(constantUsersPerSec(50).during(Duration.ofSeconds(15))))
      .protocols(httpProtocol);
  }
}
