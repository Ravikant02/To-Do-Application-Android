
package com.example.ravikant.todoapp.api.response.LoginResponse;

import com.google.gson.annotations.SerializedName;


public class LoginResponseJson {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("response_code")
    private Integer responseCode;
    @SerializedName("data")
    private UserData data;

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * 
     * @return
     *     The responseCode
     */
    public Integer getResponseCode() {
        return responseCode;
    }

    /**
     * 
     * @param responseCode
     *     The response_code
     */
    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * 
     * @return
     *     The data
     */
    public UserData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(UserData data) {
        this.data = data;
    }

}
