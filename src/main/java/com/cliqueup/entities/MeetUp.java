package com.cliqueup.entities;

import javax.persistence.*;

/**
 * Created by michaelplott on 11/16/16.
 */
@Entity
@Table(name = "meetups")
public class MeetUp {
    public enum Category {

    }
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    Category category;

    @Column(nullable = false)
    int likes;

    @Column(nullable = false)
    int dislikes;

    @Column(nullable = false)
    String description;

    @ManyToOne
    User adminUser;

    @ManyToOne
    Group group;

    public MeetUp() {
    }

    public MeetUp(String title, Category category, int likes, int dislikes, String description, User adminUser, Group group) {
        this.title = title;
        this.category = category;
        this.likes = likes;
        this.dislikes = dislikes;
        this.description = description;
        this.adminUser = adminUser;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
