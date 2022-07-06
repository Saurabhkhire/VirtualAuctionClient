package com.auction.virtualauctionclient.auctionscreen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.model.PlayerName;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.RoomInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPlayersAfterAuctionListAdapter extends BaseAdapter {

    private String userNameStr;
    private String roomIdStr;
    private String teamStr;
    private Context mContext;
    private ArrayList<String> mArrSchoolData;

    public AddPlayersAfterAuctionListAdapter(Context context, ArrayList<String> arrSchoolData, String userName, String roomId, String team) {
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
        view = inflater.inflate(R.layout.add_players_after_auction_listview, parent, false);


        // get the reference of textView and button
        TextView txtPlayersList = (TextView) view.findViewById(R.id.AddPlayersAfterAuctionListTxt);
        Button btnAction = (Button) view.findViewById(R.id.AddBtn);

        // Set the title and button name
        txtPlayersList.setText(mArrSchoolData.get(position));
        btnAction.setText("Add");

        // Click listener of button
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(view.getContext(), mArrSchoolData.get(position), Toast.LENGTH_SHORT).show();

                String getPlayerName =  mArrSchoolData.get(position);




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


