package com.cliqueup.entities;

import javax.persistence.*;

/**
 * Created by michaelplott on 11/23/16.
 */
@Entity
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue
    int id;

    @Column
    String friendName;

    @ManyToOne
    User user;

    public Friend() {
    }

    public Friend(String friendName, User user) {
        this.friendName = friendName;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
