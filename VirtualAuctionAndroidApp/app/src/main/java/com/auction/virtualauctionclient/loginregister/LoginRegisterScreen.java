package com.auction.virtualauctionclient.loginregister;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;

import java.io.IOException;
import java.io.InputStream;

public class LoginRegisterScreen extends AppCompatActivity {

    public static Activity activity;
    private Button LoginBtn, RegisterBtn;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_screen);
        activity = this;
        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(Constants.GAME_BACKGROUND, this.findViewById(android.R.id.content), assetManager);


        LoginBtn = findViewById(R.id.login_button);
        RegisterBtn = findViewById(R.id.register_button);

        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND1, LoginBtn);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND1, RegisterBtn);


        // below line is to add on click listener for our add course button.
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(LoginRegisterScreen.this, LoginScreen.class);
               // intent.putExtra("team", TeamName);
                // start the activity connect to the specified class
               startActivity(intent);


            }
        });

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(LoginRegisterScreen.this, RegisterScreen.class);
                // intent.putExtra("team", TeamName);
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
