package com.shris.bot;

import akka.actor.typed.ActorSystem;
import com.shris.bot.actor.ControlSystemActor;
import com.shris.bot.config.NotificationConfig;
import com.shris.bot.service.NotificationService;
import com.symphony.bdk.core.activity.ActivityMatcher;
import com.symphony.bdk.core.activity.form.FormReplyActivity;
import com.symphony.bdk.core.activity.form.FormReplyContext;
import com.symphony.bdk.core.activity.model.ActivityInfo;
import com.symphony.bdk.core.activity.model.ActivityType;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.message.model.Message;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class ScheduleCSFormActivity extends FormReplyActivity<FormReplyContext> {

    private final MessageService messageService;
    private final NotificationService notificationService;

    public ScheduleCSFormActivity(MessageService messageService, NotificationService notificationService) {
        this.messageService = messageService;
        this.notificationService = notificationService;
    }

    @Override
    public ActivityMatcher<FormReplyContext> matcher() {
        return context -> "schedule-cs-form".equals(context.getFormId())
                && "submit".equals(context.getFormValue("action"));
    }

    @Override
    public void onActivity(FormReplyContext context) {
        final String csName = context.getFormValue("cs-name");
        final String csDate = context.getFormValue("cs-date");
        final String csTime = context.getFormValue("cs-time");
        String retMsg = "";

        if (csName != null && !csName.isEmpty() && csDate != null && !csDate.isEmpty() && csTime != null && !csTime.isEmpty()) {
            String[] dateSplit = csDate.split("-");
            String[] timeSplit = csTime.split(":");

            LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2])),
                    LocalTime.of(Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1])));

            NotificationConfig cs2 = new NotificationConfig(csName, Duration.ofSeconds(15), localDateTime, List.of(349026222363716L));

            ActorSystem<ControlSystemActor.Command> system2 =
                    ActorSystem.create(ControlSystemActor.create(cs2,notificationService), csName + "-Actor");

            retMsg = system2.name();
        }
        final String message = "<messageML>Actor created '" + retMsg + "'</messageML>";
        this.messageService.send(context.getSourceEvent().getStream(), Message.builder().content(message).build());
    }

    @Override
    protected ActivityInfo info() {
        return new ActivityInfo().type(ActivityType.FORM)
                .name("Gif Display category form command")
                .description("\"Form handler for the Gif Category form\"");
    }
}
