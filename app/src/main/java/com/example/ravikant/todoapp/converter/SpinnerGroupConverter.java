package com.example.ravikant.todoapp.converter;

import com.example.ravikant.todoapp.api.response.DashboardResponse.Group;
import com.example.ravikant.todoapp.core.SpinnerGroup;

/**
 * Created by ravikant on 29/2/16.
 */
public class SpinnerGroupConverter {
    public static SpinnerGroup convert(Group group){
        if (group == null) return null;
        SpinnerGroup spinnerGroup = new SpinnerGroup();
        spinnerGroup.setGroupName(group.getGroupName());
        spinnerGroup.setGroupID(group.getGroupId());
        return spinnerGroup;
    }
}
