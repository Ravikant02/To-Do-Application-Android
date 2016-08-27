package com.example.ravikant.todoapp.converter;

import com.example.ravikant.todoapp.api.response.LoginResponse.UserData;
import com.example.ravikant.todoapp.core.LoginData;

/**
 * Created by ravikant on 22/2/16.
 */

public class UserConverter {
    public static LoginData convert(UserData userData){
        if (userData == null) return null;
        LoginData loginData = new LoginData();
        loginData.setUserID(userData.getUserId());
        loginData.setEmailID(userData.getEmailid());
        loginData.setContact(userData.getContact());
        loginData.setAccessToken(userData.getAccessToken());
        loginData.setAvatarUrl(userData.getAvatarUrl());
        return loginData;
    }

}
