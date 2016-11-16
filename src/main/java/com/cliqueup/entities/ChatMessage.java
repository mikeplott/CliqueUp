package com.cliqueup.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by michaelplott on 11/16/16.
 */
@Entity
@Table(name = "cms")
public class ChatMessage {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String message;

    @Column(nullable = false)
    Timestamp time;

    @ManyToOne
    Group group;

    @ManyToOne
    User user;

    public ChatMessage() {
    }

    public ChatMessage(String message, Timestamp time, Group group, User user) {
        this.message = message;
        this.time = time;
        this.group = group;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
