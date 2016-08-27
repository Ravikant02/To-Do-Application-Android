package com.example.ravikant.todoapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ravikant.todoapp.R;
import com.example.ravikant.todoapp.api.response.BoardResponse.UserBoard;
import com.example.ravikant.todoapp.api.response.DashboardResponse.Board;
import com.example.ravikant.todoapp.core.UserBoardData;
import com.example.ravikant.todoapp.core.UserGroupData;
import com.example.ravikant.todoapp.core.UserGroupsData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ravikant on 25/2/16.
 */
public class GroupAdapter extends BaseExpandableListAdapter {
    private Context _context;

    private ArrayList<UserGroupData> userGroupDatas;

    public GroupAdapter(ArrayList<UserGroupData> userGroupDatas, Context context) {
        this.userGroupDatas = userGroupDatas;
        this._context = context;
        /*Toast.makeText(this._context, "in adapter" + String.valueOf(userGroupDatas.size()),
                Toast.LENGTH_LONG).show();*/
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (this.userGroupDatas == null) return null;
        List<Board> boards = userGroupDatas.get(groupPosition).getBoards();
        return boards.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        List<Board> boards = userGroupDatas.get(groupPosition).getBoards();
        return boards.get(childPosition).getBoardId();
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        //final String childText = (String) getChild(groupPosition, childPosition);

        Board board = (Board) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.custom_board_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.boardName);

        txtListChild.setText(board.getBoardName());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.userGroupDatas.get(groupPosition).getBoards().size();

        /*return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();*/
    }

    @Override
    public Object getGroup(int groupPosition) {

        return userGroupDatas.get(groupPosition);

        //return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.userGroupDatas.size();

        //return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        //String headerTitle = (String) getGroup(groupPosition);

        UserGroupData userGroupData = (UserGroupData) getGroup(groupPosition);

        ExpandableListView eLV = (ExpandableListView) parent;
        eLV.expandGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.custom_group_item, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.groupName);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(userGroupData.getGroupName());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
