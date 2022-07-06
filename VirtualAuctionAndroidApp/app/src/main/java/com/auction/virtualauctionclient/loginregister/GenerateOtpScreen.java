package com.auction.virtualauctionclient.loginregister;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.accountscreen.VerifyOtpScreen;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.OtpInfo;
import com.auction.virtualauctionclient.model.ResponseMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GenerateOtpScreen extends AppCompatActivity {

    public static Activity activity;
    private boolean usernameCheck, emailIdCheck;
    private Button GenerateOtpBtn;
    private EditText UsernameEdit, EmailIdEdit;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_otp_screen);
        activity = this;
        UsernameEdit = findViewById(R.id.UsernameEdit);
        EmailIdEdit = findViewById(R.id.EmailIdEdit);
        GenerateOtpBtn = findViewById(R.id.generate_otp_button);

        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, GenerateOtpBtn);

        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(Constants.ACCOUNT_BACKGROUND, this.findViewById(android.R.id.content), assetManager);

        UsernameEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usernameCheck = CommonLogic.editTextCheck(UsernameEdit, Constants.I_USERNAME, Constants.NOT_NULL, Constants.ALPHA_NUMERIC_ENTRY, 1, 12);
            }
        });

        EmailIdEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailIdCheck = CommonLogic.editTextCheck(EmailIdEdit, Constants.I_EMAIL_ID, Constants.NOT_NULL, Constants.EMAIL_ENTRY, 1, 30);
            }
        });

        if(usernameCheck && emailIdCheck) {

        GenerateOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OtpInfo otpInfo = new OtpInfo();
                otpInfo.setUsername(UsernameEdit.getText().toString());
                otpInfo.setEmailId(EmailIdEdit.getText().toString());
                otpInfo.setOtpOperationType(Constants.I_RESET_PASSWORD);


                try {

                    (Client.getClient().generateOtp("application/json", otpInfo)).enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                            String message = response.body().getMessage();

                            if (message.equals(Constants.OK_MESSAGE)) {

                                Intent intent = new Intent(GenerateOtpScreen.this, VerifyOtpScreen.class);
                                intent.putExtra(Constants.I_USERNAME, UsernameEdit.getText().toString());
                                intent.putExtra(Constants.I_PASSWORD, "");
                                intent.putExtra(Constants.I_EMAIL_ID, EmailIdEdit.getText().toString());
                                intent.putExtra(Constants.I_REGISTER_OR_FORGOT_PASSWORD, "ForgotPassword");
                                startActivity(intent);

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

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }
    }
    @Override
    public void onBackPressed() {

        finish();

    }
}
