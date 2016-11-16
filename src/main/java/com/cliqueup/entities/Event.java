package com.cliqueup.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by michaelplott on 11/16/16.
 */
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    Timestamp time;

    @Column(nullable = false)
    String address;

    @ManyToOne
    Venue venue;

    @ManyToOne
    User adminUser;

    @ManyToOne
    MeetUp meetUp;

    public Event() {
    }

    public Event(String title, Timestamp time, String address, Venue venue, User adminUser, MeetUp meetUp) {
        this.title = title;
        this.time = time;
        this.address = address;
        this.venue = venue;
        this.adminUser = adminUser;
        this.meetUp = meetUp;
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public User getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(User adminUser) {
        this.adminUser = adminUser;
    }

    public MeetUp getMeetUp() {
        return meetUp;
    }

    public void setMeetUp(MeetUp meetUp) {
        this.meetUp = meetUp;
    }
}
