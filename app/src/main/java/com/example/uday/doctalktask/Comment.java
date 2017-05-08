package com.example.uday.doctalktask;

import java.io.Serializable;

/**
 * Created by meruguabhishek on 2017-05-08.
 */

public class Comment implements Serializable {
    private int id;
    private User user;
    private String body;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
