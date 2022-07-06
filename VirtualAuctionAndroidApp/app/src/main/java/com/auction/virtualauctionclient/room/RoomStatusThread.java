package com.auction.virtualauctionclient.room;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.auctionscreen.AuctionScreen;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.RoomInfo;
import com.auction.virtualauctionclient.model.RoomStatusResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomStatusThread implements Runnable {

    private TableLayout usernamesTable;
    boolean continueThread=true;
    private Context context;
    private Context contextForFinish;
    private Button StartBtn, SettingsBtn;
    private Spinner SelectTeam;
    private TextView HostText;
    private TextView UsernamesList;
    private TextView TeamsList;
    String userName;
    String roomId;

    public RoomStatusThread(TextView UsernamesList,TextView TeamsList, TextView HostText,Spinner SelectTeam, Button StartBtn,Button SettingsBtn, String userName, String roomId, Context context, Context contextForFinish, TableLayout usernamesTable) {
        this.UsernamesList = UsernamesList;
        this.TeamsList = TeamsList;
        this.SelectTeam = SelectTeam;
        this.StartBtn = StartBtn;
        this.SettingsBtn = SettingsBtn;
        this.userName = userName;
        this.roomId = roomId;
        this.HostText = HostText;
        this.context = context;
        this.contextForFinish = contextForFinish;
        this.usernamesTable = usernamesTable;
    }
    @Override
    public void run() {

        final String[] status = {"","","","","",""};

        while(continueThread) {



            try {

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomId(roomId);

                (Client.getClient().roomStatus("application/json", roomInfo)).enqueue(new Callback<RoomStatusResponse>() {
                    @Override
                    public void onResponse(Call<RoomStatusResponse> call, Response<RoomStatusResponse> response) {

                        String message = response.body().getMessage();
                        status[0] = response.body().getRoomStatus();

                        if(message.equals(Constants.OK_MESSAGE)) {

                        if(!status[0].equals(Constants.I_START_STATUS)) {
                            SettingsBtn.setVisibility(View.INVISIBLE);
                        }

                        if (status[0].equals(Constants.I_START_STATUS) || status[0].equals(Constants.I_HALT_STATUS)) {

                            String usernameList = "Username," + response.body().getUsernameslist();
                            String teamsList = "Team," + response.body().getTeamslist();
                            String host = response.body().getHost();

                            String[] splitUsernameList = usernameList.split(",");
                            String[] splitTeamList = teamsList.split(",");


                            if (usernameList != null) {

                                usernamesTable.removeAllViews();

                                //modelArrayList = new ArrayList<>();
                                GradientDrawable border = new GradientDrawable();
                                border.setColor(0xFF000000); //white background
                                border.setStroke(1, 0xFFFFFFFF); //black border with full opacity
                                usernamesTable.setBackground(border);
                                for(int i = 0; i < splitUsernameList.length; i++) {
                                    TableRow tr = new TableRow(context);
                                    //tr.setMinimumHeight(30);
                                    //tr.setBackgroundColor(Color.BLACK);
                                    TextView c1 = new TextView(context);
                                    String username = splitUsernameList[i];
                                    if(username.equals(host)) {
                                        username = username + " (Host) ";
                                    }
                                    c1.setText(username);
                                    c1.setGravity(Gravity.CENTER);
                                    c1.setBackground(border);
                                    c1.setHeight(50);
                                    c1.setWidth(380);
                                    c1.setTextColor(Color.WHITE);
                                    TextView c2 = new TextView(context);
                                    String team = splitTeamList[i];
                                    if (team.equals("") || team.equals(Constants.I_NA)) {
                                        team = "";
                                    }
                                    c2.setText(team);
                                    c2.setGravity(Gravity.CENTER);
                                    c2.setBackground(border);
                                    c2.setHeight(50);
                                    c2.setWidth(380);
                                    c2.setTextColor(Color.WHITE);
                                    tr.addView(c1);
                                    tr.addView(c2);

                                    usernamesTable.addView(tr);
                                }



                                //UsernamesList.setText(usernames);
                                //TeamsList.setText(teams);

                                if (host.equals(userName)) {
                                    HostText.setText("Host");
                                    StartBtn.setVisibility(View.VISIBLE);
                                } else {
                                    HostText.setText("");
                                    StartBtn.setVisibility(View.INVISIBLE);
                                }
                            }
                        } else {

                            status[1] = response.body().getTeam();
                            status[2] = String.valueOf(response.body().getMaxForeigners());
                            status[3] = String.valueOf(response.body().getMinTotal());
                            status[4] = String.valueOf(response.body().getMaxTotal());
                            status[5] = String.valueOf(response.body().getMaxBudget());
                            continueThread = false;

                        }
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


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        if(status[0].equals(Constants.I_ONGOING_STATUS) || status[0].equals(Constants.I_PAUSED_STATUS) || status[0].equals(Constants.I_WAITING_FOR_ROUND2) || status[0].equals(Constants.I_WAITING_FOR_ROUND3)) {

            Intent intent = new Intent(context, AuctionScreen.class);
            intent.putExtra("Username", userName);
            intent.putExtra("RoomId", roomId);
            intent.putExtra("Team", status[1]);
            intent.putExtra("MaxForeigners", Integer.parseInt(status[2]));
            intent.putExtra("MinTotal", Integer.parseInt(status[3]));
            intent.putExtra("MaxTotal", Integer.parseInt(status[4]));
            intent.putExtra("MaxBudget", Integer.parseInt(status[5]));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            ((Activity)contextForFinish).finish();

        }
    }

}
