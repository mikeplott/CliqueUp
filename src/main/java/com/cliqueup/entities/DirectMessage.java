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
    String recipientName;

    @ManyToOne
    User user;

    public DirectMessage() {
    }

    public DirectMessage(String message, String recipientName, User user) {
        this.message = message;
        this.recipientName = recipientName;
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

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
