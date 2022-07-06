package com.auction.virtualauctionclient.accountscreen;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;

public class ReadUserDetailsScreen extends AppCompatActivity  {

    private TextView UsernameEdit, PasswordEdit, PhoneNumberEdit;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_user_details_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString(Constants.I_USERNAME);
        String password = bundle.getString(Constants.I_PASSWORD);
        String phoneNumber = bundle.getString(Constants.I_EMAIL_ID);

        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(Constants.ACCOUNT_BACKGROUND, this.findViewById(android.R.id.content), assetManager);

        UsernameEdit = findViewById(R.id.UsernameEdit);
        PasswordEdit = findViewById(R.id.PasswordEdit);
        PhoneNumberEdit = findViewById(R.id.EmailIdEdit);

        UsernameEdit.setText(userName);
        PasswordEdit.setText(password);
        PhoneNumberEdit.setText(phoneNumber);

    }
    @Override
    public void onBackPressed() {

        finish();

    }
}
