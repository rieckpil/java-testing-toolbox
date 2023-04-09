package de.rieckpil.blog.jgiven;

import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.integration.spring.junit5.SpringScenarioTest;
import de.rieckpil.blog.jgiven.given.GivenUser;
import de.rieckpil.blog.jgiven.given.GivenZendeskClientMock;
import de.rieckpil.blog.jgiven.then.ThenZendeskClientMock;
import de.rieckpil.blog.jgiven.then.ThenZendeskController;
import de.rieckpil.blog.jgiven.when.WhenZendeskController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ZendeskServiceComponentTests
    extends SpringScenarioTest<GivenUser, WhenZendeskController, ThenZendeskController> {

  @ScenarioStage GivenZendeskClientMock givenZendeskClientMock;

  @ScenarioStage ThenZendeskClientMock thenZendeskClientMock;

  @Test
  void
      when_user_creates_a_ticket_then_response_returns_zendesk_ticket_id_and_a_call_is_made_to_zendesk_api() {
    given().a_user().and();

    givenZendeskClientMock.a_mock_for_zendesk_client().and().the_mock_allows_ticket_creation();

    when().the_user_creates_tries_to_create_a_zendesk_ticket();

    then().the_response_indicates_success().and().the_response_has_zendesk_ticket_id().and();

    thenZendeskClientMock.a_call_is_made_to_zendesk_to_create_the_ticket();
  }
}
