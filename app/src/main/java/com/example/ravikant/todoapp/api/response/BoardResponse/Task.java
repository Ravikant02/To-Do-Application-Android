
package com.example.ravikant.todoapp.api.response.BoardResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {

    @SerializedName("task_id")
    private Integer taskId;
    @SerializedName("task_name")
    private String taskName;

    /**
     * 
     * @return
     *     The taskId
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * 
     * @param taskId
     *     The task_id
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    /**
     * 
     * @return
     *     The taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 
     * @param taskName
     *     The task_name
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

}
