package de.rieckpil.blog.jgiven.given;

import de.rieckpil.blog.jgiven.service.UserService;
import de.rieckpil.blog.jgiven.service.ZendeskApiClient;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@JGivenStage
public class GivenZendeskClientMock extends Stage<GivenZendeskClientMock> {

    @Autowired
    UserService userService;

    @ProvidedScenarioState
    ZendeskApiClient zendeskApiClientMock;

    @ProvidedScenarioState
    Long zendeskTicketId = 1234L;

    public GivenZendeskClientMock a_mock_for_zendesk_client() {
        zendeskApiClientMock = mock(ZendeskApiClient.class);

        ReflectionTestUtils.setField(userService,
                                     "zendeskApiClient",
                                     zendeskApiClientMock);
        return this;
    }

    public GivenZendeskClientMock the_mock_allows_ticket_creation(){
       Mockito.when(zendeskApiClientMock.createZendeskTicketForUser(any())).thenReturn(zendeskTicketId);
        return this;
    }


}
