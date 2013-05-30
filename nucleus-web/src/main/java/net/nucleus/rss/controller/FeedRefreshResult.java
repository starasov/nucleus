package net.nucleus.rss.controller;

import net.nucleus.rss.model.FeedEntry;

import java.util.Collection;

/**
 * User: starasov
 * Date: 5/29/13
 * Time: 9:36 PM
 */
public class FeedRefreshResult {
    private final long totalEntryPages;
    private final Collection<FeedEntry> newFeedEntries;

    public FeedRefreshResult(long totalEntryPages, Collection<FeedEntry> newFeedEntries) {
        this.totalEntryPages = totalEntryPages;
        this.newFeedEntries = newFeedEntries;
    }

    public long getTotalEntryPages() {
        return totalEntryPages;
    }

    public Collection<FeedEntry> getNewFeedEntries() {
        return newFeedEntries;
    }
}
