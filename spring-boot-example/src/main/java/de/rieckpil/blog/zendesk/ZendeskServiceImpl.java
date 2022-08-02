package de.rieckpil.blog.zendesk;

import org.springframework.stereotype.Component;

@Component
public class ZendeskServiceImpl implements ZendeskService {


    private final ZendeskApiClient zendeskApiClient;

    public ZendeskServiceImpl(ZendeskApiClient zendeskApiClient) {
        this.zendeskApiClient = zendeskApiClient;
    }

    @Override
    public long createZendeskTicketForUser(String userIdStr) {
      Long userId = Long.parseLong(userIdStr);
      return zendeskApiClient.createZendeskTicketForUser(userId);
    }
}
