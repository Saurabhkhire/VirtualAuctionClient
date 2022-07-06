package com.auction.virtualauctionclient.loginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ContentFrameLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.room.GameScreen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private String keepLoggedIn, userName;
    private ImageView imageView;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        imageView = findViewById(R.id.StartImage);

       // AssetManager assetManager = getResources().getAssets();
        final Context context = MainActivity.this;
        CommonLogic.setOtherImage("start", imageView, context);

        ContentFrameLayout con = this.findViewById(android.R.id.content);
        final ViewTreeObserver mLayoutObserver = con.getViewTreeObserver();

        //mLayoutObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        //{

           // @Override
            //public void onGlobalLayout()
            //{
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        int deviceWidth = metrics.widthPixels;

        int deviceHeight = metrics.heightPixels;

        Log.i("aaa", String.valueOf(deviceWidth)+ ":" +String.valueOf(deviceHeight));

        float widthInPercentage =  ( (float) 1000 / 320 )  * 100; // 280 is the width of my LinearLayout and 320 is device screen width as i know my current device resolution are 320 x 480 so i'm calculating how much space (in percentage my layout is covering so that it should cover same area (in percentage) on any other device having different resolution

        float heightInPercentage =  ( (float) 1900 / 480 ) * 100; // same procedure 300 is the height of the LinearLayout and i'm converting it into percentage

        int mLayoutWidth = (int) ( (widthInPercentage * deviceWidth) / 100 );

        int mLayoutHeight = (int) ( (heightInPercentage * deviceHeight) / 100 );

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mLayoutWidth, mLayoutHeight);

        con.setLayoutParams(layoutParams);
           // }
      //  });


        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(10000);  //Delay of 10 seconds
                } catch (Exception e) {

                } finally {

                    File file = new File(getFilesDir(), Constants.INTERNAL_STORAGE_FILE);
                    if(file.exists()) {
                        try (FileInputStream fileInputStream = new FileInputStream(file)) {
                            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            String lineData = bufferedReader.readLine();
                            String[] splitData = lineData.split(",");
                            keepLoggedIn = splitData[0];
                            userName = splitData[1];
                            bufferedReader.close();
                            inputStreamReader.close();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        keepLoggedIn = Constants.I_FALSE;
                    }

                    if(keepLoggedIn.equals(Constants.I_TRUE)) {
                        Intent intent = new Intent(MainActivity.this, GameScreen.class);
                        intent.putExtra(Constants.I_USERNAME, userName);
                        // start the activity connect to the specified class
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, LoginRegisterScreen.class);
                        // intent.putExtra("team", TeamName);
                        // start the activity connect to the specified class
                        startActivity(intent);
                    }
                    finish();
                }
            }
        };
        welcomeThread.start();



    }



}
