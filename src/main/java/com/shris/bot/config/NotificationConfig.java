package com.shris.bot.config;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class NotificationConfig {
    public final String controlSystemId;
    public final Duration interval;
    public final LocalDateTime localDateTime;
    public final List<Long> users;

    public NotificationConfig(String controlSystemId, Duration interval,LocalDateTime localDateTime, List<Long> users) {
        this.controlSystemId = controlSystemId;
        this.interval = interval;
        this.localDateTime = localDateTime;
        this.users = users;
    }
}

