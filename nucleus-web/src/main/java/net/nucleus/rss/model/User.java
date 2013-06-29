package net.nucleus.rss.model;

import javax.persistence.*;

/**
 * User: starasov
 * Date: 5/12/13
 * Time: 11:11 PM
 */
@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = "username")
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String username;
    private String googleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
}
