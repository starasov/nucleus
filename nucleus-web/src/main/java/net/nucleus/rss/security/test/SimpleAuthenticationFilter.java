package net.nucleus.rss.security.test;

import net.nucleus.rss.model.User;
import net.nucleus.rss.security.google.GoogleAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: starasov
 * Date: 6/29/13
 * Time: 8:20 AM
 */
public class SimpleAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public SimpleAuthenticationFilter() {
        super("/auth/fake/success");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        User user = new User();
        user.setId(1);
        user.setUsername("Sergey Tarasov");
        return new GoogleAuthentication(user);
    }
}
