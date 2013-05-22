package net.nucleus.rss.model;

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

    @Column(length = 8192)
    private String fullDescription;

    @Column(length = 8192)
    private String shortDescription;

    @Column(length = 1024)
    private String externalUrl;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publicationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date entryTimestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
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

    @PrePersist
    public void updateTimeStamps() {
        entryTimestamp = new Date();
    }
}
