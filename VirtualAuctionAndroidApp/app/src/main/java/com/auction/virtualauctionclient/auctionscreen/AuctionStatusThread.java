package com.auction.virtualauctionclient.auctionscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.PlayerStatus;
import com.auction.virtualauctionclient.model.Team;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionStatusThread implements Runnable{

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
    private ProgressBar LoadingBar;
    private Button BidBtn;
    private TextView SetNameEdit, PlayerNameTxt, PlayerCountryTxt, PlayerRoleTxt, BattingStyleTxt, BowlingStyleTxt, BattingPositionTxt, PriceTxt, TimeTxt, CurrentBudgetEdit, HostEdit, StatusEdit, PausedEdit;
    private ImageView CurrentBid, TeamNameTxt, PlayerImage1, PlayerImage2;
    private AssetManager assetManager;

    public AuctionStatusThread(ImageView TeamNameTxt, TextView SetNameEdit, TextView PlayerNameTxt, TextView PlayerCountryTxt, TextView PlayerRoleTxt, TextView BattingStyleTxt, TextView BowlingStyleTxt, TextView BattingPositionTxt, TextView PriceTxt, ImageView CurrentBid, TextView TimeTxt, TextView CurrentBudgetEdit, TextView HostEdit, TextView StatusEdit, TextView PausedEdit, ImageView PlayerImage1, ImageView PlayerImage2, Button BidBtn, String roomId, String teamName, String userName, int maxForeigners, int minTotal, int maxTotal, int maxBudget, AssetManager assetManager, Context context, Context contextForFinish, ProgressBar LoadingBar) {
        this.TeamNameTxt = TeamNameTxt;
        this.SetNameEdit = SetNameEdit;
        this.PlayerNameTxt = PlayerNameTxt;
        this.PlayerCountryTxt = PlayerCountryTxt;
        this.PlayerRoleTxt = PlayerRoleTxt;
        this.BattingStyleTxt = BattingStyleTxt;
        this.BowlingStyleTxt = BowlingStyleTxt;
        this.BattingPositionTxt = BattingPositionTxt;
        this.PriceTxt = PriceTxt;
        this.CurrentBid = CurrentBid;
        this.TimeTxt = TimeTxt;
        this.CurrentBudgetEdit = CurrentBudgetEdit;
        this.HostEdit = HostEdit;
        this.StatusEdit = StatusEdit;
        this.PausedEdit = PausedEdit;
        this.PlayerImage1 = PlayerImage1;
        this.PlayerImage2 = PlayerImage2;
        this.BidBtn = BidBtn;
        this.roomId = roomId;
        this.teamName = teamName;
        this.userName = userName;
        this.maxForeigners = maxForeigners;
        this.minTotal = minTotal;
        this.maxTotal = maxTotal;
        this.maxBudget = maxBudget;
        this.assetManager = assetManager;
        this.context = context;
        this.contextForFinish = contextForFinish;
        this.LoadingBar = LoadingBar;
    }
    @Override
    public void run() {

        final String[] status = {""};

        while(checkStatus) {

            Team team = new Team();
            team.setUsername(userName);
            team.setRoomId(roomId);
            team.setTeam(teamName);

            try {

                (Client.getClient().info("application/json", team)).enqueue(new Callback<PlayerStatus>() {
                    @Override
                    public void onResponse(Call<PlayerStatus> call, Response<PlayerStatus> response) {
                        //Log.d("responseBodyGET", response.body().toString());

                        status[0] = response.body().getRoomStatus();

                        if (status[0].equals(Constants.I_WAITING_FOR_ROUND2) || status[0].equals(Constants.I_WAITING_FOR_ROUND3) || status[0].equals(Constants.I_FINISHED_STATUS)) {

                            checkStatus = false;

                        } else {

                            int price = response.body().getTotalPriceinLakhs();
                            String priceStr = String.valueOf(price);
                            double priceInCrores = response.body().getTotalPriceInCrores();
                            String priceInCroresStr = String.format("%.2f", priceInCrores);
                            int time = response.body().getTime();
                            String timeStr = String.valueOf(time);

                            int batsman = response.body().getBatsman();
                            String batsmanStr = String.valueOf(batsman);
                            int wicketKeepers = response.body().getWicketKeepers();
                            String wicketKeepersStr = String.valueOf(wicketKeepers);
                            int allRounders = response.body().getAllRounders();
                            String allRoundersStr = String.valueOf(allRounders);
                            int fastBowlers = response.body().getFastBowlers();
                            String fastBowlersStr = String.valueOf(fastBowlers);
                            int spinBowlers = response.body().getSpinBowlers();
                            String spinBowlersStr = String.valueOf(spinBowlers);
                            int foreigners = response.body().getForeigners();
                            String foreignersStr = String.valueOf(foreigners);
                            int total = response.body().getTotal();
                            String totalStr = String.valueOf(total);
                            int budget = response.body().getBudgetInLakhs();
                            String budgetStr = String.valueOf(budget);
                            double budgetInCrores = response.body().getBudgetInCrores();
                            String budgetInCroresStr =  String.format("%.2f", budgetInCrores);

                            String playerCountry = response.body().getPlayerCountry();
                            String playerRole = response.body().getPlayerRole();
                            String skip = response.body().getSkipType();


                            if(response.body().getHostName().equals(userName)) {
                                HostEdit.setText("Host");
                            }
                            if(status[0].equals(Constants.I_PAUSED_STATUS)) {
                                PausedEdit.setText("Paused");
                            } else {
                                PausedEdit.setText("");
                            }
                            SetNameEdit.setText(response.body().getSet().replaceAll("_"," "));
                            PlayerNameTxt.setText(response.body().getPlayerName());
                            PlayerCountryTxt.setText(playerCountry);
                            PlayerRoleTxt.setText(playerRole);
                            BattingStyleTxt.setText(response.body().getBattingStyle());
                            BowlingStyleTxt.setText(response.body().getBowlingStyle());
                            BattingPositionTxt.setText(response.body().getBattingPosition());
                            PriceTxt.setText(priceStr + " lakhs \n(" + priceInCroresStr + " crores)");
                            //CurrentBid.setText(response.body().getTeam());
                            if(response.body().getTeam().equals("") || response.body().getTeam() == null) {
                                CurrentBid.setImageDrawable(null);
                            } else {
                                CommonLogic.setTeamImage(response.body().getTeam(), CurrentBid, contextForFinish);
                            }

                            if(time ==0) {
                                if (response.body().getTeam().equals("") || response.body().getTeam() == null) {
                                    StatusEdit.setText("Last Bid : " + response.body().getPlayerName() + " Unsold");
                                } else {
                                    StatusEdit.setText("Last Bid : " + response.body().getPlayerName() + " sold to " + response.body().getTeam().toUpperCase() + " for " + priceStr + " lakhs");
                                }
                            }

                            TimeTxt.setText(timeStr);
                            CurrentBudgetEdit.setText("Budget: " + budgetStr  + " lakhs (" + budgetInCroresStr + " crores)");
                            if(response.body().getPlayerImage1Uri().equals("") || response.body().getPlayerImage1Uri() == null) {
                                PlayerImage1.setImageDrawable(null);
                            } else {
                                CommonLogic.setPlayerImage(response.body().getPlayerImage1Uri(), PlayerImage1, contextForFinish);
                            }
                            if(response.body().getPlayerImage2Uri().equals("") || response.body().getPlayerImage2Uri() == null) {
                                PlayerImage2.setImageDrawable(null);
                            } else {
                                CommonLogic.setPlayerImage(response.body().getPlayerImage2Uri(), PlayerImage2, contextForFinish);
                            }

                            boolean maxForeignCheck = !playerCountry.equals("India") && foreigners >= maxForeigners;
                            //boolean minBatsmanCheck = !playerRole.equals("Batsman") && batsman == 0 && total == maxTotal -1;
                            //boolean minKeeperCheck = !playerRole.equals("Wicket Keeper") && wicketKeepers == 0 && total == maxTotal -1;
                            //boolean minAllRounderCheck = !playerRole.equals("All Rounder") && allRounders == 0 && total == maxTotal -1;
                            //boolean minFastBowlerCheck = !playerRole.equals("Fast Bowler") && fastBowlers == 0 && total == maxTotal -1;
                            //boolean minSpinBowlerCheck = !playerRole.equals("Spin Bowler") && spinBowlers == 0 && total == maxTotal -1;
                            boolean playerAccordingToBudgetCheck = (budget - price) < (minTotal - total - 1);
                            boolean playerAccordingToBudgetCheckTeam = (budget - price) == (minTotal - total - 1) && (!response.body().getTeam().equals(""));
                            boolean skipCheck = (skip.equals(Constants.I_SKIP_CURRENT_SET) && time==0) || (skip.equals(Constants.I_SKIP_ENTIRE_ROUND)  && time==0);

                            if (time == 0 || price > budget || maxForeignCheck || total >= maxTotal || playerAccordingToBudgetCheck || playerAccordingToBudgetCheckTeam) {
                                BidBtn.setEnabled(false);
                            } else {
                                BidBtn.setEnabled(true);
                            }

                                if (skipCheck) {
                                    LoadingBar.setVisibility(View.VISIBLE);
                                } else {
                                    LoadingBar.setVisibility(View.INVISIBLE);
                                }

                        }

                    }



                    @Override
                    public void onFailure(Call<PlayerStatus> call, Throwable t) {
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

        if(status[0].equals(Constants.I_WAITING_FOR_ROUND2) || status[0].equals(Constants.I_WAITING_FOR_ROUND3) || status[0].equals(Constants.I_FINISHED_STATUS)) {

            Intent intent = new Intent(context, AuctionBreakScreen.class);
            intent.putExtra("Username", userName);
            intent.putExtra("RoomId", roomId);
            intent.putExtra("Team", teamName);
            intent.putExtra("MaxForeigners", maxForeigners);
            intent.putExtra("MinTotal", minTotal);
            intent.putExtra("MaxTotal", maxTotal);
            intent.putExtra("MaxBudget", maxBudget);
            intent.putExtra("RoomStatus", status[0]);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            ((Activity)contextForFinish).finish();
        }
    }
}
