package com.auction.virtualauctionclient.auctionscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonApiLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.RoomInfo;
import com.auction.virtualauctionclient.model.SkipInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionBreakScreen extends AppCompatActivity  {

    private TextView HostEdit;
    private Button LeaveBtn,QuitBtn, ContinueBtn, TeamsBtn, UnsoldListBtn, SelectPlayersForRoundBtn;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auction_break_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("Username");
        String roomId = bundle.getString("RoomId");
        String team = bundle.getString("Team");
        int maxForeigners = bundle.getInt("MaxForeigners");
        int minTotal = bundle.getInt("MinTotal");
        int maxTotal = bundle.getInt("MaxTotal");
        int maxBudget = bundle.getInt("MaxBudget");
        String status = bundle.getString("RoomStatus");

        HostEdit = findViewById(R.id.HostEdit);
        LeaveBtn = findViewById(R.id.leave_button);
        QuitBtn = findViewById(R.id.quit_button);
        ContinueBtn = findViewById(R.id.play_button);
        TeamsBtn = findViewById(R.id.teams_button);
        UnsoldListBtn = findViewById(R.id.unsold_list_button);
        SelectPlayersForRoundBtn = findViewById(R.id.select_player_for_round_button);

        final Context context = AuctionBreakScreen.this.getApplicationContext();
        final Context contextForFinish = AuctionBreakScreen.this;

        AuctionBreakStatusThread auctionBreakStatusThread = new AuctionBreakStatusThread(HostEdit, ContinueBtn,roomId, team, userName, maxForeigners, minTotal, maxTotal, maxBudget, context, contextForFinish);
        new Thread(auctionBreakStatusThread).start();

        if(status.equals("TempPausedForRound2")) {
            SelectPlayersForRoundBtn.setVisibility(View.INVISIBLE);
        }

        LeaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomId(roomId);

                String message = CommonApiLogic.leaveRoomApi(roomInfo);

                finish();
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
            }
        });

        UnsoldListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AuctionBreakScreen.this, UnsoldPlayersListScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                startActivity(intent);

            }
        });

        SelectPlayersForRoundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AuctionBreakScreen.this, UnsoldPlayersForShortlistListScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                startActivity(intent);


            }
        });

        ContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomId(roomId);

                try {

                    (Client.getClient().playAuction("application/json", roomInfo)).enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                            //Log.d("responseBodyGET", response.body().toString());
                            String message = response.body().getMessage();

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
}
