package com.auction.virtualauctionclient.auctionscreen;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.common.Constants;

public class AuctionParamsScreen extends AppCompatActivity  {

    private TextView MaxForeignersEdit, MinTotalEdit, MaxTotalEdit, MaxBudgetEdit;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auction_params_screen);
        Bundle bundle = getIntent().getExtras();
        int maxForeigners = bundle.getInt("MaxForeigners");
        int minTotal = bundle.getInt("MinTotal");
        int maxTotal = bundle.getInt("MaxTotal");
        int maxBudget = bundle.getInt("MaxBudget");

        MaxForeignersEdit = findViewById(R.id.MaxForeignersEdit);
        MinTotalEdit = findViewById(R.id.MinTotalEdit);
        MaxTotalEdit = findViewById(R.id.MaxTotalEdit);
        MaxBudgetEdit = findViewById(R.id.MaxBudgetEdit);

        MaxForeignersEdit.setText(maxForeigners);
        MinTotalEdit.setText(minTotal);
        MaxTotalEdit.setText(maxTotal);
        MaxBudgetEdit.setText(maxBudget);


    }
}
