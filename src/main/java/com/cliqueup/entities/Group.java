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
}
