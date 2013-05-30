package net.nucleus.rss.service;

import net.nucleus.rss.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User: starasov
 * Date: 5/30/13
 * Time: 7:47 AM
 */
@Service
public class UserService {
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public User login() {
        return (User) entityManager.createQuery("select u from User u where u.username = 'test'").getSingleResult();
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
