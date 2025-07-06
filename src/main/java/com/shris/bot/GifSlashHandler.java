package com.shris.bot;

import com.symphony.bdk.core.activity.command.CommandContext;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.message.model.Message;
import com.symphony.bdk.spring.annotation.Slash;
import com.symphony.bdk.template.api.Template;
import org.springframework.stereotype.Component;

@Component
public class GifSlashHandler {
  private final MessageService messageService;
  private final Template template;

  public GifSlashHandler(MessageService messageService) {
    this.messageService = messageService;
    this.template = messageService.templates().newTemplateFromClasspath("/templates/gif.ftl");
  }

  @Slash(value = "/gif", mentionBot = false)
  public void onSlashGif(CommandContext context) {
    this.messageService.send(context.getStreamId(), Message.builder().template(this.template).build());
  }
}
