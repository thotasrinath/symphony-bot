package com.shris.bot.service;

import com.symphony.bdk.core.service.message.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private final DataService dataService;
    private final MessageService messageService;

    public void sendMessage(List<Long> users, String message) {
        for(Long user : users) {
            messageService.send(dataService.getImStreamId(user), message);
        }
    }
}
