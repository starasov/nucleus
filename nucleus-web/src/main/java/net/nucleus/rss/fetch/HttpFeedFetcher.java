package net.nucleus.rss.fetch;

import com.sun.syndication.io.XmlReader;
import net.nucleus.rss.model.Outline;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

/**
 * User: starasov
 * Date: 5/25/13
 * Time: 6:01 PM
 */
@Component("http")
@Qualifier("http")
public class HttpFeedFetcher extends BaseFeedFetcher {

    @NotNull
    @Override
    protected XmlReader createXmlReader(@NotNull Outline outline) throws IOException {
        URL url = new URL(outline.getXmlUrl());
        return new XmlReader(url);
    }
}
