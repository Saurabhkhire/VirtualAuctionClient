package com.auction.virtualauctionclient.accountscreen;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.loginregister.GenerateOtpScreen;
import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.loginregister.RegisterScreen;
import com.auction.virtualauctionclient.loginregister.ResetPasswordScreen;
import com.auction.virtualauctionclient.loginregister.UpdateUserDetailsScreen;
import com.auction.virtualauctionclient.model.OtpInfo;
import com.auction.virtualauctionclient.model.Register;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.UpdateDetails;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerifyOtpScreen extends AppCompatActivity {

    public static Activity activity;
    private Button VerifyOtpBtn, ResendOtpBtn;
    private EditText OtpEdit;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_otp_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString(Constants.I_USERNAME);
        String password = bundle.getString(Constants.I_PASSWORD);
        String emailId = bundle.getString(Constants.I_EMAIL_ID);
        String registerOrForgotPassword = bundle.getString(Constants.I_REGISTER_OR_FORGOT_PASSWORD);
        activity = this;
        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(Constants.ACCOUNT_BACKGROUND, this.findViewById(android.R.id.content), assetManager);

        OtpEdit = findViewById(R.id.OtpEdit);
        VerifyOtpBtn = findViewById(R.id.verify_otp_button);
        ResendOtpBtn = findViewById(R.id.resend_otp_button);

        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, VerifyOtpBtn);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, ResendOtpBtn);

        VerifyOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        OtpInfo otpInfo = new OtpInfo();
        otpInfo.setUsername(userName);
        otpInfo.setOtpOperationType(registerOrForgotPassword);
        otpInfo.setOtp(Integer.parseInt(OtpEdit.getText().toString()));
                final String[] message = {""};


        try {

            (Client.getClient().verifyOtp("application/json", otpInfo)).enqueue(new Callback<ResponseMessage>() {
                @Override
                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                    message[0] = response.body().getMessage();

                }

                @Override
                public void onFailure(Call<ResponseMessage> call, Throwable t) {
                    Log.d("f", t.getMessage());
                }

            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
                //if(message[0].equals(Constants.OK_MESSAGE)) {

                    if(registerOrForgotPassword.equals("Register")) {

                        Register register = new Register();
                        register.setUsername(userName);
                        register.setPassword(password);
                        register.setEmailId(emailId);

                        try {

                            (Client.getClient().register("application/json", register)).enqueue(new Callback<ResponseMessage>() {
                                @Override
                                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                                    String message = response.body().getMessage();

                                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    if (message.equals(Constants.USER_REGISTERED)) {

                                       // Intent intent = new Intent(VerifyOtpScreen.this, MainActivity.class);
                                        // intent.putExtra("team", TeamName);
                                        // start the activity connect to the specified class
                                        //startActivity(intent);
                                        finish();
                                        GenerateOtpScreen.activity.finish();
                                        UpdateUserDetailsScreen.activity.finish();
                                        RegisterScreen.activity.finish();

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

                    } else if(registerOrForgotPassword.equals("UpdateDetails")) {

                        String[] userNameSplit = userName.split(",");
                        String[] passwordSplit = password.split(",");
                        String[] emailIdSplit = emailId.split(",");

                        UpdateDetails updateDetails = new UpdateDetails();
                        updateDetails.setUsername(userNameSplit[0]);
                        updateDetails.setPassword(passwordSplit[0]);
                        updateDetails.setEmailId(emailIdSplit[0]);
                        updateDetails.setOldUsername(userNameSplit[1]);
                        updateDetails.setOldPassword(passwordSplit[1]);
                        updateDetails.setOldEmailId(emailIdSplit[1]);


                        try {

                            (Client.getClient().updateUserDetails("application/json", updateDetails)).enqueue(new Callback<ResponseMessage>() {
                                @Override
                                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                                    String message = response.body().getMessage();

                                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    if (message.equals(Constants.USER_DETAILS_UPDATED)) {

                                        File file = new File(getFilesDir(), Constants.INTERNAL_STORAGE_FILE);
                                        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                                            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                                            bufferedWriter.write(userNameSplit[2] + "," + userNameSplit[0] + "," + passwordSplit[0] + "," + emailIdSplit[0]);
                                            bufferedWriter.close();
                                            outputStreamWriter.close();
                                        } catch (Exception exception) {
                                            exception.printStackTrace();
                                        }


                                        finish();
                                        GenerateOtpScreen.activity.finish();
                                        UpdateUserDetailsScreen.activity.finish();
                                        AccountScreen.activity.finish();

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
                        Intent intent = new Intent(VerifyOtpScreen.this, ResetPasswordScreen.class);
                        intent.putExtra(Constants.I_USERNAME, userName);
                        startActivity(intent);
                    }
               // }

            }
        });

        ResendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OtpInfo otpInfo = new OtpInfo();
                otpInfo.setUsername(userName);
                otpInfo.setEmailId(emailId);
                otpInfo.setOtpOperationType(Constants.I_RESET_PASSWORD);


                try {

                    (Client.getClient().generateOtp("application/json", otpInfo)).enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {


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
    @Override
    public void onBackPressed() {

        finish();

    }
}
