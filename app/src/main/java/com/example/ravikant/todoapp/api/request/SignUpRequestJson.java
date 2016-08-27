package com.example.ravikant.todoapp.api.request;

import java.io.Serializable;

/**
 * Created by ravikant on 6/3/16.
 */
public class SignUpRequestJson implements Serializable {
    private String userName, emailID, password;

    public SignUpRequestJson(String userName, String emailID, String password){
        this.userName = userName;
        this.emailID = emailID;
        this.password = password;
    }

    public String getUserName(){
        return this.userName;
    }

    public String getEmailID(){
        return this.emailID;
    }

    public String getPassword(){
        return this.password;
    }
}
