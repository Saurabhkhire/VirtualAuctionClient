package com.auction.virtualauctionclient.room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.loginregister.LoginScreen;

public class GameScreen extends AppCompatActivity {

    private Button ProfileBtn, IplAuctionBtn;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("Username");

        ProfileBtn = findViewById(R.id.profile_button);
        IplAuctionBtn = findViewById(R.id.ipl_auction_button);

        // below line is to add on click listener for our add course button.
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(GameScreen.this, LoginScreen.class);
                intent.putExtra("Username", userName);
                startActivity(intent);


            }
        });

        IplAuctionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(GameScreen.this, RoomScreen.class);
                intent.putExtra("Username", userName);
                // start the activity connect to the specified class
                startActivity(intent);


            }
        });

    }
}
