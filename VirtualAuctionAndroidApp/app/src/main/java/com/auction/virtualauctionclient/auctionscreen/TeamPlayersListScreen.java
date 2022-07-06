package com.auction.virtualauctionclient.auctionscreen;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ContentFrameLayout;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.model.PlayerInfoList;
import com.auction.virtualauctionclient.model.Team;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamPlayersListScreen extends AppCompatActivity {

    View topLine;
    private TextView PlayerNameTxt, PlayerCountryTxt, PlayerRoleTxt, BattingStyleTxt, BowlingStyleTxt, BattingPositionTxt, PriceInLakhsTxt, PriceInCroresTxt;
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

        PlayerNameTxt = findViewById(R.id.PlayerNameTxt);
        PlayerCountryTxt = findViewById(R.id.PlayerCountryTxt);
        PlayerRoleTxt = findViewById(R.id.PlayerRoleTxt);
        BattingStyleTxt = findViewById(R.id.BattingStyleTxt);
        BowlingStyleTxt = findViewById(R.id.BowlingStyleTxt);
        BattingPositionTxt = findViewById(R.id.BattingPositionTxt);
        PriceInLakhsTxt = findViewById(R.id.PriceinLakhsTxt);
        PriceInCroresTxt = findViewById(R.id.PriceinCroresTxt);
        topLine = findViewById(R.id.topLine);
        ContentFrameLayout contentFrameLayout = this.findViewById(android.R.id.content);

        //mListview = (ListView) findViewById(R.id.ListViewofTeamPlayers);
        //mListview.setLayoutParams(new RelativeLayout.LayoutParams(2000,    600));

//        playerNamesList.add(Constants.I_PLAYER_NAME_HEADER);
//        playerCountryList.add(Constants.I_PLAYER_COUNTRY_HEADER);
//        playerRoleList.add(Constants.I_PLAYER_ROLE_HEADER);
//        battingStyleList.add(Constants.I_BATTING_STYLE_HEADER);
//        bowlingStyleList.add(Constants.I_BOWLING_STYLE_HEADER);
//        battingPositionList.add(Constants.I_BATTING_POSITION_HEADER);
//        basePriceInLakhsList.add(Constants.I_PRICE_IN_LAKHS_HEADER);
//        basePriceInCroresList.add(Constants.I_PRICE_IN_CRORES_HEADER);

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

                TableLayout prices = (TableLayout)findViewById(R.id.tableL);
                //prices.setStretchAllColumns(true);
                //prices.bringToFront();
                CommonLogic.setTable(contentFrameLayout, topLine, PlayerNameTxt, PlayerCountryTxt, PlayerRoleTxt, BattingStyleTxt, BowlingStyleTxt, BattingPositionTxt, PriceInLakhsTxt, PriceInCroresTxt ,playerNamesList, playerCountryList, playerRoleList, battingStyleList, bowlingStyleList, battingPositionList, totalPriceInLakhsList, totalPriceInCroresList, prices, TeamPlayersListScreen.this, team);

                String colorList = CommonLogic.colorForLists(team,"list");
                String[] colorListSplit = colorList.split(",");
                TextView cc = new TextView(TeamPlayersListScreen.this);
                cc.setText("\n\n Budget Remaining : " + String.valueOf(response.body().getBudgetInLakhs()) + "\n Batsman : " + String.valueOf(response.body().getBatsman()) + "\n Wicket Keepers : " + String.valueOf(response.body().getWicketKeepers()) +  "\n All Rounders : " + String.valueOf(response.body().getAllRounders()) + "\n Fast Bowlers : " + String.valueOf(response.body().getFastBowlers()) + "\n Spin Bowlers : " + String.valueOf(response.body().getSpinBowlers()) + "\n Foreigners : " + String.valueOf(response.body().getForeigners()) + "\n Total : " + String.valueOf(response.body().getTotal()));
                cc.setTextColor(Color.parseColor(colorListSplit[1]));
                prices.addView(cc);

//                mAdapter = new UnsoldPlayersListAdapter(TeamPlayersListScreen.this, playerNamesList, playerCountryList, playerRoleList, battingStyleList, bowlingStyleList, battingPositionList, totalPriceInLakhsList, totalPriceInCroresList, mListview);
//                mListview.setAdapter(mAdapter);
//                mAdapter.notifyDataSetChanged();
//                TextView teamStats = new TextView(TeamPlayersListScreen.this);
//                //teamStats.setGravity(Gravity.CENTER);
//                //teamStats.setWidth(30);
//                teamStats.setTextSize(30);
//                teamStats.setText("Budget Remaining : " + String.valueOf(response.body().getBudget()) + "\n Batsman : " + String.valueOf(response.body().getBatsman()) + "\n Wicket Keepers : " + String.valueOf(response.body().getWicketKeepers()) +  "\n All Rounders : " + String.valueOf(response.body().getAllRounders()) + "\n Fast Bowlers : " + String.valueOf(response.body().getFastBowlers()) + "\n Spin Bowlers : " + String.valueOf(response.body().getSpinBowlers()) + "\n Foreigners : " + String.valueOf(response.body().getForeigners()) + "\n Total : " + String.valueOf(response.body().getTotal()));
//                mListview.addFooterView(teamStats);

            }

            @Override
            public void onFailure(Call<PlayerInfoList> call, Throwable t) {
                Log.d("f", t.getMessage());
            }

        });

        //mArrData = new ArrayList<>();

        // Initialize adapter and set adapter to list view


    }
    @Override
    public void onBackPressed() {

        finish();

    }
}