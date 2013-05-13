package net.nucleus.rss;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.net.URL;

/**
 * User: starasov
 * Date: 5/12/13
 * Time: 1:03 PM
 */
public class Main {
    public static void main(String[] args) throws IOException, FeedException {
        URL url = new URL("https://news.ycombinator.com/rss");
        XmlReader reader = null;

        try {

            reader = new XmlReader(url);
            SyndFeed feed = new SyndFeedInput().build(reader);
            System.out.println("Feed Title: " + feed.getAuthor());

            for (Object o : feed.getEntries()) {
                SyndEntry entry = (SyndEntry) o;
                System.out.println(entry.getTitle());
            }
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
