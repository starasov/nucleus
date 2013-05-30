package net.nucleus.rss.fetch;

import net.nucleus.rss.model.FeedEntry;
import net.nucleus.rss.model.Outline;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * User: starasov
 * Date: 5/26/13
 * Time: 9:51 PM
 */
@Component("decorator")
@Qualifier("decorator")
public class FeedFetcherMultiplierDecorator implements FeedFetcher {

    private FeedFetcher feedFetcher;

    @NotNull
    @Override
    public Set<FeedEntry> fetch(@NotNull Outline feed) throws FeedFetcherException {
        Set<FeedEntry> feedEntries = new HashSet<FeedEntry>();

        for (int i = 0; i < 30; i++) {
            Set<FeedEntry> fetchedEntries = feedFetcher.fetch(feed);
            for (FeedEntry fetchedEntry : fetchedEntries) {
                fetchedEntry.setExternalUrl(fetchedEntry.getExternalUrl() + "/" + i);
                fetchedEntry.setTitle(fetchedEntry.getTitle() + i);
                feedEntries.add(fetchedEntry);
            }
        }

        return feedEntries;
    }

    @Autowired
    @Qualifier("local")
    public void setFeedFetcher(FeedFetcher feedFetcher) {
        this.feedFetcher = feedFetcher;
    }
}
