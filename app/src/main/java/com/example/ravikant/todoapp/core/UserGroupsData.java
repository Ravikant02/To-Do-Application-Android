package com.example.ravikant.todoapp.core;

/**
 * Created by ravikant on 25/2/16.
 */
import com.example.ravikant.todoapp.api.response.DashboardResponse.Group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserGroupsData implements Serializable{
    private List<Group> groups = new ArrayList<Group>();

    public List<Group> getGroups(){
        return groups;
    }

    public void setGroups(List<Group> groups){
        this.groups = groups;
    }

}
