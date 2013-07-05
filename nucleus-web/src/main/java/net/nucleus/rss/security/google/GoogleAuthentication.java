package net.nucleus.rss.security.google;

import net.nucleus.rss.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

/**
 * User: starasov
 * Date: 6/24/13
 * Time: 7:00 PM
 */
public class GoogleAuthentication implements Authentication {

    private final User user;

    public GoogleAuthentication(@NotNull User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return createAuthorityList("ROLE_USER");
    }

    @Override
    public Object getCredentials() {
        return user.getEmail();
    }

    @Override
    public Object getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
    }

    @Override
    public String getName() {
        return user.getUsername();
    }
}
