package com.shris.bot.actor;

import akka.actor.typed.*;
import akka.actor.typed.javadsl.*;
import com.shris.bot.config.NotificationConfig;
import com.shris.bot.service.NotificationService;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
public class ControlSystemActor extends AbstractBehavior<ControlSystemActor.Command> {

    public interface Command {}
    public static class Tick implements Command {}

    private final TimerScheduler<Command> timers;
    private final NotificationConfig config;
    private final NotificationService notificationService;
    private boolean notified = false;

    public static Behavior<Command> create(NotificationConfig config,NotificationService notificationService) {
        return Behaviors.setup(ctx ->
                Behaviors.withTimers(timers -> new ControlSystemActor(ctx, timers, config,notificationService))
        );
    }

    private ControlSystemActor(ActorContext<Command> context, TimerScheduler<Command> timers, NotificationConfig config,NotificationService notificationService) {
        super(context);
        this.timers = timers;
        this.config = config;
        this.notificationService = notificationService;

        // Start periodic timer
        timers.startTimerAtFixedRate(new Tick(), config.interval);
        context.getLog().info("ControlSystemActor {} started with interval {}", config.controlSystemId, config.interval);
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(Tick.class, this::onTick)
                .build();
    }

    private Behavior<Command> onTick(Tick msg) {

        if(!notified) {
            if(LocalDateTime.now().isAfter(config.localDateTime)){
                getContext().getLog().info("Sending notification for control system {} to users: {}",
                        config.controlSystemId, config.users);

                // Simulate notification
                if(notificationService != null) {
                    notificationService.sendMessage(config.users, "Scheduled Alert! message from " + config.controlSystemId);
                }else {
                    log.warn("NotificationService is null, Dummy trigger");
                }

                notified = true;
            }
        }
        return this;
    }
}