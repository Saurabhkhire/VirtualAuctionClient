package com.auction.virtualauctionclient.auctionscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;

public class TeamsScreen extends AppCompatActivity  {

    private Button CskBtn,RcbBtn, SrhBtn, DcBtn, MiBtn, RrBtn, KkrBtn, PbksBtn, LsgBtn, GtBtn;

   // public SettingsScreen(Context contextForFinish) {
       //this.contextForFinish = contextForFinish;
    //}

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_names);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("Username");
        String roomId = bundle.getString("RoomId");

        CskBtn = findViewById(R.id.csk_button);
        RcbBtn = findViewById(R.id.rcb_button);
        SrhBtn = findViewById(R.id.srh_button);
        DcBtn = findViewById(R.id.dc_button);
        MiBtn = findViewById(R.id.mi_button);
        RrBtn = findViewById(R.id.rr_button);
        KkrBtn = findViewById(R.id.kkr_button);
        PbksBtn = findViewById(R.id.pbks_button);
        LsgBtn = findViewById(R.id.lsg_button);
        GtBtn = findViewById(R.id.gt_button);


        CskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeamsScreen.this, TeamPlayersListScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                intent.putExtra("Team", "csk");
                startActivity(intent);

            }
        });

        RcbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeamsScreen.this, TeamPlayersListScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                intent.putExtra("Team", "rcb");
                startActivity(intent);

            }
        });

        SrhBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeamsScreen.this, TeamPlayersListScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                intent.putExtra("Team", "srh");
                startActivity(intent);

            }
        });

        DcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeamsScreen.this, TeamPlayersListScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                intent.putExtra("Team", "dc");
                startActivity(intent);

            }
        });

        MiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeamsScreen.this, TeamPlayersListScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                intent.putExtra("Team", "mi");
                startActivity(intent);

            }
        });

        RrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeamsScreen.this, TeamPlayersListScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                intent.putExtra("Team", "rr");
                startActivity(intent);

            }
        });

        KkrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeamsScreen.this, TeamPlayersListScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                intent.putExtra("Team", "kkr");
                startActivity(intent);


            }
        });

        PbksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeamsScreen.this, TeamPlayersListScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                intent.putExtra("Team", "pbks");
                startActivity(intent);


            }
        });

        LsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeamsScreen.this, TeamPlayersListScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                intent.putExtra("Team", "lsg");
                startActivity(intent);


            }
        });

        GtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeamsScreen.this, TeamPlayersListScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                intent.putExtra("Team", "gt");
                startActivity(intent);


            }
        });


    }
}
