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
import com.auction.virtualauctionclient.room.RoomStatusScreen;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePrivateRoomScreen extends AppCompatActivity {

    private Button CreateRoomBtn;
    private EditText PasswordEdit;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_private_room_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString(Constants.I_USERNAME);

        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(Constants.GAME_BACKGROUND, this.findViewById(android.R.id.content), assetManager);

        CreateRoomBtn = findViewById(R.id.create_button);
        PasswordEdit = findViewById(R.id.PasswordEdit);

        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, CreateRoomBtn);

        // below line is to add on click listener for our add course button.
        CreateRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomPassword(PasswordEdit.getText().toString());
                roomInfo.setVisibility("Private");

                try {
                (Client.getClient().createRoom("application/json", roomInfo)).enqueue(new Callback<RoomInfo>() {
                    @Override
                    public void onResponse(Call<RoomInfo> call, Response<RoomInfo> response) {

                        String message = response.body().getMessage();
                        String roomId = response.body().getRoomId();

                        if(message.equals(Constants.OK_MESSAGE)) {

                            Intent intent = new Intent(CreatePrivateRoomScreen.this, RoomStatusScreen.class);
                            intent.putExtra("Username", userName);
                            intent.putExtra("RoomId", roomId);
                            intent.putExtra("RoomStatus", Constants.I_START_STATUS);
                            startActivity(intent);
                            finish();

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



    }
    @Override
    public void onBackPressed() {

        finish();

    }
}
