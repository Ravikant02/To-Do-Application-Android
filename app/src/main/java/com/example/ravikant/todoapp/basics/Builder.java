package com.example.ravikant.todoapp.basics;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ravikant on 23/2/16.
 */
public class Builder {

    public static void createSharedPreference(Context context, String userID, String accessToken,
                                              String emailID, String avatarUrl){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSecData",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userID", userID);
        editor.putString("accessToken", accessToken);
        editor.putString("userName", emailID);
        editor.putString("avatarUrl", avatarUrl);
        editor.apply();
        //editor.commit();
    }

    public static void clearSharedPreferenceData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSecData",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
        //editor.clear().commit();
    }

    public static SharedPreferenceHelper getSharedPreferenceData(Context context){
        SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper();
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSecData",
                Context.MODE_PRIVATE);
        sharedPreferenceHelper.setUserID(sharedPreferences.getString("userID", null));
        sharedPreferenceHelper.setAccessToken(sharedPreferences.getString("accessToken", null));
        sharedPreferenceHelper.setUserName(sharedPreferences.getString("userName", null));
        sharedPreferenceHelper.setAvatarUrl(sharedPreferences.getString("avatarUrl", null));

        return sharedPreferenceHelper;
    }

    public static class SharedPreferenceHelper{
        public String userID;
        public String accessToken;
        public String userName;
        public String avatarUrl;

        public String getUserID(){
            return this.userID;
        }

        public void setUserID(String userID){
            this.userID = userID;
        }

        public String getAccessToken(){
            return this.accessToken;
        }

        public void setAccessToken(String accessToken){
            this.accessToken = accessToken;
        }

        public String getUserName(){
            return this.userName;
        }

        public void setUserName(String userName){
            this.userName = userName;
        }

        public String getAvatarUrl(){
            return this.avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl){
            this.avatarUrl = avatarUrl;
        }
    }
}
