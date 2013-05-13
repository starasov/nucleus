package net.nucleus.rss.model;

import java.net.URL;
import java.util.List;

/**
 * User: starasov
 * Date: 5/12/13
 * Time: 12:21 AM
 */
public class Feed {
    private URL url;
    private String title;
    private List<Category> categories;

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Feed");
        sb.append("{url=").append(url);
        sb.append('}');
        return sb.toString();
    }
}
