package com.example.ravikant.todoapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.ravikant.todoapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {

    private String LOG_TAG = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_start);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.SignInButton)
    public void onSignInClick(View view){
        Log.i(LOG_TAG, "SignInButton has clicked.!");
        try{
            startActivity(new Intent(this, SignInActivity.class));
        }
        catch (Exception ex)
        {
            Log.e(LOG_TAG, ex.getMessage());
        }
    }

    @OnClick(R.id.SignUpButton)
    public void onSignUpClick(View view){
        Log.i(LOG_TAG, "SignUpButton has clicked.!");
        try{
            startActivity(new Intent(this, SignUpActivity.class));
        }
        catch (Exception ex)
        {
            Log.e(LOG_TAG, ex.getMessage());
        }
    }

    @Override
    public void onBackPressed()
    {
        return;
    }

}
