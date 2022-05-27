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
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.RoomInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentSetPlayersListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mArrSchoolData;

    public CurrentSetPlayersListAdapter(Context context, ArrayList<String> arrSchoolData) {
        super();
        mContext = context;
        mArrSchoolData = arrSchoolData;
    }

    public int getCount() {
        // return the number of records
        return mArrSchoolData.size();
    }

    // getView method is called for each item of ListView
    public View getView(int position, View view, ViewGroup parent) {
        // inflate the layout for each item of listView
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.current_set_players_listview, parent, false);


        // get the reference of textView and button
        TextView txtPlayersList = (TextView) view.findViewById(R.id.CurrentSetPlayersListTxt);

        // Set the title and button name
        txtPlayersList.setText(mArrSchoolData.get(position));

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


