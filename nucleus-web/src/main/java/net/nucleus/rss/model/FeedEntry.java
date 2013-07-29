package net.nucleus.rss.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * User: starasov
 * Date: 5/1/13
 * Time: 8:18 PM
 */
@Entity
@Table(name = "feed_entries")
public class FeedEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Outline feed;

    @Column(length = 1024)
    private String title;

    @Column(length = 20000)
    private String fullDescription;

    @Column(length = 1024)
    private String externalUrl;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publicationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date entryTimestamp;

    private boolean readFlag;
    private boolean starredFlag;
    private boolean openedFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public Outline getFeed() {
        return feed;
    }

    public void setFeed(Outline feed) {
        this.feed = feed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getEntryTimestamp() {
        return entryTimestamp;
    }

    public boolean isRead() {
        return readFlag;
    }

    public void setReadFlag(boolean readFlag) {
        this.readFlag = readFlag;
    }

    public boolean isStarred() {
        return starredFlag;
    }

    public void setStarredFlag(boolean starredFlag) {
        this.starredFlag = starredFlag;
    }

    public boolean isOpenedFlag() {
        return openedFlag;
    }

    public void setOpenedFlag(boolean openedFlag) {
        this.openedFlag = openedFlag;
    }

    @PrePersist
    public void updateTimeStamp() {
        entryTimestamp = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedEntry feedEntry = (FeedEntry) o;

        if (externalUrl != null ? !externalUrl.equals(feedEntry.externalUrl) : feedEntry.externalUrl != null)
            return false;
        if (title != null ? !title.equals(feedEntry.title) : feedEntry.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (externalUrl != null ? externalUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("FeedEntry");
        sb.append("{title='").append(title).append('\'');
        sb.append(", externalUrl='").append(externalUrl).append('\'');
        sb.append(", readFlag=").append(readFlag);
        sb.append(", starredFlag=").append(starredFlag);
        sb.append(", openedFlag=").append(openedFlag);
        sb.append(", entryTimestamp=").append(entryTimestamp);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
