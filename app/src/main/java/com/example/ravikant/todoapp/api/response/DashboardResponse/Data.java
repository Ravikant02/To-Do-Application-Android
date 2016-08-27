
package com.example.ravikant.todoapp.api.response.DashboardResponse;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("groups")
    private List<Group> groups = new ArrayList<Group>();

    /**
     * 
     * @return
     *     The groups
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * 
     * @param groups
     *     The groups
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

}
