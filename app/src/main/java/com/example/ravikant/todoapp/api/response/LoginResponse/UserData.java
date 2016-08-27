
package com.example.ravikant.todoapp.api.response.LoginResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("user_id")
    private String userId;
    @SerializedName("emailid")
    private String emailid;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("contact")
    private String contact;
    @SerializedName("access_token")
    private String accessToken;

    /**
     * 
     * @return
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The emailid
     */
    public String getEmailid() {
        return emailid;
    }

    /**
     * 
     * @param emailid
     *     The emailid
     */
    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    /**
     * 
     * @return
     *     The avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 
     * @param avatarUrl
     *     The avatar_url
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * 
     * @return
     *     The contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * 
     * @param contact
     *     The contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * 
     * @return
     *     The accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * 
     * @param accessToken
     *     The access_token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
