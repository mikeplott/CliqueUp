package com.cliqueup.entities;

import javax.persistence.*;

/**
 * Created by michaelplott on 11/18/16.
 */
@Entity
@Table(name = "responses")
public class Response {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String access_token;

    @Column(nullable = false)
    String refresh_token;

    @Column(nullable = false)
    String token_type;

    @Column(nullable = false)
    int expires_in;

    public Response() {
    }

    public Response(String access_token, String refresh_token, String token_type, int expires_in) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
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

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
