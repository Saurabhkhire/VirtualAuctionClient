package com.auction.virtualauctionclient.room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.auctionscreen.AuctionBreakScreen;
import com.auction.virtualauctionclient.auctionscreen.AuctionScreen;
import com.auction.virtualauctionclient.model.RoomInfo;
import com.auction.virtualauctionclient.model.RoomStatus;
import com.auction.virtualauctionclient.model.RoomStatusResponse;
import com.auction.virtualauctionclient.model.Username;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomScreen extends AppCompatActivity {

    private Button CreateRoomBtn, JoinRoomBtn;
    private EditText RoomIdEdit;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("Username");

        CreateRoomBtn = findViewById(R.id.create_room_button);
        JoinRoomBtn = findViewById(R.id.join_room_button);
        RoomIdEdit = findViewById(R.id.RoomIdEdit);

        // below line is to add on click listener for our add course button.
        CreateRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Username username = new Username();
                username.setUsername(userName);
                (Client.getClient().createRoom("application/json", username)).enqueue(new Callback<RoomInfo>() {
                    @Override
                    public void onResponse(Call<RoomInfo> call, Response<RoomInfo> response) {

                        String message = response.body().getMessage();
                        String roomId = response.body().getRoomId();

                        if(!message.equals("Error")) {

                            Intent intent = new Intent(RoomScreen.this, RoomStatusScreen.class);
                            intent.putExtra("Username", userName);
                            intent.putExtra("RoomId", roomId);
                            intent.putExtra("Status", "Start");
                            startActivity(intent);


                        }
                    }

                    @Override
                    public void onFailure(Call<RoomInfo> call, Throwable t) {
                        Log.d("f", t.getMessage());
                    }

                });





            }
        });

        JoinRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] status = {""};

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomId(RoomIdEdit.getText().toString());

                try {
                (Client.getClient().joinRoom("application/json", roomInfo)).enqueue(new Callback<RoomStatus>() {
                    @Override
                    public void onResponse(Call<RoomStatus> call, Response<RoomStatus> response) {

                        String status = response.body().getRoomStatus();

                            if(status.equals("Paused") || status.equals("Start")) {

                                Intent intent = new Intent(RoomScreen.this, RoomStatusScreen.class);
                                intent.putExtra("Username", userName);
                                intent.putExtra("RoomId", RoomIdEdit.getText().toString());
                                intent.putExtra("Status", status);
                                startActivity(intent);

                            }

                    }

                    @Override
                    public void onFailure(Call<RoomStatus> call, Throwable t) {
                        Log.d("f", t.getMessage());
                    }

                });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {

                     (Client.getClient().roomStatus("application/json", roomInfo)).enqueue(new Callback<RoomStatusResponse>() {
                        @Override
                        public void onResponse(Call<RoomStatusResponse> call, Response<RoomStatusResponse> response) {
                            //Log.d("responseBodyGET", response.body().toString());
                            String team = response.body().getTeam();
                            String status = response.body().getRoomStatus();
                            int maxForeigners = response.body().getMaxForeigners();
                            int minTotal = response.body().getMinTotal();
                            int maxTotal = response.body().getMaxTotal();
                            int maxBudget = response.body().getMaxBudget();

                            Intent intent = new Intent(RoomScreen.this, AuctionScreen.class);;

                            if(status.equals("Ongoing") || status.equals("TempPaused") || status.equals("TempPausedForRound2") || status.equals("TempPausedForRound3")) {

                                if (status.equals("TempPausedForRound2") || status.equals("TempPausedForRound3")) {

                                    intent = new Intent(RoomScreen.this, AuctionBreakScreen.class);
                                    intent.putExtra("Status", status);

                                }
                                intent.putExtra("Username", userName);
                                intent.putExtra("RoomId", RoomIdEdit.getText().toString());
                                intent.putExtra("Team", team);
                                intent.putExtra("MaxForeigners", maxForeigners);
                                intent.putExtra("MinTotal", minTotal);
                                intent.putExtra("MaxTotal", maxTotal);
                                intent.putExtra("MaxBudget", maxBudget);
                                startActivity(intent);

                            }
                        }

                        @Override
                        public void onFailure(Call<RoomStatusResponse> call, Throwable t) {
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
