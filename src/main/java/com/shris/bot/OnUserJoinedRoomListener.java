package com.shris.bot;

import com.symphony.bdk.core.service.datafeed.RealTimeEventListener;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.message.model.Message;
import com.symphony.bdk.gen.api.model.V4Initiator;
import com.symphony.bdk.gen.api.model.V4UserJoinedRoom;
import com.symphony.bdk.template.api.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static java.util.Collections.singletonMap;

@Component
public class OnUserJoinedRoomListener implements RealTimeEventListener {
  private final MessageService messageService;
  private final Template template;

  @Autowired
  public OnUserJoinedRoomListener(MessageService messageService) {
    this.messageService = messageService;
    this.template = messageService.templates().newTemplateFromClasspath("/templates/welcome.ftl");
  }

  @Override
  public void onUserJoinedRoom(V4Initiator initiator, V4UserJoinedRoom event) {
    final String userDisplayName = event.getAffectedUser().getDisplayName();
    this.messageService.send(event.getStream(),
        Message.builder().template(this.template, singletonMap("name", userDisplayName)).build());
  }
}
