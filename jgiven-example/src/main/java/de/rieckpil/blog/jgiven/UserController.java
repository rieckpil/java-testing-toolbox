package de.rieckpil.blog.jgiven;

import de.rieckpil.blog.jgiven.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/create-zendesk-ticket/{userId}")
    ResponseEntity<String> getUserData(@PathVariable String userId){
        final long zendeskTicketId = userService.userZendeskTicketForUser(userId);
        return ResponseEntity.ok().body(String.valueOf(zendeskTicketId));
    }
}
