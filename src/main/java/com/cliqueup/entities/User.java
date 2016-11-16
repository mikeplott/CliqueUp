package com.cliqueup.entities;

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
    String password;

    public User() {
    }

    public User(String image, String username, String password) {
        this.image = image;
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
