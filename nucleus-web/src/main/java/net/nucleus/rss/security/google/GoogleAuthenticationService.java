package net.nucleus.rss.security.google;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Set;

/**
 * TODO: clarify exceptions handling and propagation
 * <p/>
 * User: starasov
 * Date: 6/14/13
 * Time: 11:03 PM
 */
@Component
public class GoogleAuthenticationService {

    private final Set<String> SCOPES = ImmutableSet.of(
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/userinfo.email"
    );

    private GoogleClientConfig clientConfig;
    private RestTemplate restTemplate;

    @NotNull
    public String buildAuthorizationUrl() {
        return UriComponentsBuilder.fromHttpUrl("https://accounts.google.com/o/oauth2/auth")
                .queryParam("client_id", clientConfig.getClientId())
                .queryParam("redirect_uri", clientConfig.getRedirectUri())
                .queryParam("approval_prompt", "force")
                .queryParam("response_type", "code")
                .queryParam("scope", Joiner.on("+").join(SCOPES))
                .build().toUriString();
    }

    @NotNull
    public GoogleUserProfile authenticate(@NotNull String authorizationCode) {
        String accessToken = refreshAccessToken(authorizationCode);
        return requestUserProfile(accessToken);
    }

    @Autowired
    public void setClientConfig(GoogleClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String refreshAccessToken(String authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("code", authorizationCode);
        parameters.add("client_id", clientConfig.getClientId());
        parameters.add("client_secret", clientConfig.getClientSecret());
        parameters.add("redirect_uri", clientConfig.getRedirectUri());
        parameters.add("grant_type", "authorization_code");

        Map tokenResponse = restTemplate.postForObject("https://accounts.google.com/o/oauth2/token", parameters, Map.class);
        return (String) tokenResponse.get("access_token");
    }

    @NotNull
    @SuppressWarnings("unchecked")
    private GoogleUserProfile requestUserProfile(String accessToken) {
        Map<String, Object> userInfoResponse = (Map<String, Object>) restTemplate.getForObject("https://www.googleapis.com/oauth2/v1/userinfo?access_token={access_token}", Map.class, accessToken);

        GoogleUserProfile googleUserProfile = new GoogleUserProfile();
        googleUserProfile.setId((String) userInfoResponse.get("id"));
        googleUserProfile.setName((String) userInfoResponse.get("name"));
        googleUserProfile.setEmail((String) userInfoResponse.get("email"));

        return googleUserProfile;
    }
}
