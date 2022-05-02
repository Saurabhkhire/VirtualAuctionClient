package com.auction.virtualauctionclient.auctionscreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonApiLogic;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.RoomInfo;
import com.auction.virtualauctionclient.model.SkipInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsScreen extends AppCompatActivity  {

    private Button LeaveBtn,QuitBtn, AuctionParamsBtn, SkipAllBtn, SkipThisSetBtn, PauseBtn, PlayBtn, ChangeHostBtn;

   // public SettingsScreen(Context contextForFinish) {
       //this.contextForFinish = contextForFinish;
    //}

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("Username");
        String roomId = bundle.getString("RoomId");
        String host = bundle.getString("Host");
        String team = bundle.getString("Team");
        int maxForeigners = bundle.getInt("MaxForeigners");
        int minTotal = bundle.getInt("MinTotal");
        int maxTotal = bundle.getInt("MaxTotal");
        int maxBudget = bundle.getInt("MaxBudget");

        LeaveBtn = findViewById(R.id.leave_button);
        QuitBtn = findViewById(R.id.quit_button);
        AuctionParamsBtn = findViewById(R.id.auction_params_button);
        SkipAllBtn = findViewById(R.id.skip_all_button);
        SkipThisSetBtn = findViewById(R.id.skip_this_set_button);
        PauseBtn = findViewById(R.id.pause_button);
        PlayBtn = findViewById(R.id.play_button);
        ChangeHostBtn = findViewById(R.id.change_host_button);

        if(host.equals("")) {
            SkipAllBtn.setVisibility(View.INVISIBLE);
            SkipThisSetBtn.setVisibility(View.INVISIBLE);
            PauseBtn.setVisibility(View.INVISIBLE);
            PlayBtn.setVisibility(View.INVISIBLE);
            ChangeHostBtn.setVisibility(View.INVISIBLE);
        }

        LeaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomId(roomId);

                String message = CommonApiLogic.leaveRoomApi(roomInfo);

                finish();
                AuctionScreen.activity.finish();
            }
        });

        QuitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomId(roomId);

                String message = CommonApiLogic.quitRoomApi(roomInfo);

                finish();
                AuctionScreen.activity.finish();
            }
        });

        ChangeHostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsScreen.this, UsernamesListScreen.class);
                intent.putExtra("MaxForeigners", maxForeigners);
                intent.putExtra("MinTotal", minTotal);
                intent.putExtra("MaxTotal", maxTotal);
                intent.putExtra("MaxBudget", maxBudget);
                startActivity(intent);


            }
        });

        SkipAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    SkipInfo skipInfo = new SkipInfo();
                    skipInfo.setUsername(userName);
                    skipInfo.setRoomId(roomId);
                    skipInfo.setSkipType("SkipAll");

                    (Client.getClient().skipSets("application/json", skipInfo)).enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                            //Log.d("responseBodyGET", response.body().toString());

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

        SkipThisSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    SkipInfo skipInfo = new SkipInfo();
                    skipInfo.setUsername(userName);
                    skipInfo.setRoomId(roomId);
                    skipInfo.setSkipType("SkipOneSet");

                    (Client.getClient().skipSets("application/json", skipInfo)).enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                            //Log.d("responseBodyGET", response.body().toString());

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

        PauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomId(roomId);

                String message = CommonApiLogic.pauseAuctionApi(roomInfo);

            }
        });

        PlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomId(roomId);

                String message = CommonApiLogic.playAuctionApi(roomInfo);


            }
        });

        ChangeHostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsScreen.this, UsernamesListScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                startActivity(intent);


            }
        });

    }
}
