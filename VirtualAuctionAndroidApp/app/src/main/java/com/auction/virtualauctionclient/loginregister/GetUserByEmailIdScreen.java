package com.auction.virtualauctionclient.loginregister;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.Register;
import com.auction.virtualauctionclient.model.Username;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetUserByEmailIdScreen extends AppCompatActivity {

    private boolean emailIdCheck;
    private Button GetUsernameBtn;
    private EditText EmailIdEdit;
    private TextView UsernameEdit;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_user_by_email_id_screen);

        AssetManager assetManager = getResources().getAssets();
        CommonLogic.setBackgroundImage(Constants.ACCOUNT_BACKGROUND, this.findViewById(android.R.id.content), assetManager);

        UsernameEdit = findViewById(R.id.UsernameEdit);
        EmailIdEdit = findViewById(R.id.EmailIdEdit);
        GetUsernameBtn = findViewById(R.id.get_username_button);

        CommonLogic.setBackgroundForButtons(Constants.BUTTON_BACKGROUND2, GetUsernameBtn);

        EmailIdEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailIdCheck = CommonLogic.editTextCheck(EmailIdEdit, Constants.I_EMAIL_ID, Constants.NOT_NULL, Constants.EMAIL_ENTRY, 1, 30);
            }
        });


        GetUsernameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (emailIdCheck) {

                    Register register = new Register();
                    register.setEmailId(EmailIdEdit.getText().toString());

                    try {

                        (Client.getClient().getUserByPhoneNumber("application/json", register)).enqueue(new Callback<Username>() {
                            @Override
                            public void onResponse(Call<Username> call, Response<Username> response) {

                                String message = response.body().getMessage();
                                String username = response.body().getUsername();

                                if (message.equals(Constants.OK_MESSAGE)) {

                                    UsernameEdit.setText(username);

                                }
                            }

                            @Override
                            public void onFailure(Call<Username> call, Throwable t) {
                                Log.d("f", t.getMessage());
                            }

                        });

                    } catch (Exception ex) {
                        ex.printStackTrace();
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
