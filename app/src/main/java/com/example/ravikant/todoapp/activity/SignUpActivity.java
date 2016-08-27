package com.example.ravikant.todoapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ravikant.todoapp.R;
import com.example.ravikant.todoapp.api.ToDoApi;
import com.example.ravikant.todoapp.api.response.LoginResponse.LoginResponseJson;
import com.example.ravikant.todoapp.basics.Builder;
import com.example.ravikant.todoapp.converter.UserConverter;
import com.example.ravikant.todoapp.core.LoginData;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends AppCompatActivity {

    @Bind(R.id.txtUserName)
    EditText txtUserName;
    @Bind(R.id.txtEmail)
    EditText txtEmail;
    @Bind(R.id.txtPassword)
    EditText txtPassword;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        ButterKnife.bind(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCancelable(false);
    }

    @OnClick(R.id.btnSignUp)
    public void onClick(View view){
        String userName = txtUserName.getText().toString().trim();
        String emailID = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        if(userName.equals("") || emailID.equals("") || password.equals("")){
            Toast.makeText(getApplicationContext(),"", Toast.LENGTH_LONG).show();
        }
        else{
            dialog.show();
            ToDoApi.getINSTANCE(this).getSignUp(userName, emailID, password, new Callback<LoginResponseJson>() {
                @Override
                public void success(LoginResponseJson loginResponseJson, Response response) {
                    if (loginResponseJson.getResponseCode() == 200){
                        LoginData loginData = null;
                        loginData = UserConverter.convert(loginResponseJson.getData());
                        Builder.createSharedPreference(getApplicationContext(), loginData.getUserID(),
                                loginData.getAccessToken(), loginData.getEmailID(), loginData.getAvatarUrl());
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    else if(loginResponseJson.getResponseCode() == 406){
                        //Toast.makeText(getApplicationContext(), loginResponseJson.getMessage(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUpActivity.this);
                        alertDialog.setTitle("Try Again");
                        alertDialog.setMessage(loginResponseJson.getMessage());
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog closed
                            }
                        });
                        alertDialog.show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }

}
