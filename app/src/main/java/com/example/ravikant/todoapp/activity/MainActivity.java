package com.example.ravikant.todoapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ravikant.todoapp.R;
import com.example.ravikant.todoapp.adapters.GroupAdapter;
import com.example.ravikant.todoapp.api.ToDoApi;
import com.example.ravikant.todoapp.api.response.BoardResponse.UserBoard;
import com.example.ravikant.todoapp.api.response.DashboardResponse.Group;
import com.example.ravikant.todoapp.api.response.DashboardResponse.UserDashboard;
import com.example.ravikant.todoapp.api.response.NormalResponse;
import com.example.ravikant.todoapp.api.response.ProfileUploadResponse;
import com.example.ravikant.todoapp.basics.Builder;
import com.example.ravikant.todoapp.basics.MenuDrawer;
import com.example.ravikant.todoapp.basics.NetworkTester;
import com.example.ravikant.todoapp.config.AppConfig;
import com.example.ravikant.todoapp.converter.SpinnerGroupConverter;
import com.example.ravikant.todoapp.converter.UserGroupConverter;
import com.example.ravikant.todoapp.converter.UserGroupsConverter;
import com.example.ravikant.todoapp.core.SpinnerGroup;
import com.example.ravikant.todoapp.core.UserGroupData;
import com.example.ravikant.todoapp.core.UserGroupsData;
import com.example.ravikant.todoapp.fragment.NavigationDrawerFragment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import retrofit.RetrofitError;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class MainActivity extends AppCompatActivity {
    public String userID, accessToken, userName, avatarUrl;
    public View view;
    TextView textViewParent, textViewChild;
    ExpandableListView boardList;
    ArrayList<UserGroupData> userGroupDatas;
    GroupAdapter groupAdapter;

    private DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mTitle = "Home";
    private ArrayList<SpinnerGroup> spinnerGroups;
    MenuDrawerAdapter menuDrawerAdapter;
    ArrayAdapter spinnerAdapter;

    UserBoard userBoardForIntent = null;


    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#072F4B")));



        boardList = (ExpandableListView) findViewById(R.id.boardsList);
        textViewChild = (TextView) findViewById(R.id.boardName);
        textViewParent = (TextView) findViewById(R.id.groupName);

        Builder.SharedPreferenceHelper sharedPreferenceHelper = null;
        sharedPreferenceHelper = Builder.getSharedPreferenceData(getApplicationContext());
        userID = sharedPreferenceHelper.getUserID();
        accessToken = sharedPreferenceHelper.getAccessToken();
        userName = sharedPreferenceHelper.getUserName();
        avatarUrl = sharedPreferenceHelper.getAvatarUrl();

        userGroupDatas = new ArrayList<UserGroupData>();
        spinnerGroups = new ArrayList<SpinnerGroup>();

        groupAdapter = new GroupAdapter(userGroupDatas, getApplicationContext());
        boardList.setAdapter(groupAdapter);
        groupAdapter.notifyDataSetChanged();

        spinnerAdapter = new ArrayAdapter(this, R.layout.spinner, spinnerGroups);

        if (accessToken == null || userID == null) {
            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(intent);
        }
        else {
            loadData();
        }

        boardList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                Integer boardID = userGroupDatas.get(groupPosition).getBoards().get(childPosition).getBoardId();
                String boardName = userGroupDatas.get(groupPosition).getBoards().get(childPosition).getBoardName();
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                intent.putExtra("boardID", boardID);
                intent.putExtra("boardName", boardName);
                startActivity(intent);
                return false;
            }
        });


        boardList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, final int groupPosition, long id) {
                final View groupClickLayout = getLayoutInflater().inflate(R.layout.group_click_listener_layout, null);
                final TextView lblShare = (TextView) groupClickLayout.findViewById(R.id.lblShare);

                final AlertDialog.Builder groupClickDialog = new AlertDialog.Builder(MainActivity.this);
                groupClickDialog.setView(groupClickLayout);
                final AlertDialog dialog = groupClickDialog.create();
                dialog.show();

                lblShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        Toast.makeText(getApplicationContext(), userGroupDatas.get(groupPosition).getGroupCode(), Toast.LENGTH_LONG).show();
                        final String groupName = userGroupDatas.get(groupPosition).getGroupName();
                        final String groupCode = userGroupDatas.get(groupPosition).getGroupCode();
                        View view1 = getLayoutInflater().inflate(R.layout.add_email_to_share_layout, null);
                        final EditText txtEmail = (EditText) view1.findViewById(R.id.txtEmailToShare) ;
                        final EditText txtReceiverName = (EditText) view1.findViewById(R.id.txtReceiverName) ;
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(R.string.share_group)
                                .setView(view1)
                                .setPositiveButton(R.string.done,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                shareGroup(groupName, groupCode, txtReceiverName.getText().toString().trim(),
                                                        txtEmail.getText().toString().trim());
                                            }
                                        })
                                .setNegativeButton(R.string.cancel, null)
                                .show();
                    }
                });
                return false;
            }
        });


        // For menu
        getSupportActionBar().setTitle(mTitle);
        //Creating an ArrayAdapter to add items to mDrawerList
        ArrayList<MenuDrawer> menuDrawer = new ArrayList<MenuDrawer>();
        // menuDrawer.add(new MenuDrawer(R.drawable.home_icon, "Dashboard"));
        menuDrawer.add(new MenuDrawer(R.drawable.settings_icon, "Setting"));
        menuDrawer.add(new MenuDrawer(R.drawable.logout_icon, "Logout"));

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        menuDrawerAdapter = new MenuDrawerAdapter(getApplicationContext(), menuDrawer);
        mDrawerList.setAdapter(menuDrawerAdapter);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            //R.drawable.ic_drawer
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            // Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

		mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView profilePic = (ImageView) mDrawerLayout.findViewById(R.id.userImage);
        // profilePic.setImageResource(R.drawable.app_icon);
        Picasso.with(this).load(AppConfig.MEDIA_BASE_URL + avatarUrl).into(profilePic);

        TextView txtUserName = (TextView) mDrawerLayout.findViewById(R.id.userName);
        txtUserName.setText(userName);


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
                else if (position == 1)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Are you sure? You want to SignOut?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setMessage("Please wait...");
                            progressDialog.show();

                            Builder.clearSharedPreferenceData(getApplicationContext());
                            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                            progressDialog.dismiss();
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item))return true;

        if (id == R.id.addgroup) {
            View view1 = getLayoutInflater().inflate(R.layout.add_group_layout, null);
            final EditText groupName = (EditText) view1.findViewById(R.id.txtNewGroupName) ;
            new AlertDialog.Builder(this)
                    .setTitle(R.string.add_new_group_title)
                    .setView(view1)
                    .setPositiveButton(R.string.done,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    addGroup(groupName.getText().toString().trim());
                                }
                            })
                    .setNegativeButton(R.string.cancel, null)
                    .show();
        }

        if (id == R.id.joingroup) {
            View view1 = getLayoutInflater().inflate(R.layout.join_group_layout, null);
            final EditText groupCode = (EditText) view1.findViewById(R.id.txtGroupCode) ;
            new AlertDialog.Builder(this)
                    .setTitle(R.string.join_group)
                    .setView(view1)
                    .setPositiveButton(R.string.done,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    joinGroup(groupCode.getText().toString().trim());
                                }
                            })
                    .setNegativeButton(R.string.cancel, null)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        return;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    public class MenuDrawerAdapter extends ArrayAdapter<MenuDrawer>{
        public MenuDrawerAdapter(Context context, ArrayList<MenuDrawer> menuDrawers){
            super(context, 0 , menuDrawers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MenuDrawer menuDrawer = getItem(position);
            if(convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.left_drawer_item, parent, false);
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            TextView textView = (TextView) convertView.findViewById(R.id.textViewName);

            imageView.setImageResource(menuDrawer.icon);
            textView.setText(menuDrawer.name);

            return convertView;
        }
    }

    private void loadData(){
        if (NetworkTester.isNetworkAvailable(getApplicationContext())){
            ToDoApi.getINSTANCE(this).dashBoard(userID, accessToken, new Callback<UserDashboard>() {
                @Override
                public void success(UserDashboard userDashboard, Response response) {
                        /*Toast.makeText(getApplicationContext(), "Connected",
                                Toast.LENGTH_LONG).show();*/
                    if (userDashboard.getStatus().equals("success")) {
                        UserGroupsData userGroupsData = null;
                        userGroupsData = UserGroupsConverter.convert(userDashboard.getData());

                        for (Group group : userGroupsData.getGroups()) {
                            userGroupDatas.add(UserGroupConverter.convert(group));
                            spinnerGroups.add(SpinnerGroupConverter.convert(group));
                        }
                        groupAdapter.notifyDataSetChanged();
                        spinnerAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Snackbar.make(view, R.string.network_message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @OnClick(R.id.btnAddBoard)
    public void onClick(final View view){

        //Toast.makeText(getApplicationContext(), R.string.add, Toast.LENGTH_SHORT).show();
        View view1 = getLayoutInflater().inflate(R.layout.create_board_layout, null);
        final TextView textView = (TextView) view1.findViewById(R.id.txtBoardName) ;
        final Spinner groupSpinner = (Spinner) view1.findViewById(R.id.groupSpinner);
        groupSpinner.setAdapter(spinnerAdapter);
        new AlertDialog.Builder(this)
                .setTitle(R.string.create_new_board)
                .setView(view1)
                .setPositiveButton(R.string.create,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String boardName = textView.getText().toString();
                                SpinnerGroup spinnerGroup = (SpinnerGroup) groupSpinner.getSelectedItem();
                                addBoard(boardName, spinnerGroup.getGroupID());

                            }
                        })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }


    public void loadBoard(Integer boardID){
        if (NetworkTester.isNetworkAvailable(getApplicationContext())){
            ToDoApi.getINSTANCE(this).getBoard(userID, accessToken, boardID, new Callback<UserBoard>() {
                @Override
                public void success(UserBoard userBoard, Response response) {
                    userBoardForIntent = userBoard;
                    //panelSize = userBoard.getData().getPanels().size();
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
        else{
            Snackbar.make(view, R.string.network_message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void addBoard(String boardName, Integer groupID){
        ToDoApi.getINSTANCE(this).addBoard(userID, accessToken, boardName, groupID,
                new Callback<NormalResponse>() {
                    @Override
                    public void success(NormalResponse normalResponse, Response response) {
                        if (normalResponse.getResponseCode() == 200) {
                            finish();
                            startActivity(getIntent());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    private void addGroup(String groupName){
        ToDoApi.getINSTANCE(this).addGroup(userID, accessToken, groupName,
                new Callback<NormalResponse>() {
                    @Override
                    public void success(NormalResponse normalResponse, Response response) {
                        if (normalResponse.getResponseCode() == 200) {
                            finish();
                            startActivity(getIntent());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    private void shareGroup(String groupName, String groupCode, String receiverName,
                            String emailID){
        ToDoApi.getINSTANCE(this).shareGroup(userID, accessToken, groupName, groupCode, userName,
                receiverName, emailID, new Callback<NormalResponse>() {
                    @Override
                    public void success(NormalResponse normalResponse, Response response) {
                        if (normalResponse.getResponseCode() == 200) {
                            Toast.makeText(getApplicationContext(), "An Email has sent to your friend",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error)
                    {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void joinGroup(String groupCode)
    {
        ToDoApi.getINSTANCE(this).joinGroup(userID, accessToken, groupCode, new Callback<NormalResponse>() {
            @Override
            public void success(NormalResponse normalResponse, Response response) {
                if (normalResponse.getResponseCode() == 200) {
                    finish();
                    startActivity(getIntent());
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
}
