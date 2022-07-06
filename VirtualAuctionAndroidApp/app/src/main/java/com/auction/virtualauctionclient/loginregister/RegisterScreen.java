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
import com.auction.virtualauctionclient.model.Register;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.RoomInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterScreen extends AppCompatActivity  {

    public static Activity activity;
    private boolean usernameCheck, passwordCheck, verifyPasswordCheck, emailIdCheck;
    private Button RegisterBtn;
    private EditText UsernameEdit, PasswordEdit, VerifyPasswordEdit, EmailIdEdit;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        activity = this;
        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(Constants.ACCOUNT_BACKGROUND, this.findViewById(android.R.id.content), assetManager);

        UsernameEdit = findViewById(R.id.UsernameEdit);
        PasswordEdit = findViewById(R.id.PasswordEdit);
        VerifyPasswordEdit = findViewById(R.id.VerifyPasswordEdit);
        EmailIdEdit = findViewById(R.id.EmailIdEdit);
        RegisterBtn = findViewById(R.id.register_button);

        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, RegisterBtn);

        UsernameEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usernameCheck = CommonLogic.editTextCheck(UsernameEdit, Constants.I_USERNAME, Constants.NOT_NULL, Constants.ALPHA_NUMERIC_ENTRY, 1, 12);
            }
        });

        PasswordEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordCheck = CommonLogic.editTextCheck(PasswordEdit, Constants.I_PASSWORD, Constants.NOT_NULL, Constants.ALPHA_NUMERIC_ENTRY, 6, 12);
            }
        });

        VerifyPasswordEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verifyPasswordCheck = CommonLogic.editTextCheck(VerifyPasswordEdit, Constants.I_PASSWORD, Constants.NOT_NULL, Constants.ALPHA_NUMERIC_ENTRY, 6, 12);
            }
        });

        EmailIdEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailIdCheck = CommonLogic.editTextCheck(EmailIdEdit, Constants.I_EMAIL_ID, Constants.NULL, Constants.EMAIL_ENTRY, 0, 30);
            }
        });


        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(usernameCheck && passwordCheck && verifyPasswordCheck && emailIdCheck) {

                    if (!PasswordEdit.getText().toString().equals(VerifyPasswordEdit.getText().toString())) {

                        Toast toast = Toast.makeText(getApplicationContext(), Constants.PASSSWORDS_NOT_MATCH, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    } else {

                        if (EmailIdEdit.getText().toString().equals("")) {
                            Register register = new Register();
                            register.setUsername(UsernameEdit.getText().toString());
                            register.setPassword(PasswordEdit.getText().toString());
                            register.setEmailId("");

                            try {

                                (Client.getClient().register("application/json", register)).enqueue(new Callback<ResponseMessage>() {
                                    @Override
                                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                                        String message = response.body().getMessage();

                                        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();

                                        if (message.equals(Constants.USER_REGISTERED)) {


                                            finish();

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

                        } else {

                            OtpInfo otpInfo = new OtpInfo();
                            otpInfo.setUsername(UsernameEdit.getText().toString());
                            otpInfo.setPassword(PasswordEdit.getText().toString());
                            otpInfo.setEmailId(EmailIdEdit.getText().toString());
                            otpInfo.setOtpOperationType(Constants.I_REGISTER);

                            try {

                                (Client.getClient().generateOtp("application/json", otpInfo)).enqueue(new Callback<ResponseMessage>() {
                                    @Override
                                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                                        String message = response.body().getMessage();

                                        if (message.equals(Constants.OK_MESSAGE)) {

                                            Intent intent = new Intent(RegisterScreen.this, VerifyOtpScreen.class);
                                            intent.putExtra(Constants.I_USERNAME, UsernameEdit.getText().toString());
                                            intent.putExtra(Constants.I_PASSWORD, PasswordEdit.getText().toString());
                                            intent.putExtra(Constants.I_EMAIL_ID, EmailIdEdit.getText().toString());
                                            intent.putExtra(Constants.I_REGISTER_OR_FORGOT_PASSWORD, "Register");
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
                    }
                }
            }
        });

            }
    @Override
    public void onBackPressed() {

        finish();

    }

}
