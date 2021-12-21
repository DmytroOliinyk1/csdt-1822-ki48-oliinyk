package com.lpnu.virtual.library.core.feed.scheduler;

import com.lpnu.virtual.library.core.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedScheduler {
    private final FeedService feedService;

    @Scheduled(cron = "0 1 1 * * ?")
    public void cleanUpOldSavedSearch() {
        feedService.cleanUpForAllUser();
    }

}
