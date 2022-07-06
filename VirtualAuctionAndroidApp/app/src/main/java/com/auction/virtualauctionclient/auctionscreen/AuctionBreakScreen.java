package com.auction.virtualauctionclient.auctionscreen;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonApiLogic;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.RoomInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionBreakScreen extends AppCompatActivity  {

    private AuctionBreakStatusThread auctionBreakStatusThread;
    private TextView HostEdit, RoomIdEdit, MinBreakTime;
    private ImageView TeamNameEdit;
    private Button LeaveBtn,QuitBtn, ContinueBtn, TeamsBtn, UnsoldListBtn, SelectPlayersForRoundBtn;
    private String userName, roomId;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auction_break_screen);
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString("Username");
        roomId = bundle.getString("RoomId");
        String team = bundle.getString("Team");
        int maxForeigners = bundle.getInt("MaxForeigners");
        int minTotal = bundle.getInt("MinTotal");
        int maxTotal = bundle.getInt("MaxTotal");
        int maxBudget = bundle.getInt("MaxBudget");
        String status = bundle.getString("RoomStatus");

        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(team + Constants.PNG_EXT, this.findViewById(android.R.id.content), assetManager);

        RoomIdEdit = findViewById(R.id.RoomIdEdit);
        HostEdit = findViewById(R.id.HostEdit);
        MinBreakTime = findViewById(R.id.MinBreakTime);
        TeamNameEdit = findViewById(R.id.TeamName);
        LeaveBtn = findViewById(R.id.leave_button);
        QuitBtn = findViewById(R.id.quit_button);
        ContinueBtn = findViewById(R.id.play_button);
        TeamsBtn = findViewById(R.id.teams_button);
        UnsoldListBtn = findViewById(R.id.unsold_list_button);
        SelectPlayersForRoundBtn = findViewById(R.id.select_player_for_round_button);

        CommonLogic.setBackgroundForButtons(team, LeaveBtn);
        CommonLogic.setBackgroundForButtons(team, QuitBtn);
        CommonLogic.setBackgroundForButtons(team, ContinueBtn);
        CommonLogic.setBackgroundForButtons(team, TeamsBtn);
        CommonLogic.setBackgroundForButtons(team, UnsoldListBtn);
        CommonLogic.setBackgroundForButtons(team, SelectPlayersForRoundBtn);

        final Context context = AuctionBreakScreen.this.getApplicationContext();
        final Context contextForFinish = AuctionBreakScreen.this;

        CommonLogic.setTeamImage(team, TeamNameEdit, contextForFinish);
        RoomIdEdit.setText("Room Id : " + roomId);

        auctionBreakStatusThread = new AuctionBreakStatusThread(HostEdit, MinBreakTime, ContinueBtn,roomId, team, userName, maxForeigners, minTotal, maxTotal, maxBudget, context, contextForFinish);
        new Thread(auctionBreakStatusThread).start();

        if(status.equals(Constants.I_WAITING_FOR_ROUND2)) {
            SelectPlayersForRoundBtn.setVisibility(View.INVISIBLE);
        }

        LeaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomId(roomId);
                auctionBreakStatusThread.checkStatus = false;
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
                auctionBreakStatusThread.checkStatus = false;
                String message = CommonApiLogic.quitRoomApi(roomInfo);

                finish();
            }
        });

        TeamsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AuctionBreakScreen.this,TeamsScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                intent.putExtra("Team", team);
                startActivity(intent);

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

                if(status.equals(Constants.I_WAITING_FOR_ROUND3)) {
                    Intent intent = new Intent(AuctionBreakScreen.this, UnsoldPlayersForShortlistListScreen.class);
                    intent.putExtra("Username", userName);
                    intent.putExtra("RoomId", roomId);
                    intent.putExtra("Team", team);
                    intent.putExtra("RoomStatus", status);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AuctionBreakScreen.this, AddPlayersAfterAuctionListScreen.class);
                    intent.putExtra("Username", userName);
                    intent.putExtra("RoomId", roomId);
                    intent.putExtra("Team", team);
                    intent.putExtra("RoomStatus", status);
                    startActivity(intent);
                }


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

                            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

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

        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setUsername(userName);
        roomInfo.setRoomId(roomId);
        auctionBreakStatusThread.checkStatus = false;
        CommonApiLogic.leaveRoomApi(roomInfo);
        finish();

    }
}
