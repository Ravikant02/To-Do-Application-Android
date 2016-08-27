package com.example.ravikant.todoapp.api;

import com.example.ravikant.todoapp.api.request.LoginRequestJson;
import com.example.ravikant.todoapp.api.request.SignUpRequestJson;
import com.example.ravikant.todoapp.api.response.BoardResponse.UserBoard;
import com.example.ravikant.todoapp.api.response.LoginResponse.LoginResponseJson;
import com.example.ravikant.todoapp.api.response.DashboardResponse.UserDashboard;
import com.example.ravikant.todoapp.api.response.NormalResponse;
import com.example.ravikant.todoapp.api.response.PostResponse;
import com.example.ravikant.todoapp.api.response.ProfileUploadResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

/**
 * Created by ravikant on 22/2/16.
 */
public interface ToDoServices {

    @POST("/login/")
    void login(@Body LoginRequestJson loginRequestJson, Callback<LoginResponseJson>callback);

    @GET("/dashboard/{userID}")
    void dashboard(@Path("userID") String userID, Callback<UserDashboard> callback);

    @GET("/board/{userID}/{boardID}")
    void board(@Path("userID") String userID, @Path("boardID") Integer boardID, Callback<UserBoard> userBoardCallback);

    @FormUrlEncoded
    @POST("/signup/")
    void signUp(@Field("user_name") String userName, @Field("emailid") String emailID,
                @Field("password") String password, Callback<LoginResponseJson>callback);

    @FormUrlEncoded
    @POST("/addtask/{user_id}")
    void addNewTask(@Path("user_id") String userID, @Field("board_id") Integer boardID,
                    @Field("panel_id") Integer panelID, @Field("task_name") String task_name,
                    Callback<PostResponse> callback);

    @FormUrlEncoded
    @POST("/addpanel/{user_id}")
    void addNewPanel(@Path("user_id") String userID, @Field("board_id") Integer boardID,
                     @Field("panel_name") String panel_name, Callback<PostResponse> callback);

    @GET("/deletetask/{user_id}/{boardID}/{panelID}/{taskID}")
    void deleteTask(@Path("user_id") String userID,@Path("boardID") int boardID,
                    @Path("panelID") int panelID,
                    @Path("taskID") int taskID, Callback<NormalResponse>callback);

    @GET("/movetask/{user_id}/{boardID}/{panelID}/{taskID}")
    void moveTask(@Path("user_id") String userID,
                  @Path("boardID") int boardID, @Path("panelID") int panelID,
                  @Path("taskID") int taskID, Callback<NormalResponse>callback);

    @GET("/addboard/{user_id}/{boardName}/{groupID}")
    void addBoard(@Path("user_id") String userID,
                    @Path("boardName") String boardName,
                    @Path("groupID") int groupID, Callback<NormalResponse>callback);

    @GET("/deleteboard/{user_id}/{boardID}")
    void deleteBoard(@Path("user_id") String userID,
                  @Path("boardID") int boardID, Callback<NormalResponse>callback);

    @GET("/addgroup/{user_id}/{groupName}")
    void addGroup(@Path("user_id") String userID,
                     @Path("groupName") String groupName, Callback<NormalResponse>callback);

    @FormUrlEncoded
    @POST("/share/group/")
    void shareGroup(@Field("user_id") String userID, @Field("sender_name") String senderName,
                    @Field("receiver_name") String receiverName,
                    @Field("group_code") String groupCode,
                    @Field("group_name") String groupName,
                    @Field("email_id") String emailID, Callback<NormalResponse>callback);

    @GET("/join/group/{userID}/{groupCode}")
    void joinGroup(@Path("userID") String userID, @Path("groupCode") String groupCode,
                   Callback<NormalResponse>callback);

    @Multipart
    @POST("/upload/avatar/")
    void uploadProfile(@Part("user_id") String userID, @Part("file")TypedFile typedFile,
                       Callback<ProfileUploadResponse>callback);

    @FormUrlEncoded
    @POST("/reminder/email/")
    void emailReminder(@Field("user_id") String userID, @Field("board_name") String boardName,
                       @Field("task_name") String taskName, @Field("reminder_datetime")
                       String reminderDateTime, Callback<NormalResponse>callback);
}
