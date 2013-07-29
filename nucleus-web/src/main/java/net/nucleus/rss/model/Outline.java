package net.nucleus.rss.model;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: starasov
 * Date: 5/13/13
 * Time: 10:33 PM
 */
@Entity
@Table(name = "outlines")
public class Outline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Outline parent;

    @ManyToOne(optional = false)
    private User user;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Outline> children = new ArrayList<Outline>();

    @Enumerated(EnumType.STRING)
    @Column(name = "outline_type")
    private OutlineType type;

    private int ordinal;

    private String title;
    private String description;
    private String xmlUrl;
    private String htmlUrl;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateTime;

    private int failedUpdates;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Outline getParent() {
        return parent;
    }

    public void setParent(Outline parent) {
        this.parent = parent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isEmpty() {
        return children.isEmpty();
    }

    public List<Outline> getChildren() {
        return children;
    }

    public void setChildren(List<Outline> children) {
        this.children = children;
    }

    public void addChild(@NotNull Outline outline) {
        this.children.add(outline);
    }

    public OutlineType getType() {
        return type;
    }

    public void setType(OutlineType type) {
        this.type = type;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getXmlUrl() {
        return xmlUrl;
    }

    public void setXmlUrl(String xmlUrl) {
        this.xmlUrl = xmlUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    @PrePersist
    public void updateLastUpdateTime() {
        this.lastUpdateTime = new Date(0);
    }

    public int getFailedUpdates() {
        return failedUpdates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Outline outline = (Outline) o;

        if (id != outline.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
