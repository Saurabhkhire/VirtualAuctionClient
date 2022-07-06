package com.auction.virtualauctionclient.room;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.auctionscreen.AuctionBreakScreen;
import com.auction.virtualauctionclient.auctionscreen.AuctionScreen;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.RoomInfo;
import com.auction.virtualauctionclient.model.RoomStatusResponse;
import com.auction.virtualauctionclient.model.Username;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinPublicRoomScreen extends AppCompatActivity {

    private Button JoinRoomBtn;
    private EditText RoomIdEdit;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_public_room_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString(Constants.I_USERNAME);

        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(Constants.GAME_BACKGROUND, this.findViewById(android.R.id.content), assetManager);

        JoinRoomBtn = findViewById(R.id.join_button);
        RoomIdEdit = findViewById(R.id.RoomIdEdit);

        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, JoinRoomBtn);


        JoinRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomId(RoomIdEdit.getText().toString());
                roomInfo.setVisibility("Public");

                try {
                (Client.getClient().joinRoom("application/json", roomInfo)).enqueue(new Callback<RoomStatusResponse>() {
                    @Override
                    public void onResponse(Call<RoomStatusResponse> call, Response<RoomStatusResponse> response) {

                        String message = response.body().getMessage();
                        String status = response.body().getRoomStatus();


                        if(message.equals(Constants.OK_MESSAGE)) {

                            if (status.equals(Constants.I_HALT_STATUS) || status.equals(Constants.I_START_STATUS)) {

                                Intent intent = new Intent(JoinPublicRoomScreen.this, RoomStatusScreen.class);
                                intent.putExtra("Username", userName);
                                intent.putExtra("RoomId", RoomIdEdit.getText().toString());
                                intent.putExtra("RoomStatus", status);
                                startActivity(intent);

                            } else if(status.equals(Constants.I_WAITING_FOR_ROUND2) || status.equals(Constants.I_WAITING_FOR_ROUND3) || status.equals(Constants.I_FINISHED_STATUS)) {

                                Intent intent = new Intent(JoinPublicRoomScreen.this, AuctionBreakScreen.class);
                                intent.putExtra("Username", userName);
                                intent.putExtra("RoomId", RoomIdEdit.getText().toString());
                                intent.putExtra("RoomStatus", status);
                                intent.putExtra("Team", response.body().getTeam());
                                intent.putExtra("MaxForeigners", response.body().getMaxForeigners());
                                intent.putExtra("MinTotal", response.body().getMinTotal());
                                intent.putExtra("MaxTotal", response.body().getMaxTotal());
                                intent.putExtra("MaxBudget", response.body().getMaxBudget());
                                startActivity(intent);

                            } else if (status.equals(Constants.I_ONGOING_STATUS) || status.equals(Constants.I_PAUSED_STATUS)) {

                                Intent intent = new Intent(JoinPublicRoomScreen.this, AuctionScreen.class);
                                intent.putExtra("Username", userName);
                                intent.putExtra("RoomId", RoomIdEdit.getText().toString());
                                intent.putExtra("Team", response.body().getTeam());
                                intent.putExtra("MaxForeigners", response.body().getMaxForeigners());
                                intent.putExtra("MinTotal", response.body().getMinTotal());
                                intent.putExtra("MaxTotal", response.body().getMaxTotal());
                                intent.putExtra("MaxBudget", response.body().getMaxBudget());
                                startActivity(intent);

                            }
                            finish();
                        } else {

                            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


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
    @Override
    public void onBackPressed() {

        finish();

    }
}
