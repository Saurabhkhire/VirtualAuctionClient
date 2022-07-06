package com.auction.virtualauctionclient.auctionscreen;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonApiLogic;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.RoomInfo;
import com.auction.virtualauctionclient.model.RoomStatus;
import com.auction.virtualauctionclient.room.GameScreen;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsScreen extends AppCompatActivity  {

    private Button LeaveBtn,QuitBtn, AuctionParamsBtn,CurrentSetPlayerBtn, SkipAllBtn, UnskipSetBtn, SkipThisSetBtn, PauseBtn, PlayBtn, ChangeHostBtn;

   // public SettingsScreen(Context contextForFinish) {
       //this.contextForFinish = contextForFinish;
    //}

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("Username");
        String roomId = bundle.getString("RoomId");
        String host = bundle.getString("Host");
        String team = bundle.getString("Team");
        int maxForeigners = bundle.getInt("MaxForeigners");
        int minTotal = bundle.getInt("MinTotal");
        int maxTotal = bundle.getInt("MaxTotal");
        int maxBudget = bundle.getInt("MaxBudget");

        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(team + Constants.PNG_EXT, this.findViewById(android.R.id.content), assetManager);

        LeaveBtn = findViewById(R.id.leave_button);
        QuitBtn = findViewById(R.id.quit_button);
        AuctionParamsBtn = findViewById(R.id.auction_params_button);
        CurrentSetPlayerBtn = findViewById(R.id.current_set_players_button);
        SkipAllBtn = findViewById(R.id.skip_all_button);
        SkipThisSetBtn = findViewById(R.id.skip_this_set_button);
        UnskipSetBtn = findViewById(R.id.unskip_set_button);
        PauseBtn = findViewById(R.id.pause_button);
        PlayBtn = findViewById(R.id.play_button);
        ChangeHostBtn = findViewById(R.id.change_host_button);
        //TeamsBtn = findViewById(R.id.teams_button);

        CommonLogic.setBackgroundForButtons(team, LeaveBtn);
        CommonLogic.setBackgroundForButtons(team, QuitBtn);
        CommonLogic.setBackgroundForButtons(team, AuctionParamsBtn);
        CommonLogic.setBackgroundForButtons(team, CurrentSetPlayerBtn);
        CommonLogic.setBackgroundForButtons(team, SkipAllBtn);
        CommonLogic.setBackgroundForButtons(team, SkipThisSetBtn);
        CommonLogic.setBackgroundForButtons(team, UnskipSetBtn);
        CommonLogic.setBackgroundForButtons(team, PauseBtn);
        CommonLogic.setBackgroundForButtons(team, PlayBtn);
        CommonLogic.setBackgroundForButtons(team, ChangeHostBtn);
        //CommonLogic.setBackgroundForButtons(teamStr, TeamsBtn);

        if(host.equals("")) {
            SkipAllBtn.setVisibility(View.INVISIBLE);
            SkipThisSetBtn.setVisibility(View.INVISIBLE);
            UnskipSetBtn.setVisibility(View.INVISIBLE);
            PauseBtn.setVisibility(View.INVISIBLE);
            PlayBtn.setVisibility(View.INVISIBLE);
            ChangeHostBtn.setVisibility(View.INVISIBLE);
        }

        LeaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomId(roomId);

                String message = CommonApiLogic.leaveRoomApi(roomInfo);



                finish();
                AuctionScreen.activity.finish();


            }
        });

        QuitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomId(roomId);

                String message = CommonApiLogic.quitRoomApi(roomInfo);

                finish();
                AuctionScreen.activity.finish();
            }
        });

        ChangeHostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsScreen.this, UsernamesListScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                intent.putExtra("Team",team);
                startActivity(intent);

            }
        });

        SkipAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    RoomStatus roomStatus = new RoomStatus();
                    roomStatus.setUsername(userName);
                    roomStatus.setRoomId(roomId);
                    roomStatus.setSkipType(Constants.I_SKIP_ENTIRE_ROUND);

                    (Client.getClient().skipSets("application/json", roomStatus)).enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                            //Log.d("responseBodyGET", response.body().toString());
                            String message = response.body().getMessage();

                           // if(!message.equals(Constants.OK_MESSAGE)) {
                                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                           // }

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

        SkipThisSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    RoomStatus roomStatus = new RoomStatus();
                    roomStatus.setUsername(userName);
                    roomStatus.setRoomId(roomId);
                    roomStatus.setSkipType(Constants.I_SKIP_CURRENT_SET);

                    (Client.getClient().skipSets("application/json", roomStatus)).enqueue(new Callback<ResponseMessage>() {
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

        UnskipSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    RoomStatus roomStatus = new RoomStatus();
                    roomStatus.setUsername(userName);
                    roomStatus.setRoomId(roomId);
                    roomStatus.setSkipType(Constants.I_UNSKIP);

                    (Client.getClient().skipSets("application/json", roomStatus)).enqueue(new Callback<ResponseMessage>() {
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

        PauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomId(roomId);

                try {

                    (Client.getClient().pauseAuction("application/json", roomInfo)).enqueue(new Callback<ResponseMessage>() {
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

        PlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userName);
                roomInfo.setRoomId(roomId);

                try {

                    (Client.getClient().playAuction("application/json", roomInfo)).enqueue(new Callback<ResponseMessage>() {
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

        CurrentSetPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsScreen.this, CurrentSetPlayersListScreen.class);
                intent.putExtra("Username", userName);
                intent.putExtra("RoomId", roomId);
                startActivity(intent);


            }
        });

        AuctionParamsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsScreen.this, AuctionParamsScreen.class);
                intent.putExtra("Team", team);
                intent.putExtra(Constants.I_MAX_FOREIGNERS, maxForeigners);
                intent.putExtra(Constants.I_MIN_TOTAL, minTotal);
                intent.putExtra(Constants.I_MAX_TOTAL, maxTotal);
                intent.putExtra(Constants.I_MAX_BUDGET, maxBudget);
                startActivity(intent);


            }
        });

    }
    @Override
    public void onBackPressed() {

        finish();

    }
}
