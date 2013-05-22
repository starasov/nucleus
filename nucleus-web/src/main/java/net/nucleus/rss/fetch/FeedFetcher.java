package net.nucleus.rss.fetch;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import net.nucleus.rss.model.FeedEntry;
import net.nucleus.rss.model.Outline;
import net.nucleus.rss.sanitize.HtmlSanitizer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
@Component
public class FeedFetcher {

    private HtmlSanitizer htmlSanitizer;

    @NotNull
    public List<FeedEntry> fetch(@NotNull Outline feed) throws FeedFetcherException {
        Assert.notNull(feed, "feed parameter can't be null.");

        try {
            List<FeedEntry> feedEntries = new ArrayList<FeedEntry>();
            fetchInternal(feed, feedEntries);
            return feedEntries;
        } catch (IOException e) {
            throw new FeedFetcherException("Failed to read feed " + feed.getXmlUrl(), e);
        } catch (FeedException e) {
            throw new FeedFetcherException("Failed to read feed " + feed.getXmlUrl(), e);
        }
    }

    @Autowired
    public void setHtmlSanitizer(HtmlSanitizer htmlSanitizer) {
        this.htmlSanitizer = htmlSanitizer;
    }

    private void fetchInternal(Outline feed, List<FeedEntry> feedEntries) throws IOException, FeedException {
        XmlReader reader = null;
        try {
            URL url = new URL(feed.getXmlUrl());
            reader = new XmlReader(url);
            SyndFeed syndFeed = new SyndFeedInput().build(reader);

            for (Object o : syndFeed.getEntries()) {
                FeedEntry feedEntry = fromSyndEntry((SyndEntry) o);
                feedEntry.setFeed(feed);
                feedEntries.add(feedEntry);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private FeedEntry fromSyndEntry(SyndEntry syndEntry) {
        FeedEntry feedEntry = new FeedEntry();
        feedEntry.setExternalUrl(syndEntry.getUri());
        feedEntry.setTitle(htmlSanitizer.sanitizeStrict(syndEntry.getTitle()));
        feedEntry.setFullDescription(htmlSanitizer.sanitize(syndEntry.getDescription().getValue()));

        String shortDescription = htmlSanitizer.sanitizeStrict(syndEntry.getDescription().getValue());
        String[] lines = shortDescription.split("\\r?\\n");

        feedEntry.setShortDescription(lines[0]);
        feedEntry.setPublicationDate(syndEntry.getPublishedDate());

        return feedEntry;
    }
}
