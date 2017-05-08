package com.example.uday.doctalktask;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by uday on 2017-04-29.
 */

public class IssueResponse implements Serializable{
    @SerializedName("id")
    private int id;
    @SerializedName("number")
    private int number;
    @SerializedName("title")
    private String title;
    @SerializedName("user")
    private User user;
    @SerializedName("state")
    private String state;
    @SerializedName("created_at")
    private String createdDate;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
