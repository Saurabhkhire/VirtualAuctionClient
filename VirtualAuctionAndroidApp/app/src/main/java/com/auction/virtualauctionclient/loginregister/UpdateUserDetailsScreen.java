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
import com.auction.virtualauctionclient.accountscreen.AccountScreen;
import com.auction.virtualauctionclient.accountscreen.VerifyOtpScreen;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.OtpInfo;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.UpdateDetails;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserDetailsScreen extends AppCompatActivity  {

    public static Activity activity;
    private boolean usernameCheck, passwordCheck, emailIdCheck;
    private Button UpdateBtn;
    private EditText UsernameEdit, PasswordEdit, EmailIdEdit;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user_details_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString(Constants.I_USERNAME);
        String password = bundle.getString(Constants.I_PASSWORD);
        String emailId = bundle.getString(Constants.I_EMAIL_ID);
        String keepLoggedIn = bundle.getString(Constants.I_KEEP_LOGGED_IN);
        activity = this;
        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(Constants.ACCOUNT_BACKGROUND, this.findViewById(android.R.id.content), assetManager);

        UsernameEdit = findViewById(R.id.UsernameEdit);
        PasswordEdit = findViewById(R.id.PasswordEdit);
        EmailIdEdit = findViewById(R.id.EmailIdEdit);
        UpdateBtn = findViewById(R.id.update_button);

        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, UpdateBtn);

        UsernameEdit.setText(userName);
        PasswordEdit.setText(password);
        EmailIdEdit.setText(emailId);

        usernameCheck = true;
        passwordCheck = true;
        emailIdCheck = true;

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

        EmailIdEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailIdCheck = CommonLogic.editTextCheck(EmailIdEdit, Constants.I_PASSWORD, Constants.NULL, Constants.EMAIL_ENTRY, 0, 30);
            }
        });


            UpdateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(usernameCheck && passwordCheck && emailIdCheck) {

                    if (EmailIdEdit.getText().toString().equals(emailId) || EmailIdEdit.getText().toString().equals("")) {

                        UpdateDetails updateDetails = new UpdateDetails();
                        updateDetails.setUsername(UsernameEdit.getText().toString());
                        updateDetails.setPassword(PasswordEdit.getText().toString());
                        updateDetails.setEmailId(EmailIdEdit.getText().toString());
                        updateDetails.setOldUsername(userName);
                        updateDetails.setOldPassword(password);
                        updateDetails.setOldEmailId(emailId);


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
                                            bufferedWriter.write(keepLoggedIn + "," + UsernameEdit.getText().toString() + "," + PasswordEdit.getText().toString() + "," + EmailIdEdit.getText().toString());
                                            bufferedWriter.close();
                                            outputStreamWriter.close();
                                        } catch (Exception exception) {
                                            exception.printStackTrace();
                                        }

                                        finish();
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


                        OtpInfo otpInfo = new OtpInfo();
                        otpInfo.setUsername(UsernameEdit.getText().toString());
                        otpInfo.setEmailId(EmailIdEdit.getText().toString());
                        otpInfo.setOtpOperationType(Constants.I_UPDATE_DETAILS);
                        otpInfo.setOldUsername(userName);
                        otpInfo.setOldEmailId(emailId);

                        try {

                            (Client.getClient().generateOtp("application/json", otpInfo)).enqueue(new Callback<ResponseMessage>() {
                                @Override
                                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                                    String message = response.body().getMessage();

                                    if (message.equals(Constants.OK_MESSAGE)) {

                                        Intent intent = new Intent(UpdateUserDetailsScreen.this, VerifyOtpScreen.class);
                                        intent.putExtra(Constants.I_USERNAME, UsernameEdit.getText().toString() + "," + userName + "," + keepLoggedIn);
                                        intent.putExtra(Constants.I_PASSWORD, PasswordEdit.getText().toString() + "," + password);
                                        intent.putExtra(Constants.I_EMAIL_ID, EmailIdEdit.getText().toString() + "," + emailId);
                                        intent.putExtra(Constants.I_REGISTER_OR_FORGOT_PASSWORD, "UpdateDetails");
                                        startActivity(intent);

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
            });

    }
    @Override
    public void onBackPressed() {

        finish();

    }
}
