package com.auction.virtualauctionclient.auctionscreen;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.NamesList;
import com.auction.virtualauctionclient.model.RoomInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPlayersAfterAuctionListScreen extends AppCompatActivity {

    private ListView mListview;
    private ArrayList<String> mArrData;
    private AddPlayersAfterAuctionListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_players_after_auction_list_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("Username");
        String roomId = bundle.getString("RoomId");
        String team = bundle.getString("Team");

        mListview = (ListView) findViewById(R.id.AddPlayersAfterAuctionList);

        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setUsername(userName);
        roomInfo.setRoomId(roomId);
        (Client.getClient().getUnsoldPlayers("application/json", roomInfo)).enqueue(new Callback<NamesList>() {
            @Override
            public void onResponse(Call<NamesList> call, Response<NamesList> response) {

                mArrData = response.body().getNamesList();
                mAdapter = new AddPlayersAfterAuctionListAdapter(AddPlayersAfterAuctionListScreen.this, mArrData, userName, roomId, team);
                mListview.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NamesList> call, Throwable t) {
                Log.d("f", t.getMessage());
            }

        });

        // Initialize adapter and set adapter to list view


    }
}