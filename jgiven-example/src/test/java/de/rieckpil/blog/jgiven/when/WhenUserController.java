package de.rieckpil.blog.jgiven.when;

import de.rieckpil.blog.jgiven.pojo.User;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@JGivenStage
public class WhenUserController extends Stage<WhenUserController> {

    TestRestTemplate testRestTemplate = new TestRestTemplate();

    private static final String  BASE_URL = "http://localhost:";

    @LocalServerPort
    private Integer port;

    @ExpectedScenarioState
    private User user;

    @ProvidedScenarioState
    ResponseEntity<String> response;

    public void the_user_creates_tries_to_create_a_zendesk_ticket(){
         response = testRestTemplate.getForEntity(BASE_URL+port+"/user/create-zendesk-ticket/"+user.getUserId(),
                                                                               String.class);
    }

}
