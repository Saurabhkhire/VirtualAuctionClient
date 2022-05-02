package com.auction.virtualauctionclient.loginregister;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.model.Login;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.room.GameScreen;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginScreen extends AppCompatActivity {

    private Button LoginBtn;
    private EditText UsernameEdit, PasswordEdit;

    // try {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        UsernameEdit = findViewById(R.id.UsernameEdit);
        PasswordEdit = findViewById(R.id.PasswordEdit);
        LoginBtn = findViewById(R.id.login_button);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        Login login = new Login();
        login.setUsername(UsernameEdit.getText().toString());
        login.setPassword(PasswordEdit.getText().toString());


        try {

            (Client.getClient().login("application/json", login)).enqueue(new Callback<ResponseMessage>() {
                @Override
                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                    String message = response.body().getMessage();

                    if(message.equals("UserExist")) {

                                Intent intent = new Intent(LoginScreen.this, GameScreen.class);
                                intent.putExtra("Username", UsernameEdit.getText().toString());
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
