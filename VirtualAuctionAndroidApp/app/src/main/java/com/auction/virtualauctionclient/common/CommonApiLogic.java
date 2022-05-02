package com.auction.virtualauctionclient.common;

import android.util.Log;

import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.RoomInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonApiLogic {

    public static String leaveRoomApi(RoomInfo roomInfo) {

        final String[] message = {""};

        try {

            (Client.getClient().leaveRoom("application/json", roomInfo)).enqueue(new Callback<ResponseMessage>() {
                @Override
                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                    //Log.d("responseBodyGET", response.body().toString());
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



        return message[0];
    }

    public static String quitRoomApi(RoomInfo roomInfo) {

        final String[] message = {""};

        try {

            (Client.getClient().quitRoom("application/json", roomInfo)).enqueue(new Callback<ResponseMessage>() {
                @Override
                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                    //Log.d("responseBodyGET", response.body().toString());
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



        return message[0];
    }

    public static String pauseAuctionApi(RoomInfo roomInfo) {

        final String[] message = {""};

        try {

            (Client.getClient().pauseAuction("application/json", roomInfo)).enqueue(new Callback<ResponseMessage>() {
                @Override
                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                    //Log.d("responseBodyGET", response.body().toString());
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



        return message[0];
    }

    public static String playAuctionApi(RoomInfo roomInfo) {

        final String[] message = {""};

        try {

            (Client.getClient().playAuction("application/json", roomInfo)).enqueue(new Callback<ResponseMessage>() {
                @Override
                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                    //Log.d("responseBodyGET", response.body().toString());
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



        return message[0];
    }
}
