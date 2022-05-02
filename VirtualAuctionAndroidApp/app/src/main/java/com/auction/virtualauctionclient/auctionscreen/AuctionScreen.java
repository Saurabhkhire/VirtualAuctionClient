package com.auction.virtualauctionclient.auctionscreen;

import android.app.Activity;
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
import com.auction.virtualauctionclient.model.Bid;
import com.auction.virtualauctionclient.model.ResponseMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionScreen extends AppCompatActivity  {

    public static Activity activity;
    private Button BidBtn, TeamsBtn, SettingsBtn;
    private TextView RoomIdEdit, HostEdit, TeamNameTxt, PlayerNameTxt, PlayerCountryTxt, PlayerRoleTxt, PriceTxt, CurrentBid, TimeTxt, CurrentBudgetEdit;


    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auction_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("Username");
        String roomId = bundle.getString("RoomId");
        String team = bundle.getString("Team");
        int maxForeigners = bundle.getInt("MaxForeigners");
        int minTotal = bundle.getInt("MinTotal");
        int maxTotal = bundle.getInt("MaxTotal");
        int maxBudget = bundle.getInt("MaxBudget");
        activity = this;


        RoomIdEdit = findViewById(R.id.RoomIdEdit);
        HostEdit = findViewById(R.id.HostText);
        TeamNameTxt = findViewById(R.id.TeamName);
        PlayerNameTxt = findViewById(R.id.PlayerNameEdit);
        PlayerCountryTxt = findViewById(R.id.PlayerCountryEdit);
        PlayerRoleTxt = findViewById(R.id.PlayerRoleEdit);
        PriceTxt = findViewById(R.id.PriceEdit);
        CurrentBid = findViewById(R.id.CurrentBidEdit);
        TimeTxt = findViewById(R.id.TimeEdit);
        CurrentBudgetEdit = findViewById(R.id.BudgetEdit);
        BidBtn = findViewById(R.id.bid_button);
        TeamsBtn = findViewById(R.id.teams_button);
        SettingsBtn = findViewById(R.id.settings_button);

        TeamNameTxt.setText(team);


        final Context context = AuctionScreen.this.getApplicationContext();
        final Context contextForFinish = AuctionScreen.this;

        AuctionStatusThread statusThread = new AuctionStatusThread(TeamNameTxt, PlayerNameTxt, PlayerCountryTxt, PlayerRoleTxt, PriceTxt, CurrentBid, TimeTxt, CurrentBudgetEdit,HostEdit, BidBtn,roomId, team, userName, maxForeigners, minTotal, maxTotal, maxBudget, context, contextForFinish);
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
                startActivity(intent);

            }
        });

    }
}
