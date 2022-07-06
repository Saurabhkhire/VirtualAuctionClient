package com.auction.virtualauctionclient.auctionscreen;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.common.CommonLogic;
import com.auction.virtualauctionclient.common.Constants;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.RoomInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsernamesListAdapter extends BaseAdapter {

    private String userNameStr;
    private String roomIdStr;
    private String teamStr;
    private Context mContext;
    private ArrayList<String> mArrSchoolData;

    public UsernamesListAdapter(Context context, ArrayList<String> arrSchoolData, String userName, String roomId, String team) {
        super();
        mContext = context;
        mArrSchoolData = arrSchoolData;
        userNameStr = userName;
        roomIdStr = roomId;
        teamStr = team;
    }

    public int getCount() {
        // return the number of records
        return mArrSchoolData.size();
    }

    // getView method is called for each item of ListView
    public View getView(int position, View view, ViewGroup parent) {
        // inflate the layout for each item of listView
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.usernames_listview, parent, false);


        // get the reference of textView and button
        TextView txtPlayersList = (TextView) view.findViewById(R.id.UsernamesListTxt);
        Button btnAction = (Button) view.findViewById(R.id.MakeHostBtn);

        CommonLogic.setBackgroundForButtons(teamStr, btnAction);
        // Set the title and button name
        txtPlayersList.setText(mArrSchoolData.get(position));
        btnAction.setText("Make Host");

        // Click listener of button
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(view.getContext(), mArrSchoolData.get(position), Toast.LENGTH_SHORT).show();

                String newHostUsername =  mArrSchoolData.get(position);

                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setUsername(userNameStr + "," + newHostUsername);
                roomInfo.setRoomId(roomIdStr);
                (Client.getClient().makeHost("application/json", roomInfo)).enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                        String message = response.body().getMessage();

                        if(message.equals(Constants.OK_MESSAGE)) {

                            ((Activity)mContext).finish();

                        }   else {
                            Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {
                        Log.d("f", t.getMessage());
                    }

                });


            }
        });

        return view;
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }}


