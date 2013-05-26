package net.nucleus.rss.fetch;

import com.sun.syndication.io.XmlReader;
import net.nucleus.rss.model.Outline;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * User: starasov
 * Date: 5/25/13
 * Time: 5:26 PM
 */
@Component("local")
@Qualifier("local")
public class ClasspathResourceFeedFetcher extends BaseFeedFetcher {

    @NotNull
    @Override
    protected XmlReader createXmlReader(@NotNull Outline outline) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/javaposse.xml");
        return new XmlReader(resource.getInputStream());
    }
}
