package net.nucleus.rss.service;

import net.nucleus.rss.model.Outline;
import net.nucleus.rss.model.User;
import net.nucleus.rss.opml.OpmlImporter;
import net.nucleus.rss.opml.OpmlImporterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;

/**
 * User: starasov
 * Date: 5/14/13
 * Time: 10:05 PM
 */
@Service
public class FixturesImportService implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(FixturesImportService.class);

    private EntityManager entityManager;

    @Transactional(propagation = Propagation.MANDATORY)
    public void importTestData() throws OpmlImporterException, IOException {
        logger.debug("[importTestData] - begin");

        String username = "Sergey Tarasov";
        List users = entityManager.createQuery("select u from User u where u.username = :username")
                .setParameter("username", username).getResultList();

        User user;
        if (users.isEmpty()) {
            user = new User();
            user.setUsername(username);
            entityManager.persist(user);
        } else {
            user = (User) users.get(0);
        }

        long count = (Long) entityManager.createQuery("select count(o) from Outline o").getSingleResult();
        logger.debug("[importTestData] - imported count: {}", count);

        if (count == 0) {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource("classpath:/subscriptions.xml");

            Outline outline = OpmlImporter.fromStream(resource.getInputStream(), user);
            entityManager.persist(outline);
        }

        logger.debug("[importTestData] - end");
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            importTestData();
        } catch (OpmlImporterException e) {
            throw new IllegalStateException("Data import failed", e);
        } catch (IOException e) {
            throw new IllegalStateException("Data import failed", e);
        }
    }
}
