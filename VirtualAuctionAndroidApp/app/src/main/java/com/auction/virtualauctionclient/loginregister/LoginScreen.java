package com.auction.virtualauctionclient.loginregister;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.Login;
import com.auction.virtualauctionclient.model.Register;
import com.auction.virtualauctionclient.room.GameScreen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class LoginScreen extends AppCompatActivity {

    private boolean usernameCheck = true, passwordCheck = true;
    private Button LoginBtn, ForgotUsernameEdit, ForgotPasswordEdit;
    private EditText UsernameEdit, PasswordEdit;
    private CheckBox SaveDetailsCheck;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(Constants.ACCOUNT_BACKGROUND, this.findViewById(android.R.id.content), assetManager);

        UsernameEdit = findViewById(R.id.UsernameEdit);
        PasswordEdit = findViewById(R.id.PasswordEdit);
        SaveDetailsCheck = findViewById(R.id.SaveDetailsCheckbox);
        LoginBtn = findViewById(R.id.login_button);
        ForgotUsernameEdit = findViewById(R.id.forgot_username_button);
        ForgotPasswordEdit = findViewById(R.id.forgot_password_button);

        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, LoginBtn);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, ForgotUsernameEdit);
        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, ForgotPasswordEdit);

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
                passwordCheck = CommonLogic.editTextCheck(PasswordEdit, Constants.I_PASSWORD, Constants.NOT_NULL, Constants.ALPHA_NUMERIC_ENTRY, 1, 12);
            }
        });


            LoginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (usernameCheck && passwordCheck) {

                        Login login = new Login();
                        login.setUsername(UsernameEdit.getText().toString());
                        login.setPassword(PasswordEdit.getText().toString());


                        try {

                            (Client.getClient().login("application/json", login)).enqueue(new Callback<Register>() {
                                @Override
                                public void onResponse(Call<Register> call, Response<Register> response) {

                                    String message = response.body().getMessage();
                                    String username = response.body().getUsername();
                                    String password = response.body().getPassword();
                                    String emailId = response.body().getEmailId();
                                    String saveCheck = "false";
                                    if(SaveDetailsCheck.isChecked()) {
                                        saveCheck = "true";
                                    }

                                    if(message.equals(Constants.OK_MESSAGE)) {

                                    File file = new File(getFilesDir(), Constants.INTERNAL_STORAGE_FILE);
                                    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                                        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                                        bufferedWriter.write(saveCheck + "," + username + "," + password + "," + emailId);
                                        bufferedWriter.close();
                                        outputStreamWriter.close();
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }

                                    Intent intent = new Intent(LoginScreen.this, GameScreen.class);
                                    intent.putExtra(Constants.I_USERNAME, UsernameEdit.getText().toString());
                                    startActivity(intent);
                                    finish();
                                    LoginRegisterScreen.activity.finish();

                                    } else {

                                        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Register> call, Throwable t) {
                                    Log.d("f", t.getMessage());
                                }

                            });

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }
            });

            ForgotUsernameEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(LoginScreen.this, GetUserByEmailIdScreen.class);
                    startActivity(intent);

                }
            });

        ForgotPasswordEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginScreen.this, GenerateOtpScreen.class);
                startActivity(intent);

            }
        });



    }

    @Override
    public void onBackPressed() {

        finish();

    }



}
