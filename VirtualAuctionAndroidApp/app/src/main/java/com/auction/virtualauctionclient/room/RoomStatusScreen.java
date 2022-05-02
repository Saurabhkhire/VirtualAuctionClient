package com.auction.virtualauctionclient.room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonApiLogic;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.RoomInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomStatusScreen extends AppCompatActivity
{

    private Button SettingsBtn, StartBtn, LeaveBtn, QuitBtn;
    private Spinner SelectTeam;
    private TextView HostText, roomIdEdit, usernamesList, teamsList;
    static ArrayList<String> usernamesArrList;
    static ArrayList<String> teamsArrList;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_status_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("Username");
        String roomId = bundle.getString("RoomId");
        String status = bundle.getString("Status");

        SettingsBtn = findViewById(R.id.settings_button);
        StartBtn = findViewById(R.id.start_button);
        LeaveBtn = findViewById(R.id.leave_button);
        QuitBtn =  findViewById(R.id.quit_button);
        SelectTeam =  findViewById(R.id.SelectTeamSpinner);
        usernamesList =  findViewById(R.id.ListViewofUsers);
        teamsList =  findViewById(R.id.ListViewofTeams);
        HostText =  findViewById(R.id.HostText);
        roomIdEdit =  findViewById(R.id.RoomIdEdit);


        roomIdEdit.setText(roomId);

        final Context context = RoomStatusScreen.this.getApplicationContext();
        final Context contextForFinish = RoomStatusScreen.this;

        RoomStatusThread roomStatusThread = new RoomStatusThread(usernamesList,teamsList, HostText, SelectTeam, StartBtn,SettingsBtn, userName, roomId, context, contextForFinish);
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
                intent.putExtra("Status", status);
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

        LeaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    RoomInfo roomInfo = new RoomInfo();
                    roomInfo.setUsername(userName);
                    roomInfo.setRoomId(roomId);

                String message = CommonApiLogic.leaveRoomApi(roomInfo);

                if(!message.equals("Error")) {
                    //Intent intent = new Intent(RoomStatusScreen.this, RoomScreen.class);
                    //startActivity(intent);
                    roomStatusThread.continueThread = false;
                    finish();
                }


            }
        });

        QuitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomId(roomId);

                String message = CommonApiLogic.quitRoomApi(roomInfo);

                if(!message.equals("Error")) {
                    //Intent intent = new Intent(RoomStatusScreen.this, RoomScreen.class);
                    //startActivity(intent);

                    finish();
                }


            }
        });



    }



}


