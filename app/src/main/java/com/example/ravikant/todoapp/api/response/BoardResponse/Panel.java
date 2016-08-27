
package com.example.ravikant.todoapp.api.response.BoardResponse;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Panel {

    @SerializedName("panel_id")
    private Integer panelId;
    @SerializedName("tasks")
    private List<Task> tasks = new ArrayList<Task>();
    @SerializedName("panel_name")
    private String panelName;

    /**
     * 
     * @return
     *     The panelId
     */
    public Integer getPanelId() {
        return panelId;
    }

    /**
     * 
     * @param panelId
     *     The panel_id
     */
    public void setPanelId(Integer panelId) {
        this.panelId = panelId;
    }

    /**
     * 
     * @return
     *     The tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * 
     * @param tasks
     *     The tasks
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * 
     * @return
     *     The panelName
     */
    public String getPanelName() {
        return panelName;
    }

    /**
     * 
     * @param panelName
     *     The panel_name
     */
    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

}
