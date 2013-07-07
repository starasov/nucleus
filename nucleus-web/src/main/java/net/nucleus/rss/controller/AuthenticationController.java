package net.nucleus.rss.controller;

import net.nucleus.rss.security.google.GoogleAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Handles authentication workflows.
 * <p/>
 * For Google OAuth2 authentication:
 * 1. Send request to Google where user should allow our application.
 * 2. Receive callback to our callback-url.
 * 2.1 In case if callback is successful proceed with user registration/login within the application. Proceed to step #3.
 * 2.2 Otherwise proceed to login page with explanation message why authentication wasn't successful and start from step #1.
 * 3. END!
 *
 * @author starasov
 */
@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private GoogleAuthenticationService googleAuthenticationService;

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
        Exception exception = readAuthenticationException(request);

        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("exception", exception);
        modelAndView.addObject("authenticationFailed", exception != null);

        return modelAndView;
    }

    @RequestMapping("/google")
    public String redirectGoogle() {
        String authorizationUrl = googleAuthenticationService.buildAuthorizationUrl();
        return "redirect:" + authorizationUrl;
    }

    @RequestMapping("/fake")
    public String redirectFake() {
        return "redirect:/auth/fake/success";
    }

    @Autowired
    public void setGoogleAuthenticationService(GoogleAuthenticationService googleAuthenticationService) {
        this.googleAuthenticationService = googleAuthenticationService;
    }

    private Exception readAuthenticationException(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Exception exception = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        return exception;
    }
}
