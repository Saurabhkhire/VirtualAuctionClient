package com.auction.virtualauctionclient.auctionscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonApiLogic;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.Bid;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.RoomInfo;

import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionScreen extends AppCompatActivity  {

    private AuctionStatusThread statusThread;
    public static Activity activity;
    private ProgressBar LoadingBar;
    private Button BidBtn, TeamsBtn, SettingsBtn;
    private TextView RoomIdEdit, HostEdit, PlayerNameTxt, PlayerCountryTxt, PlayerRoleTxt, BattingStyleTxt, BowlingStyleTxt, BattingPositionTxt, PriceTxt, TimeTxt, CurrentBudgetEdit, StatusEdit, PausedEdit, SetNameEdit;
    private ImageView PlayerImage1,PlayerImage2, CurrentBid, TeamNameTxt;
    private String userName, roomId;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auction_screen);
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString("Username");
        roomId = bundle.getString("RoomId");
        String team = bundle.getString("Team");
        int maxForeigners = bundle.getInt("MaxForeigners");
        int minTotal = bundle.getInt("MinTotal");
        int maxTotal = bundle.getInt("MaxTotal");
        int maxBudget = bundle.getInt("MaxBudget");
        //int colorValue = CommonLogic.colorForAuctionScreenBackround(team, this.findViewById(android.R.id.content));
        activity = this;

        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(team + Constants.PNG_EXT, this.findViewById(android.R.id.content), assetManager);

        RoomIdEdit = findViewById(R.id.RoomIdEdit);
        HostEdit = findViewById(R.id.HostText);
        TeamNameTxt = findViewById(R.id.TeamName);
        SetNameEdit = findViewById(R.id.SetNameEdit);
        PlayerNameTxt = findViewById(R.id.PlayerNameEdit);
        PlayerCountryTxt = findViewById(R.id.PlayerCountryEdit);
        PlayerRoleTxt = findViewById(R.id.PlayerRoleEdit);
        BattingStyleTxt = findViewById(R.id.BattingStyleEdit);
        BowlingStyleTxt = findViewById(R.id.BowlingStyleEdit);
        BattingPositionTxt = findViewById(R.id.BattingPositionEdit);
        PriceTxt = findViewById(R.id.PriceEdit);
        CurrentBid = findViewById(R.id.CurrentBidEdit);
        TimeTxt = findViewById(R.id.TimeEdit);
        CurrentBudgetEdit = findViewById(R.id.BudgetEdit);
        StatusEdit = findViewById(R.id.StatusEdit);
        PausedEdit = findViewById(R.id.PausedEdit);
        PlayerImage1 = findViewById(R.id.PlayerImage1);
        PlayerImage2 = findViewById(R.id.PlayerImage2);
        BidBtn = findViewById(R.id.bid_button);
        TeamsBtn = findViewById(R.id.teams_button);
        SettingsBtn = findViewById(R.id.settings_button);
        LoadingBar = findViewById(R.id.LoadingBar);

        //TeamNameTxt.setText(team);

        //AssetManager assetManager = getResources().getAssets();
        final Context context = AuctionScreen.this.getApplicationContext();
        final Context contextForFinish = AuctionScreen.this;

        try {
            Drawable d = Drawable.createFromStream(assetManager.open("other/settings.png"), null);
            SettingsBtn.setBackground(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        CommonLogic.setBackgroundForButtons(team, BidBtn);
        CommonLogic.setBackgroundForButtons(team, TeamsBtn);


        CommonLogic.setTeamImage(team, TeamNameTxt, contextForFinish);
        RoomIdEdit.setText("Room Id : " + roomId);

        statusThread = new AuctionStatusThread(TeamNameTxt, SetNameEdit, PlayerNameTxt, PlayerCountryTxt, PlayerRoleTxt, BattingStyleTxt, BowlingStyleTxt, BattingPositionTxt, PriceTxt, CurrentBid, TimeTxt, CurrentBudgetEdit,HostEdit,StatusEdit,PausedEdit, PlayerImage1, PlayerImage2, BidBtn,roomId, team, userName, maxForeigners, minTotal, maxTotal, maxBudget,assetManager, context, contextForFinish, LoadingBar);
        new Thread(statusThread).start();

        BidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Bid price = new Bid();
                    price.setRoomId(roomId);
                    price.setTeam(team);
                    price.setPlayerName(PlayerNameTxt.getText().toString());

                    (Client.getClient().bid("application/json", price)).enqueue(new Callback<ResponseMessage>() {
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

        SettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AuctionScreen.this,SettingsScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                intent.putExtra("Host", HostEdit.getText().toString());
                intent.putExtra("Team", team);
                intent.putExtra("MaxForeigners", maxForeigners);
                intent.putExtra("MinTotal", minTotal);
                intent.putExtra("MaxTotal", maxTotal);
                intent.putExtra("MaxBudget", maxBudget);
                startActivity(intent);


            }
        });

        TeamsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AuctionScreen.this,TeamsScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                intent.putExtra("Team", team);
                startActivity(intent);

            }
        });

    }
    @Override
    public void onBackPressed() {

        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setUsername(userName);
        roomInfo.setRoomId(roomId);
        statusThread.checkStatus = false;
        CommonApiLogic.leaveRoomApi(roomInfo);
        finish();

    }



}
