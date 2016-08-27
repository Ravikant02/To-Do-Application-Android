package com.example.ravikant.todoapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ravikant.todoapp.R;
import com.example.ravikant.todoapp.api.ToDoApi;
import com.example.ravikant.todoapp.api.response.BoardResponse.Panel;
import com.example.ravikant.todoapp.api.response.BoardResponse.Task;
import com.example.ravikant.todoapp.api.response.BoardResponse.UserBoard;
import com.example.ravikant.todoapp.api.response.DashboardResponse.Board;
import com.example.ravikant.todoapp.api.response.NormalResponse;
import com.example.ravikant.todoapp.api.response.PostResponse;
import com.example.ravikant.todoapp.basics.Builder;
import com.example.ravikant.todoapp.basics.NetworkTester;
import com.example.ravikant.todoapp.config.AppConfig;
import com.example.ravikant.todoapp.core.SpinnerGroup;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BoardActivity extends AppCompatActivity {
    private String userID, accessToken;
    private Integer boardID;
    private String boardName;
    Integer panelSize = 0;
    View view;
    UserBoard userBoard = null;
    String reminderDateTime, emailDate, alarmDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        Builder.SharedPreferenceHelper sharedPreferenceHelper = null;
        sharedPreferenceHelper = Builder.getSharedPreferenceData(getApplicationContext());
        userID = sharedPreferenceHelper.getUserID();
        accessToken = sharedPreferenceHelper.getAccessToken();


        Intent intent = getIntent();
        boardID = intent.getIntExtra("boardID", 0);
        boardName = intent.getStringExtra("boardName");
        getSupportActionBar().setTitle(boardName);
        loadData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.share) {
            return true;
        }
        if (id == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure? It can't be undone.");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    deleteBoard();

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
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_board, menu);
        return true;
    }

    public void loadData(){
        if (NetworkTester.isNetworkAvailable(getApplicationContext())){
            ToDoApi.getINSTANCE(this).getBoard(userID, accessToken, boardID, new Callback<UserBoard>() {
                @Override
                public void success(final UserBoard userBoard, Response response) {
                    /*Toast.makeText(getApplicationContext(), "after" +
                            String.valueOf(userBoard.getData().getPanels().size()),
                            Toast.LENGTH_LONG ).show();*/
                    panelSize = userBoard.getData().getPanels().size();
                    //for (int i = 0; i <= panelSize; i++)
                    //for (Panel panel : userBoard.getData().getPanels())
                    if (userBoard.getResponseCode().equals(AppConfig.SUCCESS_CODE)) {

                        if (panelSize > 0) {
                            View linearLayout = findViewById(R.id.BoardActivityLayout);
                            for (final Panel panel : userBoard.getData().getPanels()){
                                View view1 = getLayoutInflater().inflate(R.layout.panel_view, null);
                                TextView textView = (TextView) view1.findViewById(R.id.textTitle);
                                final Button btnAddTask = (Button) view1.findViewById(R.id.btnAddTask);
                                textView.setText(panel.getPanelName());

                                ArrayList<Task> tasks = new ArrayList<Task>();
                                final ListView listView = (ListView) view1.findViewById(R.id.taskDetailsListView);
                                TaskAdapter taskAdapter = new TaskAdapter(getApplicationContext(), tasks);
                                listView.setAdapter(taskAdapter);

                                for (Task task : panel.getTasks())
                                {
                                    tasks.add(task);
                                }
                                taskAdapter.notifyDataSetChanged();


                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        final Task task = (Task) listView.getItemAtPosition(position);
                                        final View taskClickLayout = getLayoutInflater().inflate(R.layout.task_click_listener_layout, null);
                                        final TextView lblMove = (TextView) taskClickLayout.findViewById(R.id.lblMove);
                                        final TextView lblAlarmReminder = (TextView) taskClickLayout.findViewById(R.id.lblAlarmReminder);
                                        final TextView lblEmailReminder = (TextView) taskClickLayout.findViewById(R.id.lblEmailReminder);
                                        final TextView lblDelete = (TextView) taskClickLayout.findViewById(R.id.lblDelete);

                                        if (userBoard.getData().getPanels().size() == 1) {
                                            lblMove.setVisibility(View.GONE);
                                        }
                                        final AlertDialog.Builder taskClickDialog = new AlertDialog.Builder(BoardActivity.this);
                                        taskClickDialog.setView(taskClickLayout);
                                        final AlertDialog dialog = taskClickDialog.create();
                                        dialog.show();

                                        lblMove.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //Toast.makeText(getApplicationContext(), task.getTaskName(), Toast.LENGTH_LONG).show();
                                                View paneNamesLayout = getLayoutInflater().inflate(R.layout.task_move_panel_layout, null, false);
                                                LinearLayout panelLinearLayout = (LinearLayout) paneNamesLayout.findViewById(R.id.panelsLayout);

                                                AlertDialog.Builder builder = new AlertDialog.Builder(BoardActivity.this);
                                                builder.setView(paneNamesLayout);
                                                final AlertDialog childDialog = builder.create();

                                                for (final Panel panelInside : userBoard.getData().getPanels()){
                                                    if (panelInside.getPanelId().equals(panel.getPanelId())) continue;
                                                    View taskMoveLayout = getLayoutInflater().inflate(R.layout.panel_names_layout, null, false);
                                                    final TextView panelName = (TextView) taskMoveLayout.findViewById(R.id.lblPanelName);
                                                    panelName.setText(panelInside.getPanelName());
                                                    panelName.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            //Toast.makeText(getApplicationContext(), String.valueOf(panelInside.getPanelId()), Toast.LENGTH_LONG).show();
                                                            childDialog.dismiss();
                                                            moveTask(panelInside.getPanelId(), task.getTaskId(), String.valueOf(R.string.task_delete_message));
                                                        }
                                                    });
                                                    panelLinearLayout.addView(taskMoveLayout);
                                                }
                                                dialog.dismiss();
                                                childDialog.show();
                                                /*new AlertDialog.Builder(BoardActivity.this)
                                                        .setView(paneNamesLayout)
                                                        .show();*/
                                            }
                                        });

                                        lblEmailReminder.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                /*final Calendar calendar = Calendar.getInstance();
                                                calendar.add(Calendar.DATE, -1);
                                                final DatePickerDialog datePicker = new DatePickerDialog(BoardActivity.this, new DatePickerDialog.OnDateSetListener() {
                                                    @Override
                                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                        *//*strDate.setText(String.valueOf(dayOfMonth) + " " + getMonthName(monthOfYear)
                                                                + " " + year);*//*
                                                    }
                                                }, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
                                                datePicker.setTitle(R.string.email_date_picker_title);
                                                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis() - 1000);
                                                datePicker.show();*/
                                                dialog.dismiss();
                                                reminderDateTimeDialogBuilder(R.string.email_reminder_title);
                                                emailReminder(task.getTaskName());

                                            }
                                        });

                                        lblAlarmReminder.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                                reminderDateTimeDialogBuilder(R.string.alarm_reminder_title);
                                            }
                                        });

                                        lblDelete.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                                new AlertDialog.Builder(BoardActivity.this)
                                                        .setTitle(R.string.task_delete_confirmation)
                                                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                deleteTask(panel.getPanelId(),
                                                                        task.getTaskId(), String.valueOf(R.string.task_delete_message));
                                                            }
                                                        })
                                                        .setNegativeButton(R.string.cancel, null)
                                                        .show();

                                            }
                                        });
                                    }
                                });


                                btnAddTask.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        View addTaskLayout = getLayoutInflater().inflate(R.layout.add_task_layout, null);
                                        final EditText taskName = (EditText) addTaskLayout.findViewById(R.id.txtNewTaskName);

                                        new AlertDialog.Builder(BoardActivity.this)
                                                .setTitle(R.string.add_new_task_title)
                                                .setView(addTaskLayout)
                                                .setPositiveButton(R.string.done,
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                                addTask(panel.getPanelId(),
                                                                        taskName.getText().toString().trim());
                                                            }
                                                        })
                                                .setNegativeButton(R.string.cancel, null)
                                                .show();

                                    }
                                });
                                ((LinearLayout) linearLayout).addView(view1);
                            }
                        }
                    }
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

    @OnClick(R.id.btnAddPanel)
    public void onClick(){
        View view1 = getLayoutInflater().inflate(R.layout.add_panel_layout, null);
        final EditText panelName = (EditText) view1.findViewById(R.id.txtNewPanelName) ;
        new AlertDialog.Builder(this)
                .setTitle(R.string.add_new_panel_title)
                .setView(view1)
                .setPositiveButton(R.string.done,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                addPanel(panelName.getText().toString().trim());
                            }
                        })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    public class TaskAdapter extends ArrayAdapter<Task>{
        public TaskAdapter(Context context, ArrayList<Task> tasks){
            super(context, 0, tasks);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Task task = getItem(position);
            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.task_element, null);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.lblTask);
            textView.setText(task.getTaskName());
            return convertView;
            //return super.getView(position, convertView, parent);
        }
    }

    private void addTask(Integer panelID, String taskName){
        ToDoApi.getINSTANCE(this).addNewTask(userID, boardID, panelID, taskName, accessToken, new Callback<PostResponse>() {
            @Override
            public void success(PostResponse postResponse, Response response) {
                if (postResponse.getResponseCode() == 200){
                    finish();
                    startActivity(getIntent());
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void addPanel(String panelName){
        ToDoApi.getINSTANCE(this).addNewPanel(userID, boardID, panelName, accessToken,
                new Callback<PostResponse>() {
                    @Override
                    public void success(PostResponse postResponse, Response response) {
                        if (postResponse.getResponseCode() == 200) {
                            finish();
                            startActivity(getIntent());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    private void deleteTask(int panelID, int taskID, final String message)
    {
        ToDoApi.getINSTANCE(BoardActivity.this).deleteTask(userID, accessToken, boardID, panelID, taskID,
                new Callback<NormalResponse>() {
                    @Override
                    public void success(NormalResponse normalResponse, Response response) {
                        if (normalResponse.getResponseCode().equals(AppConfig.SUCCESS_CODE)) {
                            Toast.makeText(BoardActivity.this, message,
                                    Toast.LENGTH_SHORT).show();
                            startActivity(getIntent());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    private void moveTask(int panelID, int taskID, final String message)
    {
        ToDoApi.getINSTANCE(BoardActivity.this).moveTask(userID, accessToken, boardID, panelID, taskID,
                new Callback<NormalResponse>() {
                    @Override
                    public void success(NormalResponse normalResponse, Response response) {
                        if (normalResponse.getResponseCode().equals(AppConfig.SUCCESS_CODE)) {
                            Toast.makeText(BoardActivity.this, message,
                                    Toast.LENGTH_SHORT).show();
                            startActivity(getIntent());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    private void deleteBoard(){
        final ProgressDialog progressDialog = new ProgressDialog(BoardActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        ToDoApi.getINSTANCE(this).deleteBoard(userID, accessToken, boardID,
                new Callback<NormalResponse>() {
                    @Override
                    public void success(NormalResponse normalResponse, Response response) {
                        if (normalResponse.getResponseCode() == 200) {
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(BoardActivity.this, MainActivity.class));
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    public void reminderDateTimeDialogBuilder(int dialogTitle)
    {
        Calendar currentDate = Calendar.getInstance();
        int mYear = currentDate.get(Calendar.YEAR);
        int mMonth = currentDate.get(Calendar.MONTH);
        int mDay = currentDate.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog datePicker = new DatePickerDialog(BoardActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, final int dayOfMonth) {
                final int eventYear = year;
                final int eventMonth = monthOfYear + 1;
                final int eventDay = dayOfMonth;

                final TimePickerDialog timePicker = new TimePickerDialog(BoardActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String format;
                        String month = String.valueOf(eventMonth);
                        String min = String.valueOf(minute);
                        String hour = String.valueOf(hourOfDay);
                        String day = String.valueOf(eventDay);

                        if (hourOfDay == 0) {
                            hourOfDay += 12;
                            format = "AM";
                        } else if (hourOfDay == 12) {
                            format = "PM";
                        } else if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            format = "PM";
                        } else {
                            format = "AM";
                        }

                        if (hourOfDay < 10) hour = String.valueOf("0" + hourOfDay);
                        if (minute < 10) min = String.valueOf("0" + minute);
                        if(eventMonth < 10) month = String.valueOf("0" + eventMonth);
                        if (eventDay < 10) day = String.valueOf("0" + eventDay);

                        reminderDateTime = String.valueOf(eventYear + "/" + month + "/"
                                + day + " " + hour + ":" + min + ":00");


                        Toast.makeText(getApplicationContext(), reminderDateTime, Toast.LENGTH_SHORT).show();
                    }
                }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, false);
                timePicker.setTitle("Select Reminder Time");
                timePicker.show();
            }

        }, mYear, mMonth, mDay);
        datePicker.setTitle(dialogTitle);
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1);
        datePicker.show();
    }

    private void emailReminder(String taskName){
        ToDoApi.getINSTANCE(this).emailReminder(userID, accessToken, boardName, taskName,
                reminderDateTime, new Callback<NormalResponse>() {
                    @Override
                    public void success(NormalResponse normalResponse, Response response) {
                        if(normalResponse.getResponseCode() == 200){
                            //
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

}
