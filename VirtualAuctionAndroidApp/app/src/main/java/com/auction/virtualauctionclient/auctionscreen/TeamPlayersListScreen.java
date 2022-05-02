package com.auction.virtualauctionclient.auctionscreen;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.model.PlayerInfoList;
import com.auction.virtualauctionclient.model.Team;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamPlayersListScreen extends AppCompatActivity {

    private ListView mListview;
    private ArrayList<String> playerNamesList;
    private ArrayList<String> playerCountryList;
    private ArrayList<String> playerRoleList;
    private ArrayList<String> battingStyleList;
    private ArrayList<String> bowlingStyleList;
    private ArrayList<String> battingPositionList;
    private ArrayList<String> totalPriceInLakhsList;
    private ArrayList<String> totalPriceInCroresList;
    private UnsoldPlayersListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_players_list_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("Username");
        String roomId = bundle.getString("RoomId");
        String team = bundle.getString("Team");

        mListview = findViewById(R.id.ListViewofTeamPlayers);

        Team teamName = new Team();
        teamName.setUsername(userName);
        teamName.setRoomId(roomId);
        teamName.setTeam(team);
        (Client.getClient().getTeamPlayersList("application/json", teamName)).enqueue(new Callback<PlayerInfoList>() {
            @Override
            public void onResponse(Call<PlayerInfoList> call, Response<PlayerInfoList> response) {

                playerNamesList = response.body().getPlayerNameList();
                playerCountryList = response.body().getPlayerCountryList();
                playerRoleList = response.body().getPlayerRoleList();
                battingStyleList = response.body().getBattingStyleList();
                bowlingStyleList = response.body().getBowlingStyleList();
                battingPositionList = response.body().getBattingPositionList();
                totalPriceInLakhsList = response.body().getPriceinLakhsList();
                totalPriceInCroresList = response.body().getPriceinCroresList();
                mAdapter = new UnsoldPlayersListAdapter(TeamPlayersListScreen.this, playerNamesList, playerCountryList, playerRoleList, battingStyleList, bowlingStyleList, battingPositionList, totalPriceInLakhsList, totalPriceInCroresList, mListview);
                mListview.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                TextView teamNameTxt = new TextView(TeamPlayersListScreen.this);
                teamNameTxt.setGravity(Gravity.CENTER);
                teamNameTxt.setWidth(30);
                teamNameTxt.setTextSize(10);
                teamNameTxt.setText(team);
                mListview.addFooterView(teamNameTxt);
                TextView teamStats = new TextView(TeamPlayersListScreen.this);
                //teamStats.setGravity(Gravity.CENTER);
                //teamStats.setWidth(30);
                teamStats.setTextSize(5);
                teamStats.setText("Budget Remaining : " + String.valueOf(response.body().getBudget()) + "\n Batsman : " + String.valueOf(response.body().getBatsman()) + "\n Wicket Keepers : " + String.valueOf(response.body().getWicketKeepers()) +  "\n All Rounders : " + String.valueOf(response.body().getAllRounders()) + "\n Fast Bowlers : " + String.valueOf(response.body().getFastBowlers()) + "\n Spin Bowlers : " + String.valueOf(response.body().getSpinBowlers()) + "\n Foreigners : " + String.valueOf(response.body().getForeigners()) + "\n Total : " + String.valueOf(response.body().getTotal()));
                mListview.addFooterView(teamStats);

            }

            @Override
            public void onFailure(Call<PlayerInfoList> call, Throwable t) {
                Log.d("f", t.getMessage());
            }

        });

        //mArrData = new ArrayList<>();

        // Initialize adapter and set adapter to list view


    }
}