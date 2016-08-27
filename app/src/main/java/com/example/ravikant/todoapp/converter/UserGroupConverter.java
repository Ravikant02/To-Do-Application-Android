package com.example.ravikant.todoapp.converter;

import com.example.ravikant.todoapp.api.response.DashboardResponse.Group;
import com.example.ravikant.todoapp.core.UserGroupData;


/**
 * Created by ravikant on 25/2/16.
 */
public class UserGroupConverter {
    public static UserGroupData convert(Group group){
        if (group != null){
            UserGroupData userGroup = new UserGroupData();
            userGroup.setGroupID(group.getGroupId());
            userGroup.setGroupName(group.getGroupName());
            userGroup.setUserBoards(group.getBoards());
            userGroup.setGroupCode(group.getGroupCode());
            return userGroup;
        }
        return null;
    }
}
