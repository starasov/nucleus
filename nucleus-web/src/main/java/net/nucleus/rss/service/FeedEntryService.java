package net.nucleus.rss.service;

import net.nucleus.rss.fetch.FeedFetcher;
import net.nucleus.rss.fetch.FeedFetcherException;
import net.nucleus.rss.model.FeedEntry;
import net.nucleus.rss.model.Outline;
import net.nucleus.rss.model.User;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

/**
 * User: starasov
 * Date: 5/15/13
 * Time: 7:27 PM
 */
@Service
public class FeedEntryService {
    private static final Logger logger = LoggerFactory.getLogger(FeedEntryService.class);

    private EntityManager entityManager;
    private FeedFetcher feedFetcher;

    public Outline findFeed(User user, int feedId) {
        return entityManager.find(Outline.class, feedId);
    }

    public long feedEntriesCount(Outline outline) {
        return (Long) entityManager.createQuery("select count(e.id) from FeedEntry e where e.feed = :outline")
                .setParameter("outline", outline).getSingleResult();
    }

    public List<FeedEntry> findEntries(Outline outline, int offset, int pageSize) {
        return (List<FeedEntry>) entityManager.createQuery("select e from FeedEntry e where e.feed = :outline order by e.entryTimestamp")
                .setParameter("outline", outline)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Transactional
    public void markEntryAsRead(User loggedInUser, int entryId) {
        entityManager.createQuery("update FeedEntry e set e.readFlag = true where e.id = :id")
                .setParameter("id", entryId)
                .executeUpdate();
    }

    /**
     * TODO: split fetching and persistence into separate steps to not consume a transaction.
     *
     * @param outline
     * @throws FeedEntryServiceException
     */
    @Transactional
    public Set<FeedEntry> updateFeed(Outline outline) throws FeedEntryServiceException {
        try {
            Set<FeedEntry> feedEntries = feedFetcher.fetch(outline);

            List<FeedEntry> latestFeedEntry =
                    (List<FeedEntry>) entityManager.createQuery("select e from FeedEntry e where e.feed = :outline order by e.entryTimestamp")
                            .setParameter("outline", outline)
                            .setMaxResults(feedEntries.size())
                            .getResultList();


            persistNewFeedEntries(feedEntries, latestFeedEntry);

            return feedEntries;
        } catch (FeedFetcherException e) {
            throw new FeedEntryServiceException("Failed to import entries.", e);
        }
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    @Qualifier("decorator")
    public void setFeedFetcher(FeedFetcher feedFetcher) {
        this.feedFetcher = feedFetcher;
    }

    private void persistNewFeedEntries(@NotNull Set<FeedEntry> newFeedEntries,
                                       @NotNull List<FeedEntry> lastPersistentEntries) {
        logger.debug("[persistNewFeedEntries] - newFeedEntries: {}, lastPersistentEntries: {}", newFeedEntries.size(), lastPersistentEntries.size());

        for (FeedEntry lastPersistentEntry : lastPersistentEntries) {
            newFeedEntries.remove(lastPersistentEntry);
        }

        logger.debug("[persistNewFeedEntries] - filtered - newFeedEntries: {}", newFeedEntries.size());

        for (FeedEntry newFeedEntry : newFeedEntries) {
            entityManager.persist(newFeedEntry);
        }

        logger.debug("[persistNewFeedEntries] - end");
    }
}
