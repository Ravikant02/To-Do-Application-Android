package com.example.ravikant.todoapp.api;

import android.content.Context;
import android.telecom.Call;

import com.example.ravikant.todoapp.api.request.LoginRequestJson;
import com.example.ravikant.todoapp.api.request.SignUpRequestJson;
import com.example.ravikant.todoapp.api.response.BoardResponse.UserBoard;
import com.example.ravikant.todoapp.api.response.LoginResponse.LoginResponseJson;
import com.example.ravikant.todoapp.api.response.DashboardResponse.UserDashboard;
import com.example.ravikant.todoapp.api.response.NormalResponse;
import com.example.ravikant.todoapp.api.response.PostResponse;
import com.example.ravikant.todoapp.api.response.ProfileUploadResponse;
import com.example.ravikant.todoapp.config.AppConfig;
import com.google.gson.Gson;

import com.squareup.okhttp.OkHttpClient;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedFile;

/**
 * Created by ravikant on 22/2/16.
 */
public class ToDoApi {

    private static ToDoApi INSTANCE = new ToDoApi();
    private static Context context;

    public static ToDoApi getINSTANCE(Context context){
        ToDoApi.context = context;
        return INSTANCE;
    }

    private static ToDoServices toDoServices;

    public void getLogin(String userName, String password, Callback<LoginResponseJson> callback){
        LoginRequestJson loginRequestJson = new LoginRequestJson(userName, password);
        getToDoServices(false, null).login(loginRequestJson, callback);
    }

    public void dashBoard(String userID, String accessToken, Callback<UserDashboard>callback){
        getToDoServices(true, accessToken).dashboard(userID, callback);
    }

    public void getBoard(String userID, String accessToken, Integer boardID, Callback<UserBoard> userBoardCallback) {
        getToDoServices(true, accessToken).board(userID, boardID, userBoardCallback);
    }

    public void addBoard(String userID, String accessToken, String boardName, Integer groupID,
                         Callback<NormalResponse> callback)
    {
        getToDoServices(true, accessToken).addBoard(userID, boardName, groupID, callback);
    }

    public void deleteBoard(String userID, String accessToken, Integer boardID,
                         Callback<NormalResponse> callback)
    {
        getToDoServices(true, accessToken).deleteBoard(userID, boardID, callback);
    }

    public void addGroup(String userID, String accessToken, String groupName,
                            Callback<NormalResponse> callback)
    {
        getToDoServices(true, accessToken).addGroup(userID, groupName, callback);
    }

    public void shareGroup(String userID, String accessToken, String groupName,
                           String groupCode, String senderName, String receiverName,
                           String emailID, Callback<NormalResponse>callback)
    {
        getToDoServices(true, accessToken).shareGroup(userID, senderName, receiverName, groupCode,
                groupName, emailID, callback);
    }

    public void uploadImage(String userID, String accessToken, TypedFile typedFile,
                            Callback<ProfileUploadResponse>callback){
        getToDoServices(true, accessToken).uploadProfile(userID, typedFile, callback);
    }

    public void joinGroup(String userID, String accessToken,
                          String groupCode, Callback<NormalResponse>callback){
        getToDoServices(true, accessToken).joinGroup(userID, groupCode, callback);
    }

    public void emailReminder(String userID, String accessToken, String boardName,
                              String taskName, String reminderDateTime,
                              Callback<NormalResponse>callback){
        getToDoServices(true, accessToken).emailReminder(userID, boardName, taskName,
                reminderDateTime, callback);
    }

    public void getSignUp(String userName, String emailID, String password,
                          Callback<LoginResponseJson> loginResponseJsonCallback){
        //SignUpRequestJson signUpRequestJson = new SignUpRequestJson(userName, emailID, password);
        getToDoServices(false, null).signUp(userName, emailID, password, loginResponseJsonCallback);
    }

    public void addNewTask(String userID, int boardID, int panelID, String taskName, String accessToken,
                           Callback<PostResponse> postResponseCallback){
        getToDoServices(true,accessToken).addNewTask(userID, boardID, panelID, taskName, postResponseCallback);
    }

    public void addNewPanel(String userID, int boardID, String panelName, String accessToken,
                            Callback<PostResponse> postResponseCallback){
        getToDoServices(true, accessToken).addNewPanel(userID, boardID, panelName, postResponseCallback);
    }

    public void deleteTask(String userID, String accessToken, int boardID, int panelID, int taskID,
                           Callback<NormalResponse> callback)
    {
        getToDoServices(true, accessToken).deleteTask(userID, boardID, panelID, taskID, callback);
    }

    public void moveTask(String userID, String accessToken, int boardID, int panelID, int taskID,
                         Callback<NormalResponse> callback)
    {
        getToDoServices(true, accessToken).moveTask(userID, boardID, panelID, taskID, callback);
    }

    private static ToDoServices getToDoServices (boolean header, final String accessToken){
        if (toDoServices == null){
            OkHttpClient client = new OkHttpClient();
            if (header == true) {
                RequestInterceptor requestInterceptor = new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("ACCESSTOKEN", accessToken);
                    }
                };

                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint(AppConfig.BASE_URL)
                        .setConverter(new GsonConverter(new Gson()))
                        .setRequestInterceptor(requestInterceptor)
                        .setClient(new OkClient(client))
                        .build();
                toDoServices = restAdapter.create(ToDoServices.class);
            }
            else{
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint(AppConfig.BASE_URL)
                        .setConverter(new GsonConverter(new Gson()))
                        .setClient(new OkClient(client))
                        .build();
                toDoServices = restAdapter.create(ToDoServices.class);
            }
        }
        return toDoServices;
    }
}
