package net.nucleus.rss.fetch;

import net.nucleus.rss.model.FeedEntry;
import net.nucleus.rss.model.Outline;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * User: starasov
 * Date: 5/25/13
 * Time: 5:25 PM
 */
public interface FeedFetcher {
    @NotNull
    public Set<FeedEntry> fetch(@NotNull Outline feed) throws FeedFetcherException;
}
