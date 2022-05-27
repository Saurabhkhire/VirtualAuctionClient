package com.auction.virtualauctionclient.room;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
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
        String status = bundle.getString("RoomStatus");
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


        MaxForeignersEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CommonLogic.editTextCheck(MaxForeignersEdit, Constants.I_MAX_FOREIGNERS_NAME, Constants.NUMERIC_ENTRY, 1, 2);
            }
        });

        MinTotalEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CommonLogic.editTextCheck(MinTotalEdit, Constants.I_MIN_TOTAL_NAME, Constants.NUMERIC_ENTRY, 1, 2);
            }
        });

        MaxTotalEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CommonLogic.editTextCheck(MaxTotalEdit, Constants.I_MAX_TOTAL_NAME, Constants.NUMERIC_ENTRY, 1, 2);
            }
        });

        MaxBudgetEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CommonLogic.editTextCheck(MaxBudgetEdit, Constants.I_MAX_BUDGET_NAME, Constants.NUMERIC_ENTRY, 1, 5);
            }
        });

        String[] SelectTeamList = new String[]{"csk", "rcb", "dc","mi","kkr","pbks","rr","srh","gt","lsg"};
        ArrayAdapter<String> SelectTeamListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, SelectTeamList);
        SelectTeamSpinner.setAdapter(SelectTeamListAdapter);

        if(!host.equals(Constants.I_HOST)) {

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

                    boolean rightInput = false;
                    boolean errorInputCheck = false;
                    String errorMessage = "";

                    if (host.equals(Constants.I_HOST)) {

                        boolean maxForeignersCheck = CommonLogic.editTextCheck(MaxForeignersEdit, Constants.I_MAX_FOREIGNERS_NAME, Constants.NUMERIC_ENTRY, 1, 2);
                        boolean minTotalCheck = CommonLogic.editTextCheck(MinTotalEdit, Constants.I_MIN_TOTAL_NAME, Constants.NUMERIC_ENTRY, 1, 2);
                        boolean maxTotalCheck = CommonLogic.editTextCheck(MaxTotalEdit, Constants.I_MIN_TOTAL_NAME, Constants.NUMERIC_ENTRY, 1, 2);
                        boolean maxBudgetCheck = CommonLogic.editTextCheck(MaxBudgetEdit, Constants.I_MAX_BUDGET_NAME, Constants.NUMERIC_ENTRY, 1, 2);

                                if(maxForeignersCheck && minTotalCheck && maxTotalCheck && maxBudgetCheck) {

                                    errorInputCheck = true;

                                    int maxForeigners = Integer.parseInt(MaxForeignersEdit.getText().toString());
                                    int minTotal = Integer.parseInt(MinTotalEdit.getText().toString());
                                    int maxTotal = Integer.parseInt(MaxTotalEdit.getText().toString());
                                    int maxBudget = Integer.parseInt(MaxBudgetEdit.getText().toString());

                                    if (minTotal >= maxTotal) {

                                        errorMessage = errorMessage + Constants.MAX_FOREIGENER_GREATER_MESSAGE;

                                    } else if (maxForeigners >= minTotal) {

                                        errorMessage = errorMessage + Constants.MIN_TOTAL_GREATER_MESSAGE;

                                    } else {

                                        auctionParams.setMaxForeigners(maxForeigners);
                                        auctionParams.setMinTotal(minTotal);
                                        auctionParams.setMaxTotal(maxTotal);
                                        auctionParams.setMaxBudget(maxBudget);

                                        rightInput = true;
                                    }
                                }

                    } else {
                        auctionParams.setMaxForeigners(0);
                        auctionParams.setMinTotal(0);
                        auctionParams.setMaxTotal(0);
                        auctionParams.setMaxBudget(0);

                        rightInput = true;
                    }

                    if(rightInput) {

                    (Client.getClient().updateAuctionParams("application/json", auctionParams)).enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                            //Log.d("responseBodyGET", response.body().toString());
                            String message = response.body().getMessage();

                            if (message.equals(Constants.OK_MESSAGE)) {

                                finish();
                            } else {

                                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }


                        }

                        @Override
                        public void onFailure(Call<ResponseMessage> call, Throwable t) {
                            Log.d("f", t.getMessage());
                        }

                    });
                } else {

                        if (errorInputCheck) {
                            Toast toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                    } catch (Exception ex) {
                    ex.printStackTrace();
                }
                }


        });


    }
}
