package de.rieckpil.blog.zendesk;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/zendesk")
public class ZendeskController {

  private final ZendeskService zendeskService;

  public ZendeskController(ZendeskService zendeskService) {
    this.zendeskService = zendeskService;
  }

  @GetMapping(path ="/create-zendesk-ticket/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  ResponseEntity<String> createZendeskTicket(@PathVariable String userId){
    final long zendeskTicketId = zendeskService.createZendeskTicketForUser(userId);
    return ResponseEntity.ok().body(String.valueOf(zendeskTicketId));
  }


}
