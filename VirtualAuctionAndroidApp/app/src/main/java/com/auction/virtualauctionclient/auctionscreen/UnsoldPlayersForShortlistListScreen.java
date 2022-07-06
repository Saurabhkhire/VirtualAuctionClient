package com.auction.virtualauctionclient.auctionscreen;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonApiLogic;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.NamesList;
import com.auction.virtualauctionclient.model.PlayerName;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.RoomInfo;
import com.auction.virtualauctionclient.model.Team;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnsoldPlayersForShortlistListScreen extends AppCompatActivity {

    private Button addBtn;
    private ListView mListview;
    private ArrayList<String> playerNamesList;
    private ArrayList<String> priceList;
    private ArrayList<String> modelArrayList;
    private UnsoldPlayersForShortlistListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unsold_player_names_for_shortlist_list_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("Username");
        String roomId = bundle.getString("RoomId");
        String teamName = bundle.getString("Team");
        String status = bundle.getString("RoomStatus");

        addBtn = findViewById(R.id.add_button);

        if(status.equals(Constants.I_FINISHED_STATUS)) {
            addBtn.setText("Refresh");

        }
        //mListview = findViewById(R.id.UnsoldPlayerNamesList);


        Team team = new Team();
        team.setUsername(userName);
        team.setRoomId(roomId);
        team.setTeam(teamName);
        (Client.getClient().getUnsoldPlayers("application/json", team)).enqueue(new Callback<NamesList>() {
            @Override
            public void onResponse(Call<NamesList> call, Response<NamesList> response) {

                playerNamesList = response.body().getNamesList();
                priceList = response.body().getPriceList();

                TableLayout prices = (TableLayout)findViewById(R.id.tableL);
                CommonLogic.addPlayersTable(playerNamesList, priceList, prices, UnsoldPlayersForShortlistListScreen.this, userName, roomId, teamName);

                // mAdapter = new UnsoldPlayersForShortlistListAdapter(UnsoldPlayersForShortlistListScreen.this, mArrData, addBtn, userName, roomId, mListview);
               // mListview.setAdapter(mAdapter);
               // mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NamesList> call, Throwable t) {
                Log.d("f", t.getMessage());
            }

        });



        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    NamesList namesList = new NamesList();
                    namesList.setUsername(userName);
                    namesList.setRoomId(roomId);
                    namesList.setNamesList(CommonLogic.list());
                    (Client.getClient().addUnsoldPlayers("application/json", namesList)).enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                            //modelArrayList.clear();

                        }

                        @Override
                        public void onFailure(Call<ResponseMessage> call, Throwable t) {
                            Log.d("f", t.getMessage());
                        }

                    });
                }
        });

    }
    @Override
    public void onBackPressed() {

        finish();

    }
}