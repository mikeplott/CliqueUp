package com.cliqueup.entities;

import javax.persistence.*;

/**
 * Created by michaelplott on 11/16/16.
 */
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String description;

    @ManyToOne
    User adminUser;

    public Group() {
    }

    public Group(String name, String description, User adminUser) {
        this.name = name;
        this.description = description;
        this.adminUser = adminUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(User adminUser) {
        this.adminUser = adminUser;
    }
}
