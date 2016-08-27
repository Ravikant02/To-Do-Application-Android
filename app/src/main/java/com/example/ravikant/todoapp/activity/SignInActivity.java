package com.example.ravikant.todoapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ravikant.todoapp.R;
import com.example.ravikant.todoapp.api.ToDoApi;
import com.example.ravikant.todoapp.api.response.LoginResponse.LoginResponseJson;
import com.example.ravikant.todoapp.basics.Builder;
import com.example.ravikant.todoapp.basics.NetworkTester;
import com.example.ravikant.todoapp.converter.UserConverter;
import com.example.ravikant.todoapp.core.LoginData;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignInActivity extends AppCompatActivity {

    @Bind(R.id.email)
    EditText txtEmail;
    @Bind(R.id.password)
    EditText txtPassword;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        ButterKnife.bind(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCancelable(false);

        /*if (NetworkTester.isNetworkAvailable(getApplicationContext())){
            //
        }
        else{
            Toast.makeText(getApplicationContext(), "Please check network",
                    Toast.LENGTH_SHORT).show();
            //NetworkTester.networkMessageDialog(getApplicationContext());
        }
*/
    }


    @OnClick(R.id.emailSignInButton)
    public void onClick(final View view){
        if (NetworkTester.isNetworkAvailable(getApplicationContext())){
            String emailID = txtEmail.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();

            if(emailID.equals("") || password.equals("")){
                Toast.makeText(getApplicationContext(), "Please Provide USER NAME and PASSWORD",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                dialog.show();
                ToDoApi.getINSTANCE(this).getLogin(txtEmail.getText().toString().trim(),
                        txtPassword.getText().toString().trim(),
                        new Callback<LoginResponseJson>() {
                            @Override
                            public void success(LoginResponseJson loginResponseJson, Response response) {
                                if (loginResponseJson.getStatus().equals("success")) {
                                    LoginData loginData = null;
                                    loginData = UserConverter.convert(loginResponseJson.getData());
                                    Builder.createSharedPreference(getApplicationContext(), loginData.getUserID(),
                                            loginData.getAccessToken(), loginData.getEmailID(), loginData.getAvatarUrl());
                                    dialog.dismiss();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    dialog.dismiss();
                                    Snackbar.make(view, R.string.invalid_user_name, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.e("HERE", error.getMessage());
                            }
                        });
            }
        }
        else{
            //NetworkTester.networkMessageDialog(getApplicationContext());
            Snackbar.make(view, R.string.network_message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
