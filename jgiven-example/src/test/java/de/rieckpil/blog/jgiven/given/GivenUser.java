package de.rieckpil.blog.jgiven.given;

import de.rieckpil.blog.jgiven.pojo.User;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;

@JGivenStage
public class GivenUser extends Stage<GivenUser> {

    @ProvidedScenarioState
    private User user;

    public GivenUser a_user() {
        Long USER_ID = 9999L;
        user = new User(USER_ID);
        return this;
    }
}
