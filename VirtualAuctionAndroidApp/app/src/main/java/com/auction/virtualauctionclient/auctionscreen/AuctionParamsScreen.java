package com.auction.virtualauctionclient.auctionscreen;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;

public class AuctionParamsScreen extends AppCompatActivity  {

    private TextView MaxForeignersEdit, MinTotalEdit, MaxTotalEdit, MaxBudgetEdit, MaxBudgetCroresEdit;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auction_params_screen);
        Bundle bundle = getIntent().getExtras();
        String team = bundle.getString("Team");
        int maxForeigners = bundle.getInt(Constants.I_MAX_FOREIGNERS);
        int minTotal = bundle.getInt(Constants.I_MIN_TOTAL);
        int maxTotal = bundle.getInt(Constants.I_MAX_TOTAL);
        int maxBudget = bundle.getInt(Constants.I_MAX_BUDGET);

        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(team + Constants.PNG_EXT, this.findViewById(android.R.id.content), assetManager);

        MaxForeignersEdit = findViewById(R.id.MaxForeignersEdit);
        MinTotalEdit = findViewById(R.id.MinTotalEdit);
        MaxTotalEdit = findViewById(R.id.MaxTotalEdit);
        MaxBudgetEdit = findViewById(R.id.MaxBudgetEdit);
        MaxBudgetCroresEdit = findViewById(R.id.MaxBudgetCroresEdit);

        MaxForeignersEdit.setText(String.valueOf(maxForeigners));
        MinTotalEdit.setText(String.valueOf(minTotal));
        MaxTotalEdit.setText(String.valueOf(maxTotal));
        MaxBudgetEdit.setText(String.valueOf(maxBudget) +  " lakhs");
        double crores = maxBudget;
        crores = crores / 100.00;
        MaxBudgetCroresEdit.setText(String.valueOf(crores) +  " crores");


    }
    @Override
    public void onBackPressed() {

        finish();

    }
}
