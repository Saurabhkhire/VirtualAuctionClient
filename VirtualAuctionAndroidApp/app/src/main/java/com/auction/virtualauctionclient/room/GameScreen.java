package com.auction.virtualauctionclient.room;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.accountscreen.AccountScreen;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.loginregister.LoginScreen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class GameScreen extends AppCompatActivity {

    public static Activity activity;
    private String userName, password, phoneNumber, keepLoggedIn;
    private Button AccountBtn, IplAuctionBtn;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString(Constants.I_USERNAME);
        activity = this;
        AccountBtn = findViewById(R.id.account_button);
        IplAuctionBtn = findViewById(R.id.ipl_auction_button);

        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND1, AccountBtn);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND1, IplAuctionBtn);

        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(Constants.GAME_BACKGROUND, this.findViewById(android.R.id.content), assetManager);

        // below line is to add on click listener for our add course button.
        AccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(getFilesDir(), Constants.INTERNAL_STORAGE_FILE);
                if(file.exists()) {
                    try (FileInputStream fileInputStream = new FileInputStream(file)) {
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String lineData = bufferedReader.readLine();
                        String[] splitData = lineData.split(",");
                        keepLoggedIn = splitData[0];
                        userName = splitData[1];
                        password = splitData[2];
                        phoneNumber = splitData[3];
                        bufferedReader.close();
                        inputStreamReader.close();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }

                Intent intent = new Intent(GameScreen.this, AccountScreen.class);
                intent.putExtra(Constants.I_USERNAME, userName);
                intent.putExtra(Constants.I_PASSWORD, password);
                intent.putExtra(Constants.I_EMAIL_ID, phoneNumber);
                intent.putExtra(Constants.I_KEEP_LOGGED_IN, keepLoggedIn);
                startActivity(intent);


            }
        });

        IplAuctionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(getFilesDir(), Constants.INTERNAL_STORAGE_FILE);
                if(file.exists()) {
                    try (FileInputStream fileInputStream = new FileInputStream(file)) {
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String lineData = bufferedReader.readLine();
                        String[] splitData = lineData.split(",");
                        userName = splitData[1];
                        bufferedReader.close();
                        inputStreamReader.close();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }

                Intent intent = new Intent(GameScreen.this, RoomScreen.class);
                intent.putExtra(Constants.I_USERNAME, userName);
                // start the activity connect to the specified class
                startActivity(intent);


            }
        });

    }
    @Override
    public void onBackPressed() {

        finish();

    }
}
