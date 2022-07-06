package com.auction.virtualauctionclient.auctionscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.RoomInfo;
import com.auction.virtualauctionclient.model.RoomStatus;
import com.auction.virtualauctionclient.model.Team;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionBreakStatusThread implements Runnable{

    public boolean checkStatus = true;
    private Context context;
    private Context contextForFinish;
    private String roomId;
    private String teamName;
    private String userName;
    private int maxForeigners;
    private int minTotal;
    private int maxTotal;
    private int maxBudget;
    private TextView HostEdit, MinBreakTime;
    private Button ContinueBtn;

    public AuctionBreakStatusThread(TextView HostEdit, TextView MinBreakTime, Button ContinueBtn, String roomId, String teamName, String userName, int maxForeigners, int minTotal, int maxTotal, int maxBudget, Context context, Context contextForFinish) {
        this.HostEdit = HostEdit;
        this.MinBreakTime = MinBreakTime;
        this.ContinueBtn = ContinueBtn;
        this.roomId = roomId;
        this.teamName = teamName;
        this.userName = userName;
        this.maxForeigners = maxForeigners;
        this.minTotal = minTotal;
        this.maxTotal = maxTotal;
        this.maxBudget = maxBudget;
        this.context = context;
        this.contextForFinish = contextForFinish;
    }
    @Override
    public void run() {

        final String[] status = {""};

        while(checkStatus) {

            RoomInfo roomInfo = new Team();
            roomInfo.setRoomId(roomId);


            try {

                (Client.getClient().auctionBreakStatus("application/json", roomInfo)).enqueue(new Callback<RoomStatus>() {
                    @Override
                    public void onResponse(Call<RoomStatus> call, Response<RoomStatus> response) {
                        //Log.d("responseBodyGET", response.body().toString());

                        status[0] = response.body().getRoomStatus();
                        int minBreakTime = response.body().getAuctionBreakMinTime();

                        long minutes = (minBreakTime / 1000) / 60;
                        long seconds = (minBreakTime / 1000) % 60;
                        MinBreakTime.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));

                        if (status[0].equals(Constants.I_ONGOING_STATUS)) {

                            checkStatus = false;

                        } else {

                            if (response.body().getHostName().equals(userName)) {

                                if(minBreakTime==0) {

                                    ContinueBtn.setEnabled(true);
                                } else {

                                    ContinueBtn.setEnabled(false);

                                }
                                HostEdit.setText("Host");
                            } else {
                                ContinueBtn.setEnabled(false);
                                HostEdit.setText("");
                            }
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
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        if(status[0].equals(Constants.I_ONGOING_STATUS)) {

            Intent intent = new Intent(context, AuctionScreen.class);
            intent.putExtra("Username", userName);
            intent.putExtra("RoomId", roomId);
            intent.putExtra("Team", teamName);
            intent.putExtra("MaxForeigners", maxForeigners);
            intent.putExtra("MinTotal", minTotal);
            intent.putExtra("MaxTotal", maxTotal);
            intent.putExtra("MaxBudget", maxBudget);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            ((Activity)contextForFinish).finish();
        }
    }
}
