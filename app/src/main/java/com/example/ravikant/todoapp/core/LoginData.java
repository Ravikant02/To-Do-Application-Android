package com.example.ravikant.todoapp.core;

/**
 * Created by ravikant on 22/2/16.
 */
import java.io.Serializable;

public class LoginData implements Serializable{
    private String userID;
    private String emailID;
    private String contact;
    private String accessToken;
    private String avatarUrl;

    public String getUserID(){
        return this.userID;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    public String getEmailID(){
        return this.emailID;
    }

    public void setEmailID(String emailID){
        this.emailID = emailID;
    }

    public String getContact(){
        return this.contact;
    }

    public void setContact(String contact){
        this.contact = contact;
    }

    public String getAccessToken(){
        return this.accessToken;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String getAvatarUrl(){
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl){
        this.avatarUrl = avatarUrl;
    }
}
