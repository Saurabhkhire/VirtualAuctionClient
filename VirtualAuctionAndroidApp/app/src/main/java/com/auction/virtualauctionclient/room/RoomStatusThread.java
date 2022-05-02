package com.auction.virtualauctionclient.room;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.auctionscreen.AuctionScreen;
import com.auction.virtualauctionclient.model.RoomInfo;
import com.auction.virtualauctionclient.model.RoomStatusResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomStatusThread implements Runnable {

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

    public RoomStatusThread(TextView UsernamesList,TextView TeamsList, TextView HostText,Spinner SelectTeam, Button StartBtn,Button SettingsBtn, String userName, String roomId, Context context, Context contextForFinish) {
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
                        //Log.d("responseBodyGET", response.body().toString());
                        status[0] = response.body().getRoomStatus();

                        if(!status[0].equals("Start")) {
                            SettingsBtn.setVisibility(View.INVISIBLE);
                        }

                        if (status[0].equals("Start") || status[0].equals("Paused")) {

                            String usernameList = response.body().getUsernameslist();
                            String teamsList = response.body().getTeamslist();
                            String host = response.body().getHost();

                            String[] splitUsernameList = usernameList.split(",");
                            String[] splitTeamList = teamsList.split(",");



                            String usernames = "Usernames \n";
                            String teams = "Teams \n";

                            if (usernameList != null) {

                                for (int i = 0; i < splitUsernameList.length; i++) {
                                    if (splitUsernameList[i].equals(host)) {
                                        usernames = usernames + splitUsernameList[i] + "(Host) \n";
                                        if (splitTeamList[i].equals("") || splitTeamList[i] == null || splitTeamList[i].equals("NA")) {
                                            teams = teams + "\n";
                                        } else {
                                            teams = teams + splitTeamList[i] + "\n";
                                        }
                                    } else {
                                        usernames = usernames + splitUsernameList[i] + "\n";
                                        if (splitTeamList[i].equals("") || splitTeamList[i] == null || splitTeamList[i].equals("NA")) {
                                            teams = teams + "\n";
                                        } else {
                                            teams = teams + splitTeamList[i] + "\n";
                                        }
                                    }

                                }

                                UsernamesList.setText(usernames);
                                TeamsList.setText(teams);

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

        if(status[0].equals("Ongoing") || status[0].equals("TempPaused") || status[0].equals("TempPausedForRound2") || status[0].equals("TempPausedForRound3")) {

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
