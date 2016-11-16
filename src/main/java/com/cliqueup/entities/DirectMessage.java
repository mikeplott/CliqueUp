package com.cliqueup.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by michaelplott on 11/16/16.
 */
@Entity
@Table(name = "dms")
public class DirectMessage {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String message;

    @Column(nullable = false)
    Timestamp time;

    @Column(nullable = false)
    int recipientId;

    @ManyToOne
    User user;

    public DirectMessage() {
    }

    public DirectMessage(String message, Timestamp time, int recipientId, User user) {
        this.message = message;
        this.time = time;
        this.recipientId = recipientId;
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

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
