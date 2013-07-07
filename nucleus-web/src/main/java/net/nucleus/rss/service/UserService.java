package net.nucleus.rss.service;

import com.google.common.base.Optional;
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
    public Optional<User> login(@NotNull String email) {
        User user = (User) entityManager.createQuery("select u from User u where u.email = :email")
                .setParameter("email", email)
                .getSingleResult();

        return Optional.fromNullable(user);
    }

    @NotNull
    @Transactional(readOnly = false)
    public User registerNewUserIfRequired(@NotNull User user) {
        List users = entityManager.createQuery("select u from User u where u.email = :email")
                .setParameter("email", user.getEmail())
                .getResultList();

        if (users.isEmpty()) {
            entityManager.persist(user);
            return user;
        } else {
            return (User) users.get(0);
        }
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
