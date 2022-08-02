package de.rieckpil.blog.jgiven.given;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import de.rieckpil.blog.registration.User;
import java.time.LocalDateTime;

@JGivenStage
public class GivenUser extends Stage<GivenUser> {

    @ProvidedScenarioState
    private User user;

    public GivenUser a_user() {
        user = new User(123L,"SOME_CUTE_USER_NAME", LocalDateTime.now());
        return this;
    }
}
