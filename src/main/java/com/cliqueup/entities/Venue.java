package com.cliqueup.entities;

import javax.persistence.*;

/**
 * Created by michaelplott on 11/16/16.
 */
@Entity
@Table(name = "venues")
public class Venue {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String name;

    @Column()
    String image;

    @Column()
    String address;

    public Venue() {
    }

    public Venue(String name, String image, String address) {
        this.name = name;
        this.image = image;
        this.address = address;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
