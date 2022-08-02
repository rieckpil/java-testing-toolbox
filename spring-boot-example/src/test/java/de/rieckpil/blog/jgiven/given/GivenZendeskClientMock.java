package de.rieckpil.blog.jgiven.given;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import de.rieckpil.blog.zendesk.ZendeskApiClient;
import de.rieckpil.blog.zendesk.ZendeskService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

@JGivenStage
public class GivenZendeskClientMock extends Stage<GivenZendeskClientMock> {

    @Autowired
    ZendeskService zendeskService;

    @ProvidedScenarioState
    ZendeskApiClient zendeskApiClientMock;

    @ProvidedScenarioState
    Long zendeskTicketId = 1234L;

    public GivenZendeskClientMock a_mock_for_zendesk_client() {
        zendeskApiClientMock = mock(ZendeskApiClient.class);

        ReflectionTestUtils.setField(zendeskService,
                                     "zendeskApiClient",
                                     zendeskApiClientMock);
        return this;
    }

    public GivenZendeskClientMock the_mock_allows_ticket_creation(){
       Mockito.when(zendeskApiClientMock.createZendeskTicketForUser(any())).thenReturn(zendeskTicketId);
        return this;
    }


}
