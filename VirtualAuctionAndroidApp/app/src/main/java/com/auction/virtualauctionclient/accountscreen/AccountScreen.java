package com.auction.virtualauctionclient.accountscreen;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.loginregister.LoginRegisterScreen;
import com.auction.virtualauctionclient.loginregister.UpdateUserDetailsScreen;
import com.auction.virtualauctionclient.model.Login;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.room.GameScreen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class
AccountScreen extends AppCompatActivity {

    public static Activity activity;
    private Button ReadUserDetailsBtn, UpdateUserDetailsBtn, LogoutUserBtn, DeleteUserBtn;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString(Constants.I_USERNAME);
        String password = bundle.getString(Constants.I_PASSWORD);
        String emailId = bundle.getString(Constants.I_EMAIL_ID);
        String keepLoggedIn = bundle.getString(Constants.I_KEEP_LOGGED_IN);
        activity = this;
        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(Constants.GAME_BACKGROUND, this.findViewById(android.R.id.content), assetManager);

        ReadUserDetailsBtn = findViewById(R.id.read_user_details_button);
        UpdateUserDetailsBtn = findViewById(R.id.update_user_details_button);
        LogoutUserBtn = findViewById(R.id.logout_button);
        DeleteUserBtn = findViewById(R.id.delete_user_button);

        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND1, ReadUserDetailsBtn);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND1, UpdateUserDetailsBtn);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND1, LogoutUserBtn);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND1, DeleteUserBtn);

        // below line is to add on click listener for our add course button.
        ReadUserDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AccountScreen.this, ReadUserDetailsScreen.class);
                intent.putExtra(Constants.I_USERNAME, userName);
                intent.putExtra(Constants.I_PASSWORD, password);
                intent.putExtra(Constants.I_EMAIL_ID, emailId);
               startActivity(intent);


            }
        });

        UpdateUserDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AccountScreen.this, UpdateUserDetailsScreen.class);
                intent.putExtra(Constants.I_USERNAME, userName);
                intent.putExtra(Constants.I_PASSWORD, password);
                intent.putExtra(Constants.I_EMAIL_ID, emailId);
                intent.putExtra(Constants.I_KEEP_LOGGED_IN, keepLoggedIn);
                startActivity(intent);


            }
        });

        LogoutUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(getFilesDir(), Constants.INTERNAL_STORAGE_FILE);
                try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    bufferedWriter.write("false" + "," + userName + "," + password + "," + emailId);
                    bufferedWriter.close();
                    outputStreamWriter.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }


                Intent intent = new Intent(AccountScreen.this, LoginRegisterScreen.class);
                // intent.putExtra("team", TeamName);
                // start the activity connect to the specified class
                startActivity(intent);

                finish();
                GameScreen.activity.finish();


            }
        });

        DeleteUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                try {
                    Login login = new Login();
                    login.setUsername(userName);
                    login.setPassword(password);

                    (Client.getClient().deleteUser("application/json", login)).enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                            String message = response.body().getMessage();

                            if (message.equals(Constants.OK_MESSAGE)) {
                                Intent intent = new Intent(AccountScreen.this, LoginRegisterScreen.class);
                                // intent.putExtra("team", TeamName);
                                // start the activity connect to the specified class
                                startActivity(intent);

                                finish();
                                GameScreen.activity.finish();

                            } else {

                                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            }
                        }


                        @Override
                        public void onFailure(Call<ResponseMessage> call, Throwable t) {
                            Log.d("f", t.getMessage());
                        }

                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }





            }
        });

    }
    @Override
    public void onBackPressed() {

        finish();

    }
}
