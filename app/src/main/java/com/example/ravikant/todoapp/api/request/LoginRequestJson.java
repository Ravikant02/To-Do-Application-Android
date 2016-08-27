package com.example.ravikant.todoapp.api.request;

/**
 * Created by ravikant on 22/2/16.
 */

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
public class LoginRequestJson implements Serializable {
    @SerializedName("user_name")
    private String userName;
    private String password;

    public LoginRequestJson(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public String getUserName(){
        return this.userName;
    }
}
