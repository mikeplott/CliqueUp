package com.cliqueup.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by michaelplott on 11/16/16.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    int id;

    @Column()
    String image;

    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    boolean isOnline;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    String password;

    @ManyToOne
    Token token;

    public User() {
    }

    public User(String image, String username, String password) {
        this.image = image;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, boolean isOnline, String password, Token token) {
        this.username = username;
        this.isOnline = isOnline;
        this.password = password;
        this.token = token;
    }

    public User(String image, String username, boolean isOnline, String password, Token token) {
        this.image = image;
        this.username = username;
        this.isOnline = isOnline;
        this.password = password;
        this.token = token;
    }

    public User(String image, String username, boolean isOnline, String password) {
        this.image = image;
        this.username = username;
        this.isOnline = isOnline;
        this.password = password;
    }

    public User(String username, boolean isOnline, String password) {
        this.username = username;
        this.isOnline = isOnline;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
