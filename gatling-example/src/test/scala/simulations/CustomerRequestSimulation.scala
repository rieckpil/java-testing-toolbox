package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import java.util.UUID
import scala.concurrent.duration._

class CustomerRequestSimulation extends Simulation {

  def randomUsername() = UUID.randomUUID().toString

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
    .userAgentHeader("Gatling/Performance Test")

  val scn = scenario("Load Test HTTP POST API")
    .exec(http("spring-boot-backend-request")
      .post("/api/customers")
      .body(StringBody(_ => s"""{ "username": "${randomUsername()}" }"""))
      .asJson
    )

  setUp(scn.inject(constantUsersPerSec(50).during(15.seconds).randomized))
    .protocols(httpProtocol)
}
