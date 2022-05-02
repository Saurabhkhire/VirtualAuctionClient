package com.auction.virtualauctionclient.room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.model.AuctionParams;
import com.auction.virtualauctionclient.model.ResponseMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionSettingsScreen extends AppCompatActivity
{

    private Button SaveBtn;
    private Spinner SelectTeamSpinner;
    private EditText MaxForeignersEdit, MinTotalEdit, MaxTotalEdit, MaxBudgetEdit;
    private TextView MaxForeignersText, MinTotalText, MaxTotalText, MaxBudgetText;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auction_settings_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("Username");
        String roomId = bundle.getString("RoomId");
        String status = bundle.getString("Status");
        String host = bundle.getString("Host");

        SaveBtn = findViewById(R.id.save_button);
        SelectTeamSpinner =  findViewById(R.id.SelectTeamSpinner);
        MaxForeignersText = findViewById(R.id.MaxForeignersTxt);
        MinTotalText = findViewById(R.id.MinTotalTxt);
        MaxTotalText = findViewById(R.id.MaxTotalTxt);
        MaxBudgetText =  findViewById(R.id.MaxBudgetTxt);
        MaxForeignersEdit = findViewById(R.id.MaxForeignersEdit);
        MinTotalEdit = findViewById(R.id.MinTotalEdit);
        MaxTotalEdit = findViewById(R.id.MaxTotalEdit);
        MaxBudgetEdit =  findViewById(R.id.MaxBudgetEdit);


        String[] SelectTeamList = new String[]{"csk", "rcb", "dc","mi","kkr","pbks","rr","srh","gt","lsg"};
        ArrayAdapter<String> SelectTeamListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, SelectTeamList);
        SelectTeamSpinner.setAdapter(SelectTeamListAdapter);

        if(!host.equals("Host")) {

            MaxForeignersText.setVisibility(View.INVISIBLE);
            MinTotalText.setVisibility(View.INVISIBLE);
            MaxTotalText.setVisibility(View.INVISIBLE);
            MaxBudgetText.setVisibility(View.INVISIBLE);
            MaxForeignersEdit.setVisibility(View.INVISIBLE);
            MinTotalEdit.setVisibility(View.INVISIBLE);
            MaxTotalEdit.setVisibility(View.INVISIBLE);
            MaxBudgetEdit.setVisibility(View.INVISIBLE);
        }

        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    AuctionParams auctionParams = new AuctionParams();
                    auctionParams.setUsername(userName);
                    auctionParams.setRoomId(roomId);
                    auctionParams.setTeam(SelectTeamSpinner.getSelectedItem().toString());


                    if (host.equals("Host")) {

                    int maxForeigners = Integer.parseInt(MaxForeignersEdit.getText().toString());
                    int minTotal = Integer.parseInt(MinTotalEdit.getText().toString());
                    int maxTotal = Integer.parseInt(MaxTotalEdit.getText().toString());
                    int maxBudget = Integer.parseInt(MaxBudgetEdit.getText().toString());

                    if (minTotal > maxTotal) {


                    } else if(maxForeigners>maxTotal)  {

                } else {

                        auctionParams.setMaxForeigners(maxForeigners);
                        auctionParams.setMinTotal(minTotal);
                        auctionParams.setMaxTotal(maxTotal);
                        auctionParams.setMaxBudget(maxBudget);
                    }
                        } else {
                            auctionParams.setMaxForeigners(0);
                            auctionParams.setMinTotal(0);
                            auctionParams.setMaxTotal(0);
                            auctionParams.setMaxBudget(0);
                        }


                        (Client.getClient().updateAuctionParams("application/json", auctionParams)).enqueue(new Callback<ResponseMessage>() {
                            @Override
                            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                                //Log.d("responseBodyGET", response.body().toString());
                                String message = response.body().getMessage();


                            }

                            @Override
                            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                                Log.d("f", t.getMessage());
                            }

                        });

                    } catch (Exception ex) {
                    ex.printStackTrace();
                }
                finish();
                }


        });


    }
}
