package com.auction.virtualauctionclient.room;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.auctionscreen.AuctionBreakScreen;
import com.auction.virtualauctionclient.auctionscreen.TotalPlayersListScreen;
import com.auction.virtualauctionclient.auctionscreen.UnsoldPlayersListScreen;
import com.auction.virtualauctionclient.common.CommonApiLogic;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.RoomInfo;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomStatusScreen extends AppCompatActivity
{
    private RoomStatusThread roomStatusThread;
    private Button SettingsBtn, StartBtn, PlayersBtn, LeaveBtn, QuitBtn;
    private Spinner SelectTeam;
    private TextView HostText, roomIdEdit, usernamesList, teamsList;
    static ArrayList<String> usernamesArrList;
    static ArrayList<String> teamsArrList;
    private TableLayout usernamesTable;
    private String userName, roomId;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_status_screen);
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString("Username");
        roomId = bundle.getString("RoomId");
        String status = bundle.getString("RoomStatus");

        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(Constants.ACCOUNT_BACKGROUND, this.findViewById(android.R.id.content), assetManager);

        SettingsBtn = findViewById(R.id.settings_button);
        StartBtn = findViewById(R.id.start_button);
        LeaveBtn = findViewById(R.id.leave_button);
        QuitBtn =  findViewById(R.id.quit_button);
        SelectTeam =  findViewById(R.id.SelectTeamEdit);
        PlayersBtn = findViewById(R.id.players_button);
        HostText =  findViewById(R.id.HostText);
        roomIdEdit =  findViewById(R.id.RoomIdEdit);
        usernamesTable = (TableLayout)findViewById(R.id.tableL);

        roomIdEdit.setText("Room Id : " + roomId);

        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, SettingsBtn);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, StartBtn);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, LeaveBtn);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, QuitBtn);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, PlayersBtn);

        final Context context = RoomStatusScreen.this.getApplicationContext();
        final Context contextForFinish = RoomStatusScreen.this;

        try {
            Drawable d = Drawable.createFromStream(assetManager.open("other/settings.png"), null);
            SettingsBtn.setBackground(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        roomStatusThread = new RoomStatusThread(usernamesList,teamsList, HostText, SelectTeam, StartBtn,SettingsBtn, userName, roomId, context, contextForFinish, usernamesTable);
        Thread thread = new Thread(roomStatusThread);
        thread.start();

        //mAdapter = new RoomStatusListAdapter(context, usernamesArrList, teamsArrList, listview);
        //listview.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();

        SettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(RoomStatusScreen.this, AuctionSettingsScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                intent.putExtra("RoomStatus", status);
                intent.putExtra("Host", HostText.getText().toString());
                // start the activity connect to the specified class
                startActivity(intent);


            }
        });

        // below line is to add on click listener for our add course button.
        StartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    //roomStatusThread.continueThread = false;
                    RoomInfo roomInfo = new RoomInfo();
                    roomInfo.setUsername(userName);
                    roomInfo.setRoomId(roomId);

                    (Client.getClient().startAuction("application/json", roomInfo)).enqueue(new Callback<ResponseMessage>() {
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

        PlayersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent intent = new Intent(RoomStatusScreen.this, TotalPlayersListScreen.class);
                    intent.putExtra("Username", userName);
                    intent.putExtra("RoomId", roomId);
                    startActivity(intent);



            }
        });

        LeaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    RoomInfo roomInfo = new RoomInfo();
                    roomInfo.setUsername(userName);
                    roomInfo.setRoomId(roomId);

                String message = CommonApiLogic.leaveRoomApi(roomInfo);

                    roomStatusThread.continueThread = false;
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

                if(message.equals(Constants.OK_MESSAGE)) {

                    finish();
                } else {

                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }


            }
        });



    }

    @Override
    public void onBackPressed() {

        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setUsername(userName);
        roomInfo.setRoomId(roomId);
        CommonApiLogic.leaveRoomApi(roomInfo);
        roomStatusThread.continueThread = false;
        finish();

    }

}


