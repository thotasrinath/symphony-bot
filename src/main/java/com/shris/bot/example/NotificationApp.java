package com.shris.bot.example;

import com.shris.bot.actor.ControlSystemActor;
import com.shris.bot.config.NotificationConfig;
import akka.actor.typed.ActorSystem;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class NotificationApp {

    public static void main(String[] args) {
        NotificationConfig cs1 = new NotificationConfig("CS-001", Duration.ofSeconds(10), LocalDateTime.now(), List.of(349026222363716L));
        NotificationConfig cs2 = new NotificationConfig("CS-002", Duration.ofSeconds(15), LocalDateTime.now(), List.of(349026222363716L));

        ActorSystem<ControlSystemActor.Command> system1 =
                ActorSystem.create(ControlSystemActor.create(cs1,null), "ControlSystemActor1");

        ActorSystem<ControlSystemActor.Command> system2 =
                ActorSystem.create(ControlSystemActor.create(cs2,null), "ControlSystemActor2");
    }
}
