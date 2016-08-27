
package com.example.ravikant.todoapp.api.response.DashboardResponse;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Group {

    @SerializedName("group_id")
    private Integer groupId;
    @SerializedName("boards")
    private List<Board> boards = new ArrayList<Board>();
    @SerializedName("group_name")
    private String groupName;
    @SerializedName("group_code")
    private String groupCode;

    /**
     * 
     * @return
     *     The groupId
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 
     * @param groupId
     *     The group_id
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 
     * @return
     *     The boards
     */
    public List<Board> getBoards() {
        return boards;
    }

    /**
     * 
     * @param boards
     *     The boards
     */
    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    /**
     * 
     * @return
     *     The groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 
     * @param groupName
     *     The group_name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }


}
