package net.nucleus.rss.controller;

import java.util.List;

/**
 * User: starasov
 * Date: 5/30/13
 * Time: 8:08 AM
 */
public class OutlineResult {
    private long id;
    private List<OutlineResult> children;
    private String title;
    private int ordinal;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<OutlineResult> getChildren() {
        return children;
    }

    public void setChildren(List<OutlineResult> children) {
        this.children = children;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }
}
