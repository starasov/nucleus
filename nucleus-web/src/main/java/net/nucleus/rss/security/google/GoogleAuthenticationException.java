package net.nucleus.rss.security.google;

import org.springframework.security.core.AuthenticationException;

/**
 * User: starasov
 * Date: 7/7/13
 * Time: 12:25 PM
 */
public class GoogleAuthenticationException extends AuthenticationException {
    public GoogleAuthenticationException(String msg) {
        super(msg);
    }
}
