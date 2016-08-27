
package com.example.ravikant.todoapp.api.response.DashboardResponse;

import com.google.gson.annotations.SerializedName;

public class UserDashboard {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;
    @SerializedName("response_code")
    private Integer responseCode;

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
     *     The data
     */
    public Data getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data data) {
        this.data = data;
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

}
