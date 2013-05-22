package net.nucleus.rss.sanitize;

import org.jetbrains.annotations.NotNull;
import org.owasp.html.Handler;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.HtmlStreamRenderer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.owasp.html.HtmlSanitizer.Policy;

/**
 * User: starasov
 * Date: 5/21/13
 * Time: 8:03 PM
 */
@Component
public class HtmlSanitizer {

    private HtmlPolicyBuilder policyBuilder;
    private HtmlPolicyBuilder strictPolicyBuilder;

    @NotNull
    public String sanitize(@NotNull String input) {
        return sanitizeWithBuilder(policyBuilder, input);
    }

    @NotNull
    public String sanitizeStrict(@NotNull String input) {
        return sanitizeWithBuilder(strictPolicyBuilder, input);
    }

    @NotNull
    private String sanitizeWithBuilder(@NotNull HtmlPolicyBuilder policyBuilder, @NotNull String input) {
        StringBuilder sb = new StringBuilder();

        Policy policy = policyBuilder.build(HtmlStreamRenderer.create(sb, NULL_HANDLER));
        org.owasp.html.HtmlSanitizer.sanitize(input, policy);

        return sb.toString();
    }

    @PostConstruct
    void postConstruct() {
        policyBuilder = new HtmlPolicyBuilder()
                .allowCommonInlineFormattingElements()
                .allowCommonBlockElements()
                .allowStyling()
                .allowElements("img")
                .allowAttributes("src", "alt", "title").onElements("img")
                .allowStandardUrlProtocols();

        strictPolicyBuilder = new HtmlPolicyBuilder();
    }

    private static final Handler<String> NULL_HANDLER = new Handler<String>() {
        public void handle(String x) {
        }
    };
}
