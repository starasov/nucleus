package net.nucleus.rss.service;

import com.google.common.base.Optional;
import net.nucleus.rss.fetch.FeedFetcher;
import net.nucleus.rss.fetch.FeedFetcherException;
import net.nucleus.rss.model.FeedEntry;
import net.nucleus.rss.model.Outline;
import net.nucleus.rss.model.OutlineType;
import net.nucleus.rss.model.User;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * User: starasov
 * Date: 5/15/13
 * Time: 7:27 PM
 */
@Service
public class FeedService {
    private static final Logger logger = LoggerFactory.getLogger(FeedService.class);
    private static final int MAX_UPDATE_FAILED_COUNT = 5;

    private EntityManager entityManager;
    private FeedFetcher feedFetcher;
    private PlatformTransactionManager transactionManager;

    @Transactional(readOnly = true)
    public Optional<Outline> findRootOutlineLazy(User user) {
        List resultList = entityManager.createQuery("select o from Outline o left join fetch o.children where o.user = :user and o.parent is null")
                .setParameter("user", user)
                .getResultList();

        return resultList.isEmpty() ? Optional.<Outline>absent() : Optional.of((Outline) resultList.get(0));
    }

    @Transactional(readOnly = true)
    public Outline findRootOutlineEager(User user) {
        List resultList = entityManager.createQuery("select o from Outline o left join fetch o.children where o.user = :user order by o.parent.id asc")
                .setParameter("user", user)
                .getResultList();

        return resultList.isEmpty() ? new Outline() : (Outline) resultList.get(resultList.size() - 1);
    }

    @Transactional(readOnly = true)
    public Outline findFeed(User user, int feedId) {
        return entityManager.find(Outline.class, feedId);
    }

    @Transactional(readOnly = true)
    public long feedEntriesCount(Outline outline) {
        return (Long) entityManager.createQuery("select count(e.id) from FeedEntry e where e.feed = :outline")
                .setParameter("outline", outline)
                .getSingleResult();
    }

    @Transactional(readOnly = true)
    public Optional<Outline> findMostOutdatedFeed() {
        List<Outline> outlines = entityManager.createQuery("select o from Outline o where o.type = :outlineType and o.failedUpdates < :failedUpdates order by o.lastUpdateTime", Outline.class)
                .setParameter("outlineType", OutlineType.FEED)
                .setParameter("failedUpdates", MAX_UPDATE_FAILED_COUNT)
                .setMaxResults(1)
                .getResultList();

        return outlines.isEmpty() ? Optional.<Outline>absent() : Optional.of(outlines.get(0));
    }

    @Transactional(readOnly = true)
    public List<FeedEntry> findEntries(Outline outline, int offset, int pageSize) {
        return (List<FeedEntry>) entityManager.createQuery("select e from FeedEntry e where e.feed = :outline order by e.entryTimestamp")
                .setParameter("outline", outline)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Transactional(readOnly = false)
    public void markEntryAsRead(User loggedInUser, int entryId) {
        entityManager.createQuery("update FeedEntry e set e.readFlag = true where e.id = :id")
                .setParameter("id", entryId)
                .executeUpdate();
    }

    /**
     * @param outline
     * @throws FeedServiceException
     */
    public Set<FeedEntry> updateFeed(Outline outline) throws FeedServiceException {
        if (outline.getFailedUpdates() > MAX_UPDATE_FAILED_COUNT) {
            return Collections.emptySet();
        }

        Set<FeedEntry> feedEntries = fetchFeedEntries(outline);
        updateFeedEntries(outline, feedEntries);
        return feedEntries;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    @Qualifier("http")
    public void setFeedFetcher(FeedFetcher feedFetcher) {
        this.feedFetcher = feedFetcher;
    }

    @Autowired
    public void setFeedFetcher(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    private Set<FeedEntry> fetchFeedEntries(Outline outline) {
        try {
            return feedFetcher.fetch(outline);
        } catch (FeedFetcherException e) {
            reportFailedUpdate(outline);
            return Collections.emptySet();
        }
    }

    private void reportFailedUpdate(final Outline outline) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                entityManager.createQuery("update Outline o set o.failedUpdates = :failedUpdates where o.id = :id")
                        .setParameter("id", outline.getId())
                        .setParameter("failedUpdates", outline.getFailedUpdates() + 1)
                        .executeUpdate();
            }
        });
    }

    private void updateFeedEntries(final Outline outline, final Set<FeedEntry> feedEntries) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                List<FeedEntry> latestFeedEntries =
                        (List<FeedEntry>) entityManager.createQuery("select e from FeedEntry e where e.feed = :outline order by e.entryTimestamp")
                                .setParameter("outline", outline)
                                .setMaxResults(feedEntries.size())
                                .getResultList();

                persistNewFeedEntries(feedEntries, latestFeedEntries);

                entityManager.createQuery("update Outline o set o.lastUpdateTime = :lastUpdateTime, o.failedUpdates = 0 where o.id = :id")
                        .setParameter("id", outline.getId())
                        .setParameter("lastUpdateTime", new Date())
                        .executeUpdate();
            }
        });
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
