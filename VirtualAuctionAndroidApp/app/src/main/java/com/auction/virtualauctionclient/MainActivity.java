package com.auction.virtualauctionclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.auction.virtualauctionclient.loginregister.LoginScreen;
import com.auction.virtualauctionclient.loginregister.RegisterScreen;

public class MainActivity extends AppCompatActivity {

    private Button LoginBtn, RegisterBtn;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginBtn = findViewById(R.id.login_button);
        RegisterBtn = findViewById(R.id.register_button);

        // below line is to add on click listener for our add course button.
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, LoginScreen.class);
               // intent.putExtra("team", TeamName);
                // start the activity connect to the specified class
               startActivity(intent);


            }
        });

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, RegisterScreen.class);
                // intent.putExtra("team", TeamName);
                // start the activity connect to the specified class
                startActivity(intent);


            }
        });

    }
}
