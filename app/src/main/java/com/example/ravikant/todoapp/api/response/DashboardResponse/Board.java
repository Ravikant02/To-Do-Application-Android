
package com.example.ravikant.todoapp.api.response.DashboardResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Board {

    @SerializedName("board_id")
    private Integer boardId;
    @SerializedName("board_name")
    private String boardName;

    /**
     * 
     * @return
     *     The boardId
     */
    public Integer getBoardId() {
        return boardId;
    }

    /**
     * 
     * @param boardId
     *     The board_id
     */
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    /**
     * 
     * @return
     *     The boardName
     */
    public String getBoardName() {
        return boardName;
    }

    /**
     * 
     * @param boardName
     *     The board_name
     */
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

}
