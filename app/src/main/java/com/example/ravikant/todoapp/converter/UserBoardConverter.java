package com.example.ravikant.todoapp.converter;

import com.example.ravikant.todoapp.api.response.DashboardResponse.Board;
import com.example.ravikant.todoapp.core.UserBoardData;

/**
 * Created by ravikant on 25/2/16.
 */
public class UserBoardConverter {
    public static UserBoardData convert(Board board){
        if (board != null){
            UserBoardData userBoardData=  new UserBoardData();
            userBoardData.setBoardID(board.getBoardId());
            userBoardData.setBoardName(board.getBoardName());
            return userBoardData;
        }
        return null;
    }
}
