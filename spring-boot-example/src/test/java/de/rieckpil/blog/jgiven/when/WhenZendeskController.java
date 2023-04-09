package de.rieckpil.blog.jgiven.when;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import de.rieckpil.blog.registration.User;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@JGivenStage
public class WhenZendeskController extends Stage<WhenZendeskController> {

  TestRestTemplate testRestTemplate = new TestRestTemplate();

  private static final String BASE_URL = "http://localhost:";

  @LocalServerPort private Integer port;

  @ExpectedScenarioState private User user;

  @ProvidedScenarioState ResponseEntity<String> response;

  public void the_user_creates_tries_to_create_a_zendesk_ticket() {
    response =
        testRestTemplate.getForEntity(
            BASE_URL + port + "/zendesk/create-zendesk-ticket/" + user.getId(), String.class);
  }
}
