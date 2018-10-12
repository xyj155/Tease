package com.example.home.gson;

/**
 * Created by Xuyijie on 2018/10/12.
 */

public class HomeMessage {
    private String avatar;
    private String username;
    private String message;

    public HomeMessage() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HomeMessage(String avatar, String username, String message) {
        this.avatar = avatar;
        this.username = username;
        this.message = message;
    }
}
