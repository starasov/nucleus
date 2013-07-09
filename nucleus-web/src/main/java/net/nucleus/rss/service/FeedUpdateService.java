package net.nucleus.rss.service;

import com.google.common.base.Optional;
import net.nucleus.rss.model.Outline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * User: starasov
 * Date: 7/8/13
 * Time: 10:22 PM
 */
@Service
public class FeedUpdateService {

    private FeedService feedService;

    @Scheduled(fixedRate = 10000)
    public void scheduleUpdates() throws FeedServiceException {
        Optional<Outline> mostOutdatedFeed = feedService.findMostOutdatedFeed();
        if (mostOutdatedFeed.isPresent()) {
            Date date = new Date();
            Date lastUpdateTime = mostOutdatedFeed.get().getLastUpdateTime();
            if (isFeedUpdateRequired(date, lastUpdateTime)) {
                feedService.updateFeed(mostOutdatedFeed.get());
            }
        }

    }

    private boolean isFeedUpdateRequired(Date date, Date lastUpdateTime) {
        return lastUpdateTime == null || (date.getTime() - lastUpdateTime.getTime()) > 1000 * 60 * 60 * 15;
    }

    @Autowired
    public void setFeedService(FeedService feedService) {
        this.feedService = feedService;
    }
}
