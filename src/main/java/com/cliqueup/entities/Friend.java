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

    @Column
    String friendImage;

    @Column()
    Integer friendMeetupId;

    @ManyToOne
    User user;

    public Friend() {
    }

    public Friend(String friendName, String friendImage, User user) {
        this.friendName = friendName;
        this.friendImage = friendImage;
        this.user = user;
    }

    public Friend(String friendName, String friendImage, int friendMeetupId, User user) {
        this.friendName = friendName;
        this.friendImage = friendImage;
        this.friendMeetupId = friendMeetupId;
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

    public String getFriendImage() {
        return friendImage;
    }

    public void setFriendImage(String friendImage) {
        this.friendImage = friendImage;
    }

    public int getFriendMeetupId() {
        return friendMeetupId;
    }

    public void setFriendMeetupId(int friendMeetupId) {
        this.friendMeetupId = friendMeetupId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
