package net.nucleus.rss.controller;

import net.nucleus.rss.auth.google.GoogleAuthenticationService;
import net.nucleus.rss.auth.google.GoogleUserProfile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

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
    public String login() {
        return "login";
    }

    @RequestMapping("/google")
    public String redirectGoogle() {
        String authorizationUrl = googleAuthenticationService.buildAuthorizationUrl();
        return "redirect:" + authorizationUrl;
    }

    /**
     * Handles callback from Google OAuth2 service.
     * <p/>
     * Sample response from Google (success):
     * http://localhost:8080/auth/google_callback?code=4/0cdUS_TCmXPjUQMdfxruvn7325ww.Es3poH-izQ4VmmS0T3UFEsMW33VWfgI#/0cdUS_TCmXPjUQMdfxruvn7325ww.Es3poH-izQ4VmmS0T3UFEsMW33VWfgI
     * <p/>
     * Sample response from Google (failure):
     * http://localhost:8080/auth/google_callback?error=access_denied#/google_callback?error=access_denied
     * <p/>
     * In case if response is successful following user identification steps are performed:
     * 1. We obtain access token from Google.
     * 2. Using access token we request user profile information.
     * 3. Using user profile information
     * 3.1 Create new account (if doesn't exists)
     * 3.2 Login user
     * 4. Redirect to landing page
     *
     * @param request a callback request sent by Google
     * @return redirect to the feeds page if authentication was successful or back to the login page otherwise.
     */
    @RequestMapping("/google_callback")
    public String googleCallback(HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        String error = request.getParameter("error");

        if (StringUtils.isBlank(code)) {
            return UriComponentsBuilder.fromUriString("redirect:/auth/login").queryParam("error", error).build().toUriString();
        }

        GoogleUserProfile googleUserProfile = googleAuthenticationService.authenticate(code);

        return "redirect:welcome";
    }

    @Autowired
    public void setGoogleAuthenticationService(GoogleAuthenticationService googleAuthenticationService) {
        this.googleAuthenticationService = googleAuthenticationService;
    }
}
