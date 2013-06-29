package net.nucleus.rss.security.google;

import net.nucleus.rss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * User: starasov
 * Date: 6/24/13
 * Time: 6:57 PM
 */
@Component
public class GoogleAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;
    private GoogleAuthenticationService googleAuthenticationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(GoogleAuthentication.class);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setGoogleAuthenticationService(GoogleAuthenticationService googleAuthenticationService) {
        this.googleAuthenticationService = googleAuthenticationService;
    }
}
