package com.auction.virtualauctionclient.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.auction.virtualauctionclient.MainActivity;
import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.model.Register;
import com.auction.virtualauctionclient.model.ResponseMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterScreen extends AppCompatActivity  {

    private Button RegisterBtn;
    private EditText UsernameEdit, PasswordEdit, EmailIdEdit;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        UsernameEdit = findViewById(R.id.UsernameEdit);
        PasswordEdit = findViewById(R.id.PasswordEdit);
        EmailIdEdit = findViewById(R.id.EmailIdEdit);
        RegisterBtn = findViewById(R.id.register_button);

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        Register register = new Register();
        register.setUsername(UsernameEdit.getText().toString());
        register.setPassword(PasswordEdit.getText().toString());
        register.setEmailId(EmailIdEdit.getText().toString());


        try {

            (Client.getClient().register("application/json", register)).enqueue(new Callback<ResponseMessage>() {
                @Override
                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                    String message = response.body().getMessage();

                    if(message.equals("Ok")) {

                                Intent intent = new Intent(RegisterScreen.this, MainActivity.class);
                                // intent.putExtra("team", TeamName);
                                // start the activity connect to the specified class
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
        });
    }
}
