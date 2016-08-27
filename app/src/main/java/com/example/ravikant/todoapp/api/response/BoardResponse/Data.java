
package com.example.ravikant.todoapp.api.response.BoardResponse;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("board_id")
    private String boardId;
    @SerializedName("board_name")
    private String boardName;
    @SerializedName("panels")
    private List<Panel> panels = new ArrayList<Panel>();

    /**
     * 
     * @return
     *     The boardId
     */
    public String getBoardId() {
        return boardId;
    }

    /**
     * 
     * @param boardId
     *     The board_id
     */
    public void setBoardId(String boardId) {
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

    /**
     * 
     * @return
     *     The panels
     */
    public List<Panel> getPanels() {
        return panels;
    }

    /**
     * 
     * @param panels
     *     The panels
     */
    public void setPanels(List<Panel> panels) {
        this.panels = panels;
    }

}
