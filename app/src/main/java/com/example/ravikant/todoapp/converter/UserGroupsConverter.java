package com.example.ravikant.todoapp.converter;

import com.example.ravikant.todoapp.api.response.DashboardResponse.Data;
import com.example.ravikant.todoapp.api.response.DashboardResponse.Group;
import com.example.ravikant.todoapp.core.UserGroupsData;

import java.util.List;

/**
 * Created by ravikant on 25/2/16.
 */
public class UserGroupsConverter {
    public static UserGroupsData convert(Data data){
        if (data != null){
            UserGroupsData userGroupsData = new UserGroupsData();
            userGroupsData.setGroups(data.getGroups());
            return userGroupsData;
        }
        return null;
    }
}
