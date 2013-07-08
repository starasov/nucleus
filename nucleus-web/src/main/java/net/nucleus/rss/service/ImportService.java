package net.nucleus.rss.service;

import net.nucleus.rss.model.Outline;
import net.nucleus.rss.model.OutlineType;
import net.nucleus.rss.model.User;
import net.nucleus.rss.opml.OpmlImporter;
import net.nucleus.rss.opml.OpmlImporterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.InputStream;
import java.util.List;

/**
 * User: starasov
 * Date: 7/7/13
 * Time: 8:44 PM
 */
@Service
public class ImportService {
    private static final Logger logger = LoggerFactory.getLogger(ImportService.class);

    private EntityManager entityManager;

    @Transactional(readOnly = false)
    public boolean importOmpl(User user, InputStream inputStream) throws ImportServiceException {
        try {
            Outline root = findRoot(user);

            OpmlImporter.fromStream(inputStream, root);
            entityManager.persist(root);

            return !root.isEmpty();
        } catch (OpmlImporterException e) {
            throw new ImportServiceException(e);
        }
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Outline findRoot(User user) {
        List outlines = entityManager.createQuery("select o from Outline o where o.user = :user and o.parent is null")
                .setParameter("user", user)
                .getResultList();

        if (outlines.isEmpty()) {
            Outline root = new Outline();
            root.setUser(user);
            root.setType(OutlineType.FOLDER);

            return root;
        } else {
            return (Outline) outlines.get(0);
        }
    }
}
