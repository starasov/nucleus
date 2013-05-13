package net.nucleus.rss.model;

import java.util.Date;

/**
 * User: starasov
 * Date: 5/1/13
 * Time: 8:18 PM
 */
public class FeedEntry {
    private String title;
    private String shortDescription;
    private String fullDescription;
    private String externalUrl;

    private Date publicationDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("FeedEntry");
        sb.append("{title='").append(title).append('\'');
        sb.append(", shortDescription='").append(shortDescription).append('\'');
        sb.append(", fullDescription='").append(fullDescription).append('\'');
        sb.append(", externalUrl='").append(externalUrl).append('\'');
        sb.append(", publicationDate=").append(publicationDate);
        sb.append('}');
        return sb.toString();
    }
}
