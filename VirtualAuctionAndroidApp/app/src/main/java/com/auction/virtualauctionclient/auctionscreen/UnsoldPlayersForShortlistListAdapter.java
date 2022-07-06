package com.auction.virtualauctionclient.auctionscreen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;


import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.model.NamesList;
import com.auction.virtualauctionclient.model.ResponseMessage;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnsoldPlayersForShortlistListAdapter extends BaseAdapter {

    private static final ArrayList<String> modelArrayList= new ArrayList<>();
    private String userNameStr;
    private String roomIdStr;
    private Button mAddBtn;
    private Context mContext;
    private ArrayList<String> mArrSchoolData;
    private ListView mListView;

    public UnsoldPlayersForShortlistListAdapter(Context context, ArrayList arrSchoolData, Button addBtn, String userName, String roomId, ListView listView) {
        super();
        mAddBtn = addBtn;
        mContext = context;
        mArrSchoolData = arrSchoolData;
        userNameStr =userName;
        roomIdStr = roomId;
        mListView = listView;
    }

    public int getCount() {
        // return the number of records
        return mArrSchoolData.size();
    }

    // getView method is called for each item of ListView
    public View getView(int position, View view, ViewGroup parent) {
        // inflate the layout for each item of listView
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.unsold_player_names_for_shortlist_listview, parent, false);


        // get the reference of textView and button
        TextView UnsoldPlayersListTxt = (TextView) view.findViewById(R.id.UnsoldPlayersListTxt);
        CheckBox SelectPlayerBox = (CheckBox) view.findViewById(R.id.SelectPlayerBox);

        // Set the title and button name
        UnsoldPlayersListTxt.setText(mArrSchoolData.get(position));
        SelectPlayerBox.setChecked(false);

        SelectPlayerBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer) SelectPlayerBox.getTag();
                if(SelectPlayerBox.isChecked()) {

                    modelArrayList.add(mArrSchoolData.get(position));
                } else {
                    modelArrayList.remove(mArrSchoolData.get(position));
                }
            }
        });

        // Click listener of button
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NamesList namesList = new NamesList();
                namesList.setUsername(userNameStr);
                namesList.setRoomId(roomIdStr);
                namesList.setNamesList(modelArrayList);
                (Client.getClient().addUnsoldPlayers("application/json", namesList)).enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                        String message = response.body().getMessage();

                        modelArrayList.clear();

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


