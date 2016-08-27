package com.example.ravikant.todoapp.core;

import java.io.Serializable;

/**
 * Created by ravikant on 25/2/16.
 */
public class UserBoardData implements Serializable {
    private Integer boardID;
    private String boardName;

    public Integer getBoardID(){
        return this.boardID;
    }

    public void setBoardID(Integer boardID){
        this.boardID = boardID;
    }

    public String getBoardName(){
        return this.boardName;
    }

    public void setBoardName(String boardName){
        this.boardName = boardName;
    }
}
