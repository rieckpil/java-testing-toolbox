package de.rieckpil.blog.jgiven.then;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@JGivenStage
public class ThenZendeskController extends Stage<ThenZendeskController> {

    @ExpectedScenarioState
    ResponseEntity<String> response;

    @ExpectedScenarioState
    Long zendeskTicketId;

    public ThenZendeskController the_response_indicates_success() {
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        return this;
    }

    public ThenZendeskController the_response_has_zendesk_ticket_id() {
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()).isEqualTo(zendeskTicketId.toString());

        return this;
    }
}
