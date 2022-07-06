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
import com.auction.virtualauctionclient.model.RoomStatus;
import com.auction.virtualauctionclient.model.RoomStatusResponse;
import com.auction.virtualauctionclient.model.Username;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomScreen extends AppCompatActivity {

    private Button CreatePublicRoomBtn, CreatePrivateRoomBtn, JoinPublicRoomBtn, JoinPrivateRoomBtn, JoinRandomRoomBtn;
    private EditText RoomIdEdit;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString(Constants.I_USERNAME);

        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(Constants.GAME_BACKGROUND, this.findViewById(android.R.id.content), assetManager);

        CreatePublicRoomBtn = findViewById(R.id.create_public_room_button);
        CreatePrivateRoomBtn = findViewById(R.id.create_private_room_button);
        JoinPublicRoomBtn = findViewById(R.id.join_public_room_button);
        JoinPrivateRoomBtn = findViewById(R.id.join_private_room_button);
        JoinRandomRoomBtn = findViewById(R.id.join_random_room_button);
        RoomIdEdit = findViewById(R.id.RoomIdEdit);

        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND1, CreatePublicRoomBtn);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND1, CreatePrivateRoomBtn);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND1, JoinPublicRoomBtn);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND1, JoinPrivateRoomBtn);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND1, JoinRandomRoomBtn);

        // below line is to add on click listener for our add course button.
        CreatePublicRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setVisibility("Public");

                try {
                (Client.getClient().createRoom("application/json", roomInfo)).enqueue(new Callback<RoomInfo>() {
                    @Override
                    public void onResponse(Call<RoomInfo> call, Response<RoomInfo> response) {

                        String message = response.body().getMessage();
                        String roomId = response.body().getRoomId();

                        if(message.equals(Constants.OK_MESSAGE)) {

                            Intent intent = new Intent(RoomScreen.this, RoomStatusScreen.class);
                            intent.putExtra("Username", userName);
                            intent.putExtra("RoomId", roomId);
                            intent.putExtra("RoomStatus", Constants.I_START_STATUS);
                            startActivity(intent);


                        } else {

                            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }
                    }

                    @Override
                    public void onFailure(Call<RoomInfo> call, Throwable t) {
                        Log.d("f", t.getMessage());
                    }

                });


                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }
        });

        CreatePrivateRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RoomScreen.this, CreatePrivateRoomScreen.class);
                intent.putExtra("Username", userName);
                startActivity(intent);


            }
        });

        JoinPublicRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RoomScreen.this, JoinPublicRoomScreen.class);
                intent.putExtra("Username", userName);
                startActivity(intent);


            }
        });

        JoinPrivateRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RoomScreen.this, JoinPrivateRoomScreen.class);
                intent.putExtra("Username", userName);
                startActivity(intent);


            }
        });

        JoinRandomRoomBtn.setOnClickListener(new View.OnClickListener() {
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
                        //String status = response.body().getRoomStatus();
                        String roomId = response.body().getRoomId();


                        if(message.equals(Constants.OK_MESSAGE)) {


                                Intent intent = new Intent(RoomScreen.this, RoomStatusScreen.class);
                                intent.putExtra("Username", userName);
                                intent.putExtra("RoomId", roomId);
                                intent.putExtra("RoomStatus", "Start");
                                startActivity(intent);


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
