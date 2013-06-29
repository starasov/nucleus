package net.nucleus.rss.service;

import net.nucleus.rss.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: starasov
 * Date: 5/30/13
 * Time: 7:47 AM
 */
@Service
public class UserService {
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public User login(@NotNull String username) {
        return (User) entityManager.createQuery("select u from User u where u.username = :username")
                .setParameter("username", username)
                .getSingleResult();
    }


    @NotNull
    @Transactional(readOnly = false)
    public User registerNewUserIfRequired(@NotNull User user) {
        List<User> users = (List<User>) entityManager.createQuery("select u from User u where u.username = :username")
                .setParameter("username", user.getUsername())
                .getResultList();

        if (users.isEmpty()) {
            entityManager.persist(user);
            return user;
        } else {
            return users.get(0);
        }
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
