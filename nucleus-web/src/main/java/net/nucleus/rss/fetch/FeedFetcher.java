package net.nucleus.rss.fetch;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import net.nucleus.rss.model.Feed;
import net.nucleus.rss.model.FeedEntry;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * User: starasov
 * Date: 5/12/13
 * Time: 12:21 AM
 */
public class FeedFetcher {

    public boolean validate(URL url) {
        return true;
    }

    public List<FeedEntry> fetch(Feed feed) throws FeedFetcherException {
        Assert.notNull(feed, "feed parameter can't be null.");

        try {
            List<FeedEntry> feedEntries = new ArrayList<FeedEntry>();
            fetchInternal(feed, feedEntries);
            return feedEntries;
        } catch (IOException e) {
            throw new FeedFetcherException("Failed to read feed " + feed.getUrl(), e);
        } catch (FeedException e) {
            throw new FeedFetcherException("Failed to read feed " + feed.getUrl(), e);
        }
    }

    private void fetchInternal(Feed feed, List<FeedEntry> feedEntries) throws IOException, FeedException {
        XmlReader reader = null;
        try {
            reader = new XmlReader(feed.getUrl());
            SyndFeed syndFeed = new SyndFeedInput().build(reader);

            for (Object o : syndFeed.getEntries()) {
                feedEntries.add(fromSyndEntry((SyndEntry) o));
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static FeedEntry fromSyndEntry(SyndEntry syndEntry) {
        FeedEntry feedEntry = new FeedEntry();
        feedEntry.setExternalUrl(syndEntry.getUri());
        feedEntry.setTitle(syndEntry.getTitle());
        feedEntry.setFullDescription(syndEntry.getDescription().getValue());
        feedEntry.setPublicationDate(syndEntry.getPublishedDate());

        return feedEntry;
    }
}
