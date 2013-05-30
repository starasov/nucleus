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
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * User: starasov
 * Date: 5/12/13
 * Time: 12:21 AM
 */
public abstract class BaseFeedFetcher implements FeedFetcher {

    private HtmlSanitizer htmlSanitizer;

    @NotNull
    @Override
    public Set<FeedEntry> fetch(@NotNull Outline feed) throws FeedFetcherException {
        Assert.notNull(feed, "feed parameter can't be null.");

        try {
            Set<FeedEntry> feedEntries = new LinkedHashSet<FeedEntry>();
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

    private void fetchInternal(@NotNull Outline feed, @NotNull Set<FeedEntry> feedEntries) throws IOException, FeedException {
        XmlReader reader = null;
        try {
            reader = createXmlReader(feed);
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

        String descriptionRaw = syndEntry.getDescription().getValue();
        String descriptionNormalized = descriptionRaw.length() > 8000 ? descriptionRaw.substring(0, 8000) : descriptionRaw;
        String descriptionSanitized = htmlSanitizer.sanitize(descriptionNormalized);

        feedEntry.setFullDescription(descriptionSanitized);

        feedEntry.setPublicationDate(syndEntry.getPublishedDate());

        return feedEntry;
    }

    @NotNull
    protected abstract XmlReader createXmlReader(@NotNull Outline outline) throws IOException;
}
