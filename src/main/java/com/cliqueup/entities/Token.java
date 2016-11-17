package com.cliqueup.entities;

import javax.persistence.*;

/**
 * Created by michaelplott on 11/17/16.
 */
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String key;

    @Column(nullable = false)
    String secret;

    @Column(nullable = false)
    String redirectURL;

    @ManyToOne
    User user;

    public Token() {
    }

    public Token(String key, String secret, String redirectURL, User user) {
        this.key = key;
        this.secret = secret;
        this.redirectURL = redirectURL;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
