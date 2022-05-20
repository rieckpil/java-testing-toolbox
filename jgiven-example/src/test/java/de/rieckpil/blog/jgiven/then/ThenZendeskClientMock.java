package de.rieckpil.blog.jgiven.then;

import com.tngtech.jgiven.Stage;
import de.rieckpil.blog.jgiven.service.ZendeskApiClient;
import de.rieckpil.blog.jgiven.pojo.User;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.mockito.Mockito;

@JGivenStage
public class ThenZendeskClientMock extends Stage<ThenZendeskClientMock> {

    @ExpectedScenarioState
    ZendeskApiClient zendeskApiClientMock;

    @ExpectedScenarioState
    User user;

    public ThenZendeskClientMock a_call_is_made_to_zendesk_to_create_the_ticket() {
        Mockito.verify(zendeskApiClientMock).createZendeskTicketForUser(user.getUserId());
        return this;
    }
}
