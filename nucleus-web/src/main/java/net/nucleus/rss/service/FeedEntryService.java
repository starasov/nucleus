package net.nucleus.rss.service;

import net.nucleus.rss.fetch.FeedFetcher;
import net.nucleus.rss.fetch.FeedFetcherException;
import net.nucleus.rss.model.FeedEntry;
import net.nucleus.rss.model.Outline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: starasov
 * Date: 5/15/13
 * Time: 7:27 PM
 */
@Service
public class FeedEntryService {

    private EntityManager entityManager;
    private FeedFetcher feedFetcher;

    public Outline findFeed(int feedId) {
        return entityManager.find(Outline.class, feedId);
    }

    public List<FeedEntry> findEntries(Outline outline, int offset, int pageSize) {
        return (List<FeedEntry>) entityManager.createQuery("select e from FeedEntry e where e.feed = :outline order by e.entryTimestamp")
                .setParameter("outline", outline)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Transactional
    public void updateFeed(Outline outline) throws FeedEntryServiceException {
        try {
            List<FeedEntry> feedEntries = feedFetcher.fetch(outline);

            List<FeedEntry> latestFeedEntry =
                    (List<FeedEntry>) entityManager.createQuery("select e from FeedEntry e where e.feed = :outline order by e.entryTimestamp")
                            .setParameter("outline", outline)
                            .setMaxResults(feedEntries.size())
                            .getResultList();


            if (latestFeedEntry.isEmpty()) {
                importAllFeedEntries(feedEntries);
            } else {
                importFeedEntriesAfterLastStored(feedEntries, latestFeedEntry.get(0));
            }
        } catch (FeedFetcherException e) {
            throw new FeedEntryServiceException("Failed to import entries.", e);
        }
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    public void setFeedFetcher(FeedFetcher feedFetcher) {
        this.feedFetcher = feedFetcher;
    }

    private void importAllFeedEntries(List<FeedEntry> feedEntries) {
        for (FeedEntry feedEntry : feedEntries) {
            entityManager.persist(feedEntry);
        }
    }

    private void importFeedEntriesAfterLastStored(List<FeedEntry> feedEntries, FeedEntry feedEntry) {
        for (FeedEntry entry : feedEntries) {
            if (entry.getExternalUrl().equals(feedEntry.getExternalUrl())) {
                return;
            }

            entityManager.persist(entry);
        }
    }
}
