package com.example.ravikant.todoapp.core;

/**
 * Created by ravikant on 25/2/16.
 */


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserBoardsData implements Serializable {
    private List<UserBoardData> boards = new ArrayList<UserBoardData>();

    public List<UserBoardData> getBoards(){
        return this.boards;
    }

    public void setBoards(List<UserBoardData> boards){
        this.boards = boards;
    }
}

