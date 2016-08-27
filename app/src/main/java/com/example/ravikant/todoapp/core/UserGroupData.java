package com.example.ravikant.todoapp.core;

/**
 * Created by ravikant on 25/2/16.
 */
import com.example.ravikant.todoapp.api.response.DashboardResponse.Board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserGroupData implements Serializable {
    private Integer groupID;
    private String groupName;
    private String groupCode;
    private List<Board> boards = new ArrayList<Board>();

    public Integer getGroupID(){
        return groupID;
    }

    public void setGroupID(Integer groupID){
        this.groupID = groupID;
    }

    public String getGroupName(){
        return groupName;
    }

    public String getGroupCode(){
        return groupCode;
    }

    public void setGroupCode(String groupCode){
        this.groupCode = groupCode;
    }

    public void setGroupName(String groupName){
        this.groupName = groupName;
    }

    public List<Board> getBoards(){
        return boards;
    }

    public void setUserBoards(List<Board> boards){
        this.boards = boards;
    }
}
