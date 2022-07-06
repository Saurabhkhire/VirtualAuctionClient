package com.auction.virtualauctionclient.auctionscreen;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
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
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPlayersAfterAuctionListScreen extends AppCompatActivity {

    private TextView PlayersPriceEdit;
    private Spinner BuyPlayersList;
    private Button buyBtn;
    private ArrayList<String> playerNamesList;
    private ArrayList<String> priceList;
    private ArrayAdapter<String> SelectTeamListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_players_after_auction_list_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("Username");
        String roomId = bundle.getString("RoomId");
        String team = bundle.getString("Team");
        String status = bundle.getString("RoomStatus");

        PlayersPriceEdit = findViewById(R.id.PlayersPriceEdit);
        BuyPlayersList = findViewById(R.id.PlayersListTxt);
        buyBtn = findViewById(R.id.BuyBtn);

        final Context context = AddPlayersAfterAuctionListScreen.this.getApplicationContext();
        final Context contextForFinish = AddPlayersAfterAuctionListScreen.this;

        try {
        Team teams = new Team();
        teams.setUsername(userName);
        teams.setRoomId(roomId);
        teams.setTeam(team);
        (Client.getClient().getUnsoldPlayers("application/json", teams)).enqueue(new Callback<NamesList>() {
            @Override
            public void onResponse(Call<NamesList> call, Response<NamesList> response) {

                playerNamesList = response.body().getNamesList();
                priceList = response.body().getPriceList();

                SelectTeamListAdapter = new ArrayAdapter<>(AddPlayersAfterAuctionListScreen.this, android.R.layout.simple_spinner_dropdown_item, playerNamesList);
                BuyPlayersList.setAdapter(SelectTeamListAdapter);

                // mAdapter = new UnsoldPlayersForShortlistListAdapter(UnsoldPlayersForShortlistListScreen.this, mArrData, addBtn, userName, roomId, mListview);
                // mListview.setAdapter(mAdapter);
                // mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NamesList> call, Throwable t) {
                Log.d("f", t.getMessage());
            }

        });
    } catch (Exception ex) {
        ex.printStackTrace();
    }

        BuyPlayersList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {

                int position = BuyPlayersList.getSelectedItemPosition();
                PlayersPriceEdit.setText(priceList.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    PlayerName playerName = new PlayerName();
                    playerName.setUsername(userName);
                    playerName.setRoomId(roomId);
                    playerName.setTeam(team);
                    playerName.setPlayerName(BuyPlayersList.getSelectedItem().toString());
                    //CommonApiLogic.addPlayer(playerName);

                final String[] message = {""};
                //RoomInfo roomInfo = new RoomInfo();
                //roomInfo.setUsername("sk");
                //roomInfo.setRoomId("2");

                try {
                    (Client.getClient().addPlayerToTeamAfterAuction("application/json", playerName)).enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                            //String message = response.body().getMessage();



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

        // Initialize adapter and set adapter to list view


    }
}