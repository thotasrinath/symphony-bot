package com.shris.bot;

import com.symphony.bdk.core.activity.command.CommandContext;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.message.model.Message;
import com.symphony.bdk.spring.annotation.Slash;
import com.symphony.bdk.template.api.Template;
import org.springframework.stereotype.Component;

@Component
public class ScheduleCSSlashHandler {
    private final MessageService messageService;
    private final Template schedCsTemplate;

    public ScheduleCSSlashHandler(MessageService messageService) {
        this.messageService = messageService;
        this.schedCsTemplate = messageService.templates().newTemplateFromClasspath("/templates/schedule-cs.ftl");
    }

    @Slash(value = "/schedule-cs", mentionBot = false)
    public void onScheduleCs(CommandContext context) {
        this.messageService.send(context.getStreamId(), Message.builder().template(this.schedCsTemplate).build());
    }
}
