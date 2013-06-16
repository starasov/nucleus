package net.nucleus.rss.security;

import net.nucleus.rss.model.User;
import net.nucleus.rss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

/**
 * A simple wrapper around {@link UserService} to provide Spring security integration point.
 * By default creates {@link UserDetails} for {@link User} model.
 * Assigns default role {@code ROLE_USER} to all loaded users.
 * <p/>
 * User: starasov
 * Date: 6/16/13
 * Time: 12:29 PM
 */
@Component
public class LocalUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User login = userService.login(username);
        return new org.springframework.security.core.userdetails.User(login.getUsername(), null, createAuthorityList("ROLE_USER"));
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
