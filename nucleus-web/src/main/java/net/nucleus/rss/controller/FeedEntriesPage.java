package net.nucleus.rss.controller;

import net.nucleus.rss.model.FeedEntry;

import java.util.List;

/**
 * User: starasov
 * Date: 5/27/13
 * Time: 10:08 PM
 */
public class FeedEntriesPage {
    private final List<FeedEntry> feedEntries;
    private final int page;
    private final int totalPages;

    public FeedEntriesPage(List<FeedEntry> feedEntries, int page, int totalPages) {
        this.feedEntries = feedEntries;
        this.page = page;
        this.totalPages = totalPages;
    }

    public List<FeedEntry> getFeedEntries() {
        return feedEntries;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
