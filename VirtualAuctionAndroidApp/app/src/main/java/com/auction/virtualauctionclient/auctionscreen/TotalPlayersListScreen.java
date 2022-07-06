package com.auction.virtualauctionclient.auctionscreen;

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
import com.auction.virtualauctionclient.model.RoomInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TotalPlayersListScreen extends AppCompatActivity {

    View topLine;
    private TextView PlayerNameTxt, PlayerCountryTxt, PlayerRoleTxt, BattingStyleTxt, BowlingStyleTxt, BattingPositionTxt, PriceInLakhsTxt, PriceInCroresTxt;
    private ListView mListview;
    private ArrayList<String> playerNamesList;
    private ArrayList<String> playerCountryList;
    private ArrayList<String> playerRoleList;
    private ArrayList<String> battingStyleList;
    private ArrayList<String> bowlingStyleList;
    private ArrayList<String> battingPositionList;
    private ArrayList<String> basePriceInLakhsList;
    private ArrayList<String> basePriceInCroresList;
    private UnsoldPlayersListAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_players_list_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("Username");
        String roomId = bundle.getString("RoomId");

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

        try {
            //mListview = findViewById(R.id.ListViewofUnsoldPlayers);

//        playerNamesList.add(Constants.I_PLAYER_NAME_HEADER);
//        playerCountryList.add(Constants.I_PLAYER_COUNTRY_HEADER);
//        playerRoleList.add(Constants.I_PLAYER_ROLE_HEADER);
//        battingStyleList.add(Constants.I_BATTING_STYLE_HEADER);
//        bowlingStyleList.add(Constants.I_BOWLING_STYLE_HEADER);
//        battingPositionList.add(Constants.I_BATTING_POSITION_HEADER);
//        basePriceInLakhsList.add(Constants.I_PRICE_IN_LAKHS_HEADER);
//        basePriceInCroresList.add(Constants.I_PRICE_IN_CRORES_HEADER);

            RoomInfo roomInfo = new RoomInfo();
            roomInfo.setUsername(userName);
            roomInfo.setRoomId(roomId);
            (Client.getClient().getTotalPlayersList("application/json", roomInfo)).enqueue(new Callback<PlayerInfoList>() {
                @Override
                public void onResponse(Call<PlayerInfoList> call, Response<PlayerInfoList> response) {

                    playerNamesList = response.body().getPlayerNameList();
                    playerCountryList = response.body().getPlayerCountryList();
                    playerRoleList = response.body().getPlayerRoleList();
                    battingStyleList = response.body().getBattingStyleList();
                    bowlingStyleList = response.body().getBowlingStyleList();
                    battingPositionList = response.body().getBattingPositionList();
                    basePriceInLakhsList = response.body().getPriceinLakhsList();
                    basePriceInCroresList = response.body().getPriceinCroresList();

                    TableLayout prices = (TableLayout) findViewById(R.id.tableL);
                    CommonLogic.setTable(contentFrameLayout, topLine, PlayerNameTxt, PlayerCountryTxt, PlayerRoleTxt, BattingStyleTxt, BowlingStyleTxt, BattingPositionTxt, PriceInLakhsTxt, PriceInCroresTxt, playerNamesList, playerCountryList, playerRoleList, battingStyleList, bowlingStyleList, battingPositionList, basePriceInLakhsList, basePriceInCroresList, prices, TotalPlayersListScreen.this, "normal");

                    //mAdapter = new UnsoldPlayersListAdapter(UnsoldPlayersListScreen.this, playerNamesList, playerCountryList, playerRoleList, battingStyleList, bowlingStyleList, battingPositionList, basePriceInLakhsList, basePriceInCroresList, mListview);
                    //mListview.setAdapter(mAdapter);
                    //mAdapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<PlayerInfoList> call, Throwable t) {
                    Log.d("f", t.getMessage());
                }

            });

            //mArrData = new ArrayList<>();

            // Initialize adapter and set adapter to list view
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {

        finish();

    }
}