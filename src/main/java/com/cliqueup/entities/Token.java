package com.cliqueup.entities;

import javax.persistence.*;

/**
 * Created by michaelplott on 11/17/16.
 */
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue
    int id;

    @Column()
    String access_token;

    public Token() {
    }

    public Token(String access_token) {
        this.access_token = access_token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

}
