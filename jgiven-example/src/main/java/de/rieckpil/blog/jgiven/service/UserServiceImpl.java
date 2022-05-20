package de.rieckpil.blog.jgiven.service;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final ZendeskApiClient zendeskApiClient;

    public UserServiceImpl(ZendeskApiClient zendeskApiClient) {
        this.zendeskApiClient = zendeskApiClient;
    }

    @Override
    public long userZendeskTicketForUser(String userIdStr) {
        Long userId = Long.parseLong(userIdStr);
       return zendeskApiClient.createZendeskTicketForUser(userId);
    }
}
