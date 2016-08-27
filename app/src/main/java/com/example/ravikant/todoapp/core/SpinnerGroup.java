package com.example.ravikant.todoapp.core;

/**
 * Created by ravikant on 29/2/16.
 */
public class SpinnerGroup {
    public Integer groupID;
    public String groupName;

    @Override
    public String toString(){
        return groupName;
    }

    public Integer getGroupID(){
        return groupID;
    }

    public void setGroupID(Integer groupID){
        this.groupID = groupID;
    }

    public String getGroupName(){
        return groupName;
    }

    public void setGroupName(String groupName){
        this.groupName = groupName;
    }
}
