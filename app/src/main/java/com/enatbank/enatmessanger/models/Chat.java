package com.enatbank.enatmessanger.models;

import java.io.Serializable;

/**
 * Created by btinsae on 15/02/2017.
 */

public class Chat implements Serializable {
    private int userAvatar;
    private String username;
    private String chatMessage;
    private String chatDate;

    public Chat(int userAvatar, String username, String chatMessage, String chatDate) {
        this.userAvatar = userAvatar;
        this.username = username;
        this.chatMessage = chatMessage;
        this.chatDate = chatDate;
    }

    public int getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(int userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getChatDate() {
        return chatDate;
    }

    public void setChatDate(String chatDate) {
        this.chatDate = chatDate;
    }
}
