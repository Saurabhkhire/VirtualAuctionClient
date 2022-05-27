package com.auction.virtualauctionclient.auctionscreen;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.PlayerInfoList;
import com.auction.virtualauctionclient.model.RoomInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnsoldPlayersListScreen extends AppCompatActivity {

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
        setContentView(R.layout.unsold_players_list_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("Username");
        String roomId = bundle.getString("RoomId");

        mListview = findViewById(R.id.ListViewofUnsoldPlayers);

        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setUsername(userName);
        roomInfo.setRoomId(roomId);
        (Client.getClient().getTotalUnsoldPlayersList("application/json", roomInfo)).enqueue(new Callback<PlayerInfoList>() {
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
                mAdapter = new UnsoldPlayersListAdapter(UnsoldPlayersListScreen.this, playerNamesList, playerCountryList, playerRoleList, battingStyleList, bowlingStyleList, battingPositionList, basePriceInLakhsList, basePriceInCroresList, mListview);
                mListview.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

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