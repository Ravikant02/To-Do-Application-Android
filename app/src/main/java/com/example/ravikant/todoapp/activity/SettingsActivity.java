package com.example.ravikant.todoapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ravikant.todoapp.R;
import com.example.ravikant.todoapp.api.ToDoApi;
import com.example.ravikant.todoapp.api.response.ProfileUploadResponse;
import com.example.ravikant.todoapp.basics.Builder;
import com.example.ravikant.todoapp.config.AppConfig;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class SettingsActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE = 1;
    private String selectedPath;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    private String userID, userName, avatarUrl, accessToken;
    Builder.SharedPreferenceHelper sharedPreferenceHelper = null;

    @Bind(R.id.userProfileImage)
    ImageView userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Setting");


        sharedPreferenceHelper = Builder.getSharedPreferenceData(getApplicationContext());
        userID = sharedPreferenceHelper.getUserID();
        accessToken = sharedPreferenceHelper.getAccessToken();
        userName = sharedPreferenceHelper.getUserName();
        avatarUrl = sharedPreferenceHelper.getAvatarUrl();
        // Toast.makeText(getApplicationContext(), avatarUrl, Toast.LENGTH_SHORT).show();
        Picasso.with(this).load(AppConfig.MEDIA_BASE_URL + avatarUrl).into(userProfile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() !=null) {
            if (requestCode == SELECT_IMAGE) {
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
            }
            TypedFile typedFile = new TypedFile("multipart/form-data", new File(selectedPath));
            uploadImage(typedFile);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void uploadImage(TypedFile typedFile){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        ToDoApi.getINSTANCE(this).uploadImage(userID, accessToken, typedFile,
                new Callback<ProfileUploadResponse>() {
                    @Override
                    public void success(ProfileUploadResponse profileUploadResponse, Response response) {
                        progressDialog.dismiss();
                        if (profileUploadResponse.getResponseCode() == 200) {
                            // avatarUrl = profileUploadResponse.getAvatarUrl();
                            Builder.clearSharedPreferenceData(getApplicationContext());
                            Builder.createSharedPreference(SettingsActivity.this, userID, accessToken,
                                    userName, profileUploadResponse.getAvatarUrl());
                            finish();
                            startActivity(getIntent());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressDialog.dismiss();
                    }
                });
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    @OnClick(R.id.userProfileImage)
    public void changeImage(){
        View videoPickerLayout = getLayoutInflater().inflate(R.layout.custom_upload_profile_layout, null);
        TextView fromGallery = (TextView) videoPickerLayout.findViewById(R.id.lblChooseImage);
        TextView takeImage = (TextView) videoPickerLayout.findViewById(R.id.lblTakeImage);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(videoPickerLayout);
        builder.setTitle(R.string.upload_profile_image);
        final AlertDialog dialog = builder.create();
        dialog.show();

        fromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Select an image "), SELECT_IMAGE);
            }
        });

        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }
}
