package com.auction.virtualauctionclient.loginregister;

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
import com.auction.virtualauctionclient.model.Login;
import com.auction.virtualauctionclient.model.ResponseMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResetPasswordScreen extends AppCompatActivity {

    private boolean passwordCheck, verifyPasswordCheck;
    Button ResetPasswordBtn;
    private EditText PasswordEdit, VerifyPasswordEdit;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_screen);
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString(Constants.I_USERNAME);
        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(Constants.ACCOUNT_BACKGROUND, this.findViewById(android.R.id.content), assetManager);

        PasswordEdit = findViewById(R.id.PasswordEdit);
        VerifyPasswordEdit = findViewById(R.id.VerifyPasswordEdit);
        ResetPasswordBtn = findViewById(R.id.reset_password_button);

        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, ResetPasswordBtn);

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

                ResetPasswordBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(passwordCheck && verifyPasswordCheck) {

                            if (!PasswordEdit.getText().toString().equals(VerifyPasswordEdit.getText().toString())) {

                                Toast toast = Toast.makeText(getApplicationContext(), Constants.PASSSWORDS_NOT_MATCH, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            } else {


                                Login login = new Login();
                                login.setUsername(userName);
                                login.setPassword(PasswordEdit.getText().toString());


                                try {

                                    (Client.getClient().resetPassword("application/json", login)).enqueue(new Callback<ResponseMessage>() {
                                        @Override
                                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                                            String message = response.body().getMessage();

                                            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();

                                            if (message.equals(Constants.PASSWORD_RESET)) {

                                                finish();
                                                VerifyOtpScreen.activity.finish();
                                                GenerateOtpScreen.activity.finish();

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
