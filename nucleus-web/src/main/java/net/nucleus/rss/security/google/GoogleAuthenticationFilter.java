package net.nucleus.rss.security.google;

import net.nucleus.rss.model.User;
import net.nucleus.rss.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: starasov
 * Date: 6/16/13
 * Time: 12:47 PM
 */
public class GoogleAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private UserService userService;
    private GoogleAuthenticationService googleAuthenticationService;
    private boolean testModeFlag;

    public GoogleAuthenticationFilter() {
        super("/auth/google_callback");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String code = request.getParameter("code");
        String error = request.getParameter("error");

        String email = request.getParameter("email");
        if (testModeFlag && StringUtils.isNotBlank(email)) {
            return new GoogleAuthentication(userService.login(email).get());
        }

        if (StringUtils.isBlank(code)) {
            throw new GoogleAuthenticationException("Google Authentication Failed: " + error);
        }

        GoogleUserProfile googleUserProfile = googleAuthenticationService.authenticate(code);
        User user = userService.registerNewUserIfRequired(fromGoogleProfile(googleUserProfile));

        return new GoogleAuthentication(user);
    }

    public void setUserService(@NotNull UserService userService) {
        this.userService = userService;
    }

    public void setGoogleAuthenticationService(@NotNull GoogleAuthenticationService googleAuthenticationService) {
        this.googleAuthenticationService = googleAuthenticationService;
    }

    public void setTestModeFlag(boolean testModeFlag) {
        this.testModeFlag = testModeFlag;
    }

    private User fromGoogleProfile(GoogleUserProfile googleUserProfile) {
        User user = new User();

        user.setUsername(googleUserProfile.getName());
        user.setEmail(googleUserProfile.getEmail());

        return user;
    }
}
