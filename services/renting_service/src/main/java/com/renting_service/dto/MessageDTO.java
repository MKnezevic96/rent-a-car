package com.renting_service.dto;

import com.renting_service.model.Message;

import java.text.SimpleDateFormat;

public class MessageDTO {


    private Integer id;
    private String content;
    private String userFromEmail;
    private String userToEmail;
    private String date;
    private boolean deleted = false;

    public MessageDTO(){}

    public MessageDTO(Integer id, String content, String userFromEmail, String userToEmail, String date, boolean deleted) {
        this.id = id;
        this.content = content;
        this.userFromEmail = userFromEmail;
        this.userToEmail = userToEmail;
        this.date = date;
        this.deleted = deleted;
    }

    public MessageDTO(Message message){
        this.content = message.getContent();
        this.id = message.getId();
        this.userFromEmail = message.getFrom().getEmail();
        this.userToEmail = message.getTo().getEmail();

        SimpleDateFormat newFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        String finalString = newFormat.format(message.getDate());

        this.date = finalString;
        this.deleted = message.isDeleted();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserFromEmail() {
        return userFromEmail;
    }

    public void setUserFromEmail(String userFromEmail) {
        this.userFromEmail = userFromEmail;
    }

    public String getUserToEmail() {
        return userToEmail;
    }

    public void setUserToEmail(String userToEmail) {
        this.userToEmail = userToEmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

