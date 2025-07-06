package com.shris.bot.service;

import com.symphony.bdk.core.service.stream.StreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataService {

    private final StreamService streamService;

    @Cacheable("im-stream-id")
    public String getImStreamId(long userId) {
        return streamService.create(userId).getId();
    }
}
